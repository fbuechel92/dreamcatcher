package com.dreamcatcher.mobile.repository;

import com.dreamcatcher.mobile.entity.User;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByAuth0Id(String auth0Id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.auth0Id = :auth0Id")
    Optional<User> findByAuth0IdWithLock(@Param("auth0Id") String auth0Id);
}

