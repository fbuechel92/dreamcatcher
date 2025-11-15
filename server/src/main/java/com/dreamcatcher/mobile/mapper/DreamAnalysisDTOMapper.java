package com.dreamcatcher.mobile.mapper;

import com.dreamcatcher.mobile.dto.DreamAnalysisDTO;
import com.dreamcatcher.mobile.entity.DreamAnalysis;
import org.springframework.stereotype.Component;

@Component
public class DreamAnalysisDTOMapper {
    
    public DreamAnalysisDTO mapToDreamAnalysisDTO(DreamAnalysis analysis) {
        return new DreamAnalysisDTO(
            analysis.getDreamAnalysisId(),
            analysis.getUser().getUserId(),
            analysis.getTheory().getTheoryId(),
            analysis.getTheory().getTheoryName(),
            analysis.getDreamTitle(),
            analysis.getDreamTheme(),
            analysis.getInterpretation(),
            analysis.getImplications()
        );
    }
}