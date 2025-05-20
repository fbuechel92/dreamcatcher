package com.dreamcatcher.mobile.dto;

public record DreamAnalysis(
    Integer dreamAnalysisId,
    Integer userId,
    String dreamTitle,
    String dreamTheme,
    String interpretation,
    String implications
) {}

