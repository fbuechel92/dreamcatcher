package com.dreamcatcher.mobile.dto;

public record DreamDTO(
    Integer dreamId,
    Integer userId,
    Integer analysisId,
    Integer sleepId,
    Integer theoryId,
    String visitor,
    String plot,
    String location,
    String mood,
    String additionalInfo
) {}