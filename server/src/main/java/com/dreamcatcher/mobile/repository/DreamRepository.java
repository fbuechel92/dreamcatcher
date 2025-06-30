package com.dreamcatcher.mobile.repository;

import com.dreamcatcher.mobile.entity.Dream;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DreamRepository extends JpaRepository<Dream, Integer> {
    List<Dream> findByUserId(Integer userId);
}