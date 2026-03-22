package com.swapnil.bookstore.app.controller;

import com.swapnil.bookstore.app.dto.ReviewRequest;
import com.swapnil.bookstore.app.entity.Review;
import com.swapnil.bookstore.app.repository.ReviewRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository repo;

    public ReviewController(ReviewRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Review add(@RequestBody ReviewRequest request) {
        Review review = new Review();
        review.setUserId(request.getUserId());
        review.setBookId(request.getBookId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return repo.save(review);
    }

    @GetMapping("/{bookId}")
    public List<Review> getByBook(@PathVariable Long bookId) {
        return repo.findByBookId(bookId);
    }
}

