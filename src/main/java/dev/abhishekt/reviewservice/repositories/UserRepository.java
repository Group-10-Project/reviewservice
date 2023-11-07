package dev.abhishekt.reviewservice.repositories;

import dev.abhishekt.reviewservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
