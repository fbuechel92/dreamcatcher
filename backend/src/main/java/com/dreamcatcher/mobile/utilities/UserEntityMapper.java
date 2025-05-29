package com.dreamcatcher.mobile.utilities;

import com.dreamcatcher.mobile.dto.UserAccountCreationDTO;
import com.dreamcatcher.mobile.dto.UserAccountDTO;
import com.dreamcatcher.mobile.entity.User;
import java.util.Date;
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

    public User mapToUserAccountEntity(UserAccountDTO userAccountDTO) {
        return new User(
            userAccountDTO.email(),
            userAccountDTO.name(),
            userAccountDTO.gender(),
            userAccountDTO.birthdate(),
            userAccountDTO.country(),
            userAccountDTO.occupation()
        );
    }
}