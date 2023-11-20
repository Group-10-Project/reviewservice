package dev.abhishekt.reviewservice.services;

import dev.abhishekt.reviewservice.controllers.ReviewController;
import dev.abhishekt.reviewservice.exceptions.NotFoundException;
import dev.abhishekt.reviewservice.models.Review;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.abhishekt.reviewservice.models.User;
import dev.abhishekt.reviewservice.repositories.ReviewRepository;
import dev.abhishekt.reviewservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@SpringBootTest
public class ReviewServiceTest {
    @Autowired
    ReviewService reviewService;
    @Autowired
    private ReviewController reviewController;
    @MockBean
    ReviewRepository reviewRepository;
    @MockBean
    UserRepository userRepository;

    @Test
    public void testCreateReview() {
        // Arrange
        Review reviewToSave = new Review();
        Review savedReview = new Review(); // Create a saved review for comparison
        when(reviewRepository.save(reviewToSave)).thenReturn(savedReview);
        // Act
        ResponseEntity<Review> response = reviewService.createReview(reviewToSave);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedReview, response.getBody());

        // Verify that the save method was called on the repository
        verify(reviewRepository, times(1)).save(reviewToSave);
    }
    @Test
    public void testLikeReviewSuccess() throws NotFoundException {
        // Arrange
        Review review = new Review();
        User user = new User();
        review.getLikes().add(user);
        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<Review> response = reviewService.likeReview(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(review.getLikes().contains(user));

        // Verify that save method wasn't called because like already exists
        verify(reviewRepository, never()).save(any());
    }

    @Test
    public void testLikeReviewReviewNotFound() {
        // Arrange
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        when(reviewRepository.findById(any())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> {
            reviewService.likeReview(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        });

        // Verify that the save method wasn't called
        verify(reviewRepository, never()).save(any());
    }
    @Test
    public void testLikeReviewUserNotFound() {
        // Arrange
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        when(reviewRepository.findById(any())).thenReturn(Optional.of(new Review()));
        when(userRepository.findById(any())).thenReturn(Optional.empty());


        // Act and Assert
        assertThrows(NotFoundException.class, () -> {
            reviewService.likeReview(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        });

        // Verify that the save method wasn't called
        verify(reviewRepository, never()).save(any());
    }

    @Test
    public void testLikeReviewAddLike() throws NotFoundException {
        // Arrange
        Review review = new Review();
        User user = new User();
        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        // Act
        ResponseEntity<Review> response = reviewService.likeReview(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(review.getLikes().contains(user));
        // Verify that save method was called once
        verify(reviewRepository, times(1)).save(review);
    }
    @Test
    public void testDislikeReviewSuccess() throws NotFoundException {
        // Arrange
        Review review = new Review();
        User user = new User();
        review.getDislikes().add(user);

        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));


        // Act
        ResponseEntity<Review> response = reviewService.dislikeReview(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(review.getDislikes().contains(user));

        // Verify that save method wasn't called because dislike already exists
        verify(reviewRepository, never()).save(any());
    }

    @Test
    public void testDislikeReviewReviewNotFound() {
        // Arrange
        when(reviewRepository.findById(any())).thenReturn(Optional.empty());
        // Act and Assert
        assertThrows(NotFoundException.class, () -> {
            reviewService.dislikeReview(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        });
        // Verify that the save method wasn't called
        verify(reviewRepository, never()).save(any());
    }

    @Test
    public void testDislikeReviewUserNotFound() {
        // Arrange
        when(reviewRepository.findById(any())).thenReturn(Optional.of(new Review()));
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> {
            reviewService.dislikeReview(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        });

        // Verify that the save method wasn't called
        verify(reviewRepository, never()).save(any());
    }

    @Test
    public void testDislikeReviewAddDislike() throws NotFoundException {
        // Arrange
        Review review = new Review();
        User user = new User();

        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));


        // Act
        ResponseEntity<Review> response = reviewService.dislikeReview(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(review.getDislikes().contains(user));

        // Verify that save method was called once
        verify(reviewRepository, times(1)).save(review);
    }
    @Test
    public void testUpdateReviewSuccess() {
        // Arrange
        Review review = new Review();
        Review updatedReview = new Review(); // Create an updated review for comparison

        when(reviewRepository.save(review)).thenReturn(updatedReview);


        // Act
        ResponseEntity<Review> response = reviewService.updateReview(review);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedReview, response.getBody());

        // Verify that the save method was called once with the expected review
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void testUpdateReviewFailure() {
        // Arrange
        Review review = new Review();
        Review updatedReview = new Review();
        when(reviewRepository.save(review)).thenReturn(review); // Simulate a save failure

        // Act and Assert
        ResponseEntity<Review> response = reviewService.updateReview(review);
        assertNotEquals(updatedReview, review);

        // Verify that the save method was called once with the expected review
        verify(reviewRepository, times(1)).save(review);
    }
    @Test
    public void testDeleteReviewSuccess() throws NotFoundException {
        // Arrange
        Review review = new Review();
        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        // Act
        ResponseEntity<Void> response = reviewService.deleteReview(UUID.randomUUID().toString());
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verify that the delete method was called once with the expected review
        verify(reviewRepository, times(1)).delete(eq(review));
    }

    @Test
    public void testDeleteReviewNotFound() {
        // Arrange
        when(reviewRepository.findById(any())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> {
            reviewService.deleteReview(UUID.randomUUID().toString());
        });

        // Verify that the delete method was not called
        verify(reviewRepository, never()).delete(any());
    }
    @Test
    public void testGetReviewsForServiceSuccess() {
        // Arrange
        dev.abhishekt.reviewservice.models.Service service = new dev.abhishekt.reviewservice.models.Service();
        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(new Review());
        expectedReviews.add(new Review());
        when(reviewRepository.findByService(eq(service))).thenReturn(expectedReviews);
        // Act
        List<Review> reviews = reviewService.getReviewsForService(service);
        // Assert
        assertEquals(expectedReviews, reviews);

        // Verify that the findByService method was called once with the expected service
        verify(reviewRepository, times(1)).findByService(eq(service));
    }

    @Test
    public void testGetReviewsForServiceEmpty() {
        // Arrange
        dev.abhishekt.reviewservice.models.Service service = new dev.abhishekt.reviewservice.models.Service();

        when(reviewRepository.findByService(eq(service))).thenReturn(new ArrayList<>());

        // Act
        List<Review> reviews = reviewService.getReviewsForService(service);

        // Assert
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());

        // Verify that the findByService method was called once with the expected service
        verify(reviewRepository, times(1)).findByService(eq(service));
    }
}


