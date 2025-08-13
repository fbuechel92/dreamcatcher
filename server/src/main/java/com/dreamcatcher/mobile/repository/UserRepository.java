package com.dreamcatcher.mobile.repository;

import com.dreamcatcher.mobile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByAuth0Id(String auth0Id);
}

