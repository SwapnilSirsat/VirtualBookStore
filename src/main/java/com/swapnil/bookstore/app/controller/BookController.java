package com.swapnil.bookstore.app.controller;

import com.swapnil.bookstore.app.dto.BookResponse;
import com.swapnil.bookstore.app.entity.Book;
import com.swapnil.bookstore.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookResponse> getBooks(Pageable pageable) {
        return bookService.getBooks(pageable);
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id,
                       @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}


