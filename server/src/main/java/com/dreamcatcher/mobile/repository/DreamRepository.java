package com.dreamcatcher.mobile.repository;

import com.dreamcatcher.mobile.entity.Dream;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DreamRepository extends JpaRepository<Dream, Integer> {
    @Query("SELECT d FROM Dream d WHERE d.user.userId = :userId order by createdAt desc")
    List<Dream> findByUserId(@Param("userId") Integer userId);
}