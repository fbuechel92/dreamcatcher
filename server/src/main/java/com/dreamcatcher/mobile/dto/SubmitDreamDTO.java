package com.dreamcatcher.mobile.dto;

public record SubmitDreamDTO(
    Integer dreamId,
    String visitor,
    String plot,
    String location,
    String mood,
    String sleepQuality,
    String additionalInfo
) {}