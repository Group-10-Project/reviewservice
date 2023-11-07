package dev.abhishekt.reviewservice.repositories;

import dev.abhishekt.reviewservice.models.Review;
import dev.abhishekt.reviewservice.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
  List<Review> findByService(Service service);
}
