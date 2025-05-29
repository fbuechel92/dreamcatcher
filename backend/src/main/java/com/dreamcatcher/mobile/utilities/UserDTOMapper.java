package com.dreamcatcher.mobile.utilities;

import com.dreamcatcher.mobile.dto.UserAccountDTO;
import com.dreamcatcher.mobile.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public UserAccountDTO mapToUserAccountDTO(User user){
        return new UserAccountDTO(
            user.getEmail(),
            user.getName(),
            user.getGender(),
            user.getBirthdate(),
            user.getCountry(),
            user.getOccupation()
        );
    }
}