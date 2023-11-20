package dev.abhishekt.reviewservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private String reviewId;
    private String userId;

    public ReviewDTO(String userId, String reviewId) {
        this.reviewId=reviewId;
        this.userId=userId;
    }
}
