package com.dreamcatcher.mobile.dto;

import java.time.LocalDateTime;

import com.dreamcatcher.mobile.enums.SleepQuality;
import com.dreamcatcher.mobile.enums.Mood;

public record CallDreamDTO(
    Integer dreamId,
    String visitor,
    String plot,
    String location,
    Mood mood,
    SleepQuality sleepQuality,
    String additionalInfo,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}