package com.dreamcatcher.mobile.dto;

import java.time.LocalDateTime;

public record DreamDTO(
    Integer dreamId,
    String visitor,
    String plot,
    String location,
    String mood,
    String sleepQuality,
    String additionalInfo,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}