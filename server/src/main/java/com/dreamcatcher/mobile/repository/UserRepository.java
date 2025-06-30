package com.dreamcatcher.mobile.repository;

import com.dreamcatcher.mobile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
}

