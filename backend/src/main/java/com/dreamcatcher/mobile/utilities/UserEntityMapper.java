package com.dreamcatcher.mobile.utilities;

import com.dreamcatcher.mobile.dto.UserAuthDTO;
import com.dreamcatcher.mobile.dto.UserProfileDTO;
import com.dreamcatcher.mobile.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public User mapToUserAuthEntity(UserAuthDTO userAuthDTO) {
        return new User(
            userAuthDTO.email(),
            userAuthDTO.password(),
            userAuthDTO.name()
        );
    }

    public User mapToUserProfileEntity(UserProfileDTO userProfileDTO) {
        return new User(
            userProfileDTO.gender(),
            userProfileDTO.birthdate(),
            userProfileDTO.country(),
            userProfileDTO.occupation()
        );
    }
}