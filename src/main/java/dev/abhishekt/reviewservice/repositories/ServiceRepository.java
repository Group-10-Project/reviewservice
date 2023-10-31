package dev.abhishekt.reviewservice.repositories;

import dev.abhishekt.reviewservice.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
}
