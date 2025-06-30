package com.dreamcatcher.mobile.dto;

public record DreamDTO(
    Integer dreamId,
    String visitor,
    String plot,
    String location,
    String mood,
    String sleepQuality,
    String additionalInfo
) {}