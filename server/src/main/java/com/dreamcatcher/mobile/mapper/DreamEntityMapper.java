package com.dreamcatcher.mobile.mapper;

import com.dreamcatcher.mobile.dto.SubmitDreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import org.springframework.stereotype.Component;

@Component
public class DreamEntityMapper {

    public Dream mapToDreamEntity(SubmitDreamDTO submitDreamDTO) {
        return new Dream(
            submitDreamDTO.visitor(),
            submitDreamDTO.plot(),
            submitDreamDTO.location(),
            submitDreamDTO.mood(),
            submitDreamDTO.sleepQuality(),
            submitDreamDTO.additionalInfo()
        );
    }
}