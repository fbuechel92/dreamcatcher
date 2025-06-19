package com.dreamcatcher.mobile.dto;

public record DreamDTO(
    Integer dreamId,
    Integer userId,
    Integer analysisId,
    String visitor,
    String plot,
    String location,
    String mood,
    String sleepQuality,
    String additionalInfo
) {}