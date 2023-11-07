package dev.abhishekt.reviewservice.repositories;

import dev.abhishekt.reviewservice.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, UUID> {
}
