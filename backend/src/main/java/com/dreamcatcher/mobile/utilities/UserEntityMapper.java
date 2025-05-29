package com.dreamcatcher.mobile.utilities;

import com.dreamcatcher.mobile.dto.UserAccountCreationDTO;
import com.dreamcatcher.mobile.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public User mapToUserAccountCreationEntity(UserAccountCreationDTO userAccountCreationDTO) {
        return new User(
            userAccountCreationDTO.email(),
            userAccountCreationDTO.password(),
            userAccountCreationDTO.name()
        );
    }
}