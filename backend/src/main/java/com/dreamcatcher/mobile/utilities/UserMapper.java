package com.dreamcatcher.mobile.utilities;

import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO mapToDTO(User user){
        return new UserDTO(
            user.getUserId(),
            user.getEmail(),
            user.getPassword(),
            user.getName(),
            user.getGender(),
            user.getBirthdate(),
            user.getCountry(),
            user.getOccupation()
        );
    }

    public User mapToEntity(UserDTO userDTO) {
        return new User(
            userDTO.email(),
            userDTO.password(),
            userDTO.name(),
            userDTO.gender(),
            userDTO.birthdate(),
            userDTO.country(),
            userDTO.occupation()
        );
    }
}