package dev.abhishekt.reviewservice.services;

import dev.abhishekt.reviewservice.exceptions.NotFoundException;
import dev.abhishekt.reviewservice.models.Review;
import dev.abhishekt.reviewservice.models.User;
import dev.abhishekt.reviewservice.repositories.ReviewRepository;
import dev.abhishekt.reviewservice.repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    public ReviewService(ReviewRepository reviewRepository,UserRepository userRepository){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }
    public ResponseEntity<Review> createReview(Review review){
        Review savedReview = reviewRepository.save(review);
        ResponseEntity<Review> response = new ResponseEntity<>(savedReview, HttpStatus.CREATED);
        return response;
    }
    public ResponseEntity<Review> likeReview(String userId, String reviewId) throws NotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(UUID.fromString(reviewId));
        Optional<User> userOptional = userRepository.findById(UUID.fromString(userId));
        if(reviewOptional.isEmpty()){
            throw new NotFoundException("Review Not Found For Id:"+reviewId);
//            return null;
        }
        if(userOptional.isEmpty()){
            throw new NotFoundException("Review Not Found For Id:"+userId);
//            return null;
        }
        Review review = reviewOptional.get();
        User user = userOptional.get();
        if(!review.getLikes().contains(user)){
            review.getLikes().add(user);
            reviewRepository.save(review);
        }
        ResponseEntity<Review> response = new ResponseEntity<>(review,HttpStatus.OK);
        return response;
    }

    public ResponseEntity<Review> dislikeReview(String userId,String reviewId) throws NotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(UUID.fromString(reviewId));
        Optional<User> userOptional = userRepository.findById(UUID.fromString(userId));
        if(reviewOptional.isEmpty()) {
            throw new NotFoundException("Review Not Found For Id:"+reviewId);
//            return null;
        }
        if(userOptional.isEmpty()){
            throw new NotFoundException("Review Not Found For Id:"+userId);
//            return null;
        }

        Review review = reviewOptional.get();
        User user = userOptional.get();
        if(!review.getDislikes().contains(user)){
            review.getDislikes().add(user);
            reviewRepository.save(review);
        }
        ResponseEntity<Review> response = new ResponseEntity<>(review,HttpStatus.OK);
        return response;
    }
    public ResponseEntity<Review> updateReview(Review review){
        Review updatedReview = reviewRepository.save(review);
        ResponseEntity<Review> response = new ResponseEntity<>(updatedReview,HttpStatus.OK);
        return response;
    }
    public ResponseEntity<Void> deleteReview(String id) throws NotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(UUID.fromString(id));
        if(reviewOptional.isEmpty()) {
            throw new NotFoundException("Review Not Found For Id:"+id);
//            return null;
        }
        Review review = reviewOptional.get();
        reviewRepository.delete(review);
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }
    public ResponseEntity<Review> getReview(String id) throws NotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(UUID.fromString(id));
        if(reviewOptional.isEmpty()) {
            throw new NotFoundException("Review Not Found For Id:"+id);
//            return null;
        }
        Review review = reviewOptional.get();
        ResponseEntity<Review> response = new ResponseEntity<>(review,HttpStatus.OK);
        return response;
    }
    public List<Review> getReviewsForService(dev.abhishekt.reviewservice.models.Service service){
        return reviewRepository.findByService(service);
    }
}
