package com.csaba79coder.userservice.model.persistence;

import com.csaba79coder.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * responsible for the persistence layer
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserById(UUID id);
    Optional<User> findUserByEmail(String email);
}
