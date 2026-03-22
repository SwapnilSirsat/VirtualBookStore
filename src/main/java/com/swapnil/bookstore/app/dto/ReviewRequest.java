package com.swapnil.bookstore.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Long userId;
    private Long bookId;
    private int rating;
    private String comment;
}
