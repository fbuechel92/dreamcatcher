package com.dreamcatcher.mobile.mapper;

import com.dreamcatcher.mobile.dto.CallDreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import org.springframework.stereotype.Component;

@Component
public class DreamEntityMapper {

    public Dream mapToDreamEntity(CallDreamDTO callDreamDTO) {
        return new Dream(
            callDreamDTO.visitor(),
            callDreamDTO.plot(),
            callDreamDTO.location(),
            callDreamDTO.mood(),
            callDreamDTO.sleepQuality(),
            callDreamDTO.additionalInfo()
        );
    }
}