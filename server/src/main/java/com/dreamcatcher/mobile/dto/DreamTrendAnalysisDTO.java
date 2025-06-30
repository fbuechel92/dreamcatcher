package com.dreamcatcher.mobile.dto;

public record DreamTrendAnalysisDTO(
    Integer dreamTrendAnalysisId,
    Integer userId,
    String trend,
    String interpretation,
    String implications
) {}

