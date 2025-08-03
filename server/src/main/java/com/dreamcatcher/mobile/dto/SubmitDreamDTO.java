package com.dreamcatcher.mobile.dto;

import com.dreamcatcher.mobile.enums.Mood;
import com.dreamcatcher.mobile.enums.SleepQuality;

public record SubmitDreamDTO(
    Integer dreamId,
    String visitor,
    String plot,
    String location,
    Mood mood,
    SleepQuality sleepQuality,
    String additionalInfo
) {}