package com.dreamcatcher.mobile.dto;

public record DreamAnalysisDTO(
    Integer dreamAnalysisId,
    Integer userId,
    Integer theoryId,
    String dreamTitle,
    String dreamTheme,
    String interpretation,
    String implications
) {}