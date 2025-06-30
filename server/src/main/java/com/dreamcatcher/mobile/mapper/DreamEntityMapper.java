package com.dreamcatcher.mobile.mapper;

import com.dreamcatcher.mobile.dto.DreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DreamEntityMapper {

    public Dream mapToDreamEntity(User user, DreamDTO dreamDTO) {
        return new Dream(
            user,
            dreamDTO.visitor(),
            dreamDTO.plot(),
            dreamDTO.location(),
            dreamDTO.mood(),
            dreamDTO.sleepQuality(),
            dreamDTO.additionalInfo()
        );
    }
}