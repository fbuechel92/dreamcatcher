package com.dreamcatcher.mobile.dto;

import java.time.LocalDateTime;

public record DreamAnalysisDTO(
    Integer dreamAnalysisId,
    Integer userId,
    Integer theoryId,
    String theoryName,
    String dreamTitle,
    String dreamTheme,
    String interpretation,
    String implications,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}