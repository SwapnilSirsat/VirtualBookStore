package com.swapnil.bookstore.app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swapnil.bookstore.app.entity.Book;
import lombok.Getter;

@Getter
public class BookResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final String genre;
    private final Double price;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.price = book.getPrice();
    }

    @JsonCreator
    public BookResponse(@JsonProperty("id") Long id,
                        @JsonProperty("title") String title,
                        @JsonProperty("author") String author,
                        @JsonProperty("genre") String genre,
                        @JsonProperty("price") Double price) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
    }
}

