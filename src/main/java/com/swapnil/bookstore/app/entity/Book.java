package com.swapnil.bookstore.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String author;
    private String genre;
    private Double price;
    private Integer stock;

    @Version
    private Long version;
}
