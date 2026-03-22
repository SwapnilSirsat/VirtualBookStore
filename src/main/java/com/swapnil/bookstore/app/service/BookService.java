package com.swapnil.bookstore.app.service;

import com.swapnil.bookstore.app.dto.BookResponse;
import com.swapnil.bookstore.app.entity.Book;
import com.swapnil.bookstore.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private static final String BOOK_CACHE_PREFIX = "book:";
    private static final String BOOK_LIST_CACHE_PREFIX = "books:page:";

    private final BookRepository bookRepository;
    private final RedisTemplate<String, List<BookResponse>> redisTemplate;

    public BookService(BookRepository bookRepository,
                       @Qualifier("redisTemplate") RedisTemplate<String, List<BookResponse>> redisTemplate) {
        this.bookRepository = bookRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Get paginated list of books
     * Cached by page + size for fast browsing
     */
    public List<BookResponse> getBooks(Pageable pageable) {

        // later try to implement a credit card or library card based payement page where i give the card with details, where
        // they make payments via otp and all
        // try to understand the working of this pageable later
        String cacheKey = BOOK_LIST_CACHE_PREFIX
                + pageable.getPageNumber() + ":" + pageable.getPageSize();

        // if we fetch data from db, its working completly fine
        List<BookResponse> cached =
                 redisTemplate.opsForValue().get(cacheKey);
        if(cached != null){
            return cached;
        }

        List<BookResponse> bookResponses = bookRepository.findAll(pageable).stream().map(this::toDto).toList();


        redisTemplate.opsForValue()
                .set(cacheKey, bookResponses, Duration.ofMinutes(5));

        return bookResponses;
    }

    /**
     * Get single book details
     */
    public BookResponse getBookById(Long id) {

        String cacheKey = BOOK_CACHE_PREFIX + id;

        List<BookResponse> cached =  redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return cached.getFirst();
        }

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        redisTemplate.opsForValue()
                .set(cacheKey, new ArrayList<>(){{
                    add(new BookResponse(book));
                }}, Duration.ofMinutes(10));

        return new BookResponse(book);
    }

    /**
     * Admin: create new book
     */
    public Book createBook(Book book) {
        Book saved = bookRepository.save(book);
        evictBookCaches(saved.getId());
        return saved;
    }

    /**
     * Admin: update existing book
     */
    public Book updateBook(Long id, Book updatedBook) {

        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setGenre(updatedBook.getGenre());
        existing.setPrice(updatedBook.getPrice());
        existing.setStock(updatedBook.getStock());

        Book saved = bookRepository.save(existing);
        evictBookCaches(id);

        return saved;
    }

    /**
     * Admin: delete book
     */
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
        evictBookCaches(id);
    }

    /**
     * Clear all related caches when data changes
     */
    private void evictBookCaches(Long bookId) {
        redisTemplate.delete(BOOK_CACHE_PREFIX + bookId);
        redisTemplate.delete(BOOK_LIST_CACHE_PREFIX + "*");
    }

    private BookResponse toDto(Book book) {
        return new BookResponse(book);
    }
}


