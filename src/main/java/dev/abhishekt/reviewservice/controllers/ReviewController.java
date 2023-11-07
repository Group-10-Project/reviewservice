package dev.abhishekt.reviewservice.controllers;

import dev.abhishekt.reviewservice.dtos.DeleteReviewDTO;
import dev.abhishekt.reviewservice.dtos.ReviewDTO;
import dev.abhishekt.reviewservice.exceptions.NotFoundException;
import dev.abhishekt.reviewservice.models.Review;
import dev.abhishekt.reviewservice.models.Service;
import dev.abhishekt.reviewservice.models.User;
import dev.abhishekt.reviewservice.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private ReviewService reviewService;
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }
    @PostMapping("/submit")
    public ResponseEntity<Review> createReview(@RequestBody Review review){
        return reviewService.createReview(review);
    }
    @PutMapping("/like")
    public ResponseEntity<Review> likeReview(@RequestBody ReviewDTO reviewDTO) throws NotFoundException {
        return reviewService.likeReview(reviewDTO.getUserId(),reviewDTO.getReviewId());
    }
    @PutMapping("/dislike")
    public ResponseEntity<Review> dislikeReview(@RequestBody ReviewDTO reviewDTO) throws NotFoundException {
        return reviewService.dislikeReview(reviewDTO.getUserId(),reviewDTO.getReviewId());
    }
    @PutMapping("/update")
    public ResponseEntity<Review> updateReview(@RequestBody Review review){
        return reviewService.updateReview(review);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteReview(@RequestBody DeleteReviewDTO reviewDTO) throws NotFoundException {
        return reviewService.deleteReview(reviewDTO.getId());
    }
    @GetMapping("{id}")
    public ResponseEntity<Review> getReview(@PathVariable("id") String id) throws NotFoundException {
        return reviewService.getReview(id);
    }
    @GetMapping("/service")
    public List<Review> getReviewsForService(@RequestBody Service service){
        return reviewService.getReviewsForService(service);
    }

    public void setReviewService(ReviewService reviewService) {
    }
}
