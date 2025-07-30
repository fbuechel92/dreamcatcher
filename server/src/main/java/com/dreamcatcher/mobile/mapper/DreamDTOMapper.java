package com.dreamcatcher.mobile.mapper;

import com.dreamcatcher.mobile.dto.CallDreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import org.springframework.stereotype.Component;

@Component
public class DreamDTOMapper {
    
    public CallDreamDTO mapToDreamDTO(Dream dream){
        return new CallDreamDTO(
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