package com.dreamcatcher.mobile.mapper;

import com.dreamcatcher.mobile.dto.DreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import org.springframework.stereotype.Component;

@Component
public class DreamEntityMapper {

    public Dream mapToDreamEntity(DreamDTO dreamDTO) {
        return new Dream(
            dreamDTO.visitor(),
            dreamDTO.plot(),
            dreamDTO.location(),
            dreamDTO.mood(),
            dreamDTO.sleepQuality(),
            dreamDTO.additionalInfo(),
            dreamDTO.createdAt(),
            dreamDTO.updatedAt()
        );
    }
}