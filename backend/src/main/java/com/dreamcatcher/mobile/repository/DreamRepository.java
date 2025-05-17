package com.dreamcatcher.mobile.repository;

import com.dreamcatcher.mobile.entity.Dream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DreamRepository extends JpaRepository<Dream, Integer> {
}