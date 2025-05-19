package com.dreamcatcher.mobile.dto;

public record DreamTrendAnalysis(
    Integer dreamTrendAnalysisId,
    Integer userId,
    String trend,
    String interpretation,
    String implications
) {}

