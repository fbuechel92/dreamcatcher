package com.dreamcatcher.mobile.utilities;

import com.dreamcatcher.mobile.dto.UserAuthDTO;
import com.dreamcatcher.mobile.dto.UserProfileDTO;
import com.dreamcatcher.mobile.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public UserProfileDTO mapToUserProfileDTO(User user){
        return new UserProfileDTO(
            user.getGender(),
            user.getBirthdate(),
            user.getCountry(),
            user.getOccupation()
        );
    }
}