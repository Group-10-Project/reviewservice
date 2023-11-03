package dev.abhishekt.reviewservice.controllers;

import static org.mockito.Mockito.*;

import dev.abhishekt.reviewservice.dtos.DeleteReviewDTO;
import dev.abhishekt.reviewservice.dtos.ReviewDTO;
import dev.abhishekt.reviewservice.exceptions.NotFoundException;
import dev.abhishekt.reviewservice.models.Review;
import dev.abhishekt.reviewservice.models.Service;
import dev.abhishekt.reviewservice.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReviewControllerTest {
    @Autowired
    private ReviewController reviewController;
    @MockBean
    private ReviewService reviewService;
    @Captor
    private ArgumentCaptor<String> idCaptor;
    @Test
    void returnExceptionWhenReviewDoesntExist() throws NotFoundException {
        when(reviewService.getReview("c4550096-1596-432d-89c2-c6f3fb9954a0"))
                .thenReturn(null);
        assertThrows(NotFoundException.class,() -> reviewController.getReview("c4550096-1596-432d-89c2-c6f3fb9954a1"));
    }
    @Test
    public void testCreateReviewSuccess() {
        // Arrange
        Review review = new Review();
        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(review, HttpStatus.CREATED);

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.createReview(eq(review))).thenReturn(expectedResponse);

//        ReviewController reviewController = new ReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify that createReview method was called once with the expected review
        verify(reviewService, times(1)).createReview(eq(review));
    }

    @Test
    public void testCreateReviewFailure() {
        // Arrange
        Review review = new Review();
        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.createReview(eq(review))).thenReturn(expectedResponse);

//        ReviewController reviewController = new ReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify that createReview method was called once with the expected review
        verify(reviewService, times(1)).createReview(eq(review));
    }

    @Test
    public void testLikeReviewSuccess() throws NotFoundException {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO("userId", "reviewId");
        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(new Review(), HttpStatus.OK);

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.likeReview(eq("userId"), eq("reviewId"))).thenReturn(expectedResponse);

