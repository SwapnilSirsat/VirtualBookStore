package com.swapnil.bookstore.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long bookId;
    private int rating;
    private String comment;
}

