package dev.abhishekt.reviewservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteReviewDTO {
    private String id;//reviewId

    public DeleteReviewDTO(String reviewId) {
        this.id=reviewId;
    }

}