//        Re reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        ResponseEntity<Review> response = reviewController.likeReview(reviewDTO);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify that likeReview method was called once with the expected userId and reviewId
        verify(reviewService, times(1)).likeReview(eq("userId"), eq("reviewId"));
    }

    @Test
    public void testLikeReviewFailure() throws NotFoundException {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO("userId", "reviewId");
        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.likeReview(eq("userId"), eq("reviewId"))).thenReturn(expectedResponse);

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        ResponseEntity<Review> response = reviewController.likeReview(reviewDTO);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify that likeReview method was called once with the expected userId and reviewId
        verify(reviewService, times(1)).likeReview(eq("userId"), eq("reviewId"));
    }

    @Test
    public void testLikeReviewNotFound() throws NotFoundException {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO("userId", "reviewId");

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.likeReview(eq("userId"), eq("reviewId"))).thenThrow(new NotFoundException("Not found"));

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> {
            reviewController.likeReview(reviewDTO);
        });

        // Verify that likeReview method was called once with the expected userId and reviewId
        verify(reviewService, times(1)).likeReview(eq("userId"), eq("reviewId"));
    }
    @Test
    public void testDislikeReviewSuccess() throws NotFoundException {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO("userId", "reviewId");
        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(new Review(), HttpStatus.OK);

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.dislikeReview(eq("userId"), eq("reviewId"))).thenReturn(expectedResponse);

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        ResponseEntity<Review> response = reviewController.dislikeReview(reviewDTO);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify that dislikeReview method was called once with the expected userId and reviewId
        verify(reviewService, times(1)).dislikeReview(eq("userId"), eq("reviewId"));
    }

    @Test
    public void testDislikeReviewFailure() throws NotFoundException {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO("userId", "reviewId");
        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.dislikeReview(eq("userId"), eq("reviewId"))).thenReturn(expectedResponse);

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        ResponseEntity<Review> response = reviewController.dislikeReview(reviewDTO);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify that dislikeReview method was called once with the expected userId and reviewId
        verify(reviewService, times(1)).dislikeReview(eq("userId"), eq("reviewId"));
    }

    @Test
    public void testDislikeReviewNotFound() throws NotFoundException {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO("userId", "reviewId");

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.dislikeReview(eq("userId"), eq("reviewId"))).thenThrow(new NotFoundException("Not found"));

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> {
            reviewController.dislikeReview(reviewDTO);
        });

        // Verify that dislikeReview method was called once with the expected userId and reviewId
        verify(reviewService, times(1)).dislikeReview(eq("userId"), eq("reviewId"));
    }
    @Test
    public void testUpdateReviewSuccess() {
        // Arrange
        Review review = new Review();
        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(new Review(), HttpStatus.OK);

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.updateReview(eq(review))).thenReturn(expectedResponse);

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        ResponseEntity<Review> response = reviewController.updateReview(review);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify that updateReview method was called once with the expected review
        verify(reviewService, times(1)).updateReview(eq(review));
    }

    @Test
    public void testUpdateReviewFailure() {
        // Arrange
        Review review = new Review();
        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.updateReview(eq(review))).thenReturn(expectedResponse);

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        ResponseEntity<Review> response = reviewController.updateReview(review);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify that updateReview method was called once with the expected review
        verify(reviewService, times(1)).updateReview(eq(review));
    }
    @Test
    public void testDeleteReviewSuccess() throws NotFoundException {
        // Arrange
        DeleteReviewDTO reviewDTO = new DeleteReviewDTO("reviewId");
        ResponseEntity<Void> expectedResponse = new ResponseEntity<>(HttpStatus.OK);

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.deleteReview(eq("reviewId"))).thenReturn(expectedResponse);

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        ResponseEntity<Void> response = reviewController.deleteReview(reviewDTO);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify that deleteReview method was called once with the expected reviewId
        verify(reviewService, times(1)).deleteReview(eq("reviewId"));
    }

    @Test
    public void testDeleteReviewFailure() throws NotFoundException {
        // Arrange
        DeleteReviewDTO reviewDTO = new DeleteReviewDTO("reviewId");

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.deleteReview(eq("reviewId"))).thenThrow(new NotFoundException("Not found"));

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> {
            reviewController.deleteReview(reviewDTO);
        });

        // Verify that deleteReview method was called once with the expected reviewId
        verify(reviewService, times(1)).deleteReview(eq("reviewId"));
    }

    @Test
    public void testGetReviewSuccess() throws NotFoundException {
        // Arrange
        String reviewId = "reviewId";
        Review expectedReview = new Review();
        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(expectedReview, HttpStatus.OK);

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.getReview(eq(reviewId))).thenReturn(expectedResponse);

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        ResponseEntity<Review> response = reviewController.getReview(reviewId);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify that getReview method was called once with the expected reviewId
        verify(reviewService, times(1)).getReview(eq(reviewId));
    }

    @Test
    public void testGetReviewNotFound() throws NotFoundException {
        // Arrange
        String reviewId = "reviewId";

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.getReview(eq(reviewId))).thenThrow(new NotFoundException("Not found"));

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> {
            reviewController.getReview(reviewId);
        });

        // Verify that getReview method was called once with the expected reviewId
        verify(reviewService, times(1)).getReview(eq(reviewId));
    }
    @Test
    public void testGetReviewsForService() {
        // Arrange
        Service service = new Service(); // Create a service object or use appropriate data
        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(new Review());
        expectedReviews.add(new Review());

        ReviewService reviewService = mock(ReviewService.class);
        when(reviewService.getReviewsForService(eq(service))).thenReturn(expectedReviews);

//        YourReviewController reviewController = new YourReviewController();
        reviewController.setReviewService(reviewService);

        // Act
        List<Review> reviews = reviewController.getReviewsForService(service);

        // Assert
        assertNotNull(reviews);
        assertEquals(expectedReviews.size(), reviews.size());
        assertEquals(expectedReviews, reviews);

        // Verify that getReviewsForService method was called once with the expected service
        verify(reviewService, times(1)).getReviewsForService(eq(service));
    }
}
