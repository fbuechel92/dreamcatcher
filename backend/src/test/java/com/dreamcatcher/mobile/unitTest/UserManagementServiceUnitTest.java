package com.dreamcatcher.mobile.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.dreamcatcher.mobile.dto.UserAuthDTO;
import com.dreamcatcher.mobile.dto.UserProfileDTO;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.UserRepository;
import com.dreamcatcher.mobile.service.UserManagementService;
import com.dreamcatcher.mobile.utilities.UserUpdater;
import com.dreamcatcher.mobile.utilities.UserDTOMapper;
import com.dreamcatcher.mobile.utilities.UserEntityMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserManagementServiceUnitTest {

    private BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    @Test
    public void testModifyAuthWithoutDataChange(){

        String hashedPassword = "$2a$12$65Fi4l1I7iUW3Vy7fAb2P.UmcLlT5vSF0In7.y6bGQk01oy1XLiJK";

        //Create User
        User currentUser = new User("test@gmail.com", hashedPassword, "Curie");
        User submittedUser = new User("test@gmail.com", "password123","Curie");

        //mock password encoder
        when(passwordEncoder.encode(anyString())).thenReturn(hashedPassword);

        //Apply change to current user and check if change was made
        UserUpdater userUpdater = new UserUpdater(passwordEncoder);
        boolean userAppliedChange = userUpdater.updateAuthFields(currentUser, submittedUser);

        Assertions.assertFalse(userAppliedChange, "There was no data change but updateAuthFields returns True.");
        Assertions.assertEquals("Curie", currentUser.getName(),"The user name should have stayed the same but it changed.");
    }
}