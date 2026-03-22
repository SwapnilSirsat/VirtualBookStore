package com.swapnil.bookstore.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Long userId;
    private String idempotencyKey;
}

