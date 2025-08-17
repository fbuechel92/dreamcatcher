package com.dreamcatcher.mobile.dto;

public record SubmitDreamDTO(
    String visitor,
    String plot,
    String location,
    String mood,
    String sleepQuality,
    String additionalInfo
) {}