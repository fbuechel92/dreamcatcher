package com.dreamcatcher.mobile.mapper;

import com.dreamcatcher.mobile.dto.DreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import org.springframework.stereotype.Component;

@Component
public class DreamDTOMapper {
    
    public DreamDTO mapToDreamDTO(Dream dream){
        return new DreamDTO(
            dream.getDreamId(),
            dream.getVisitor(),
            dream.getPlot(),
            dream.getLocation(),
            dream.getMood(),
            dream.getSleepQuality(),
            dream.getAdditionalInfo(),
            dream.getcreatedAt(),
            dream.getUpdatedAt()
        );
    }
}