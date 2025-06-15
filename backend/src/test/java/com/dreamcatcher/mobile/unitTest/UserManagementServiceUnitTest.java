package com.dreamcatcher.mobile.unitTest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.utilities.UserUpdater;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserManagementServiceUnitTest {

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserUpdater userUpdater;

    @Test
    public void testModifyAuthWithoutDataChange(){

        String hashedPassword = "$2a$12$65Fi4l1I7iUW3Vy7fAb2P.UmcLlT5vSF0In7.y6bGQk01oy1XLiJK";

        //Create User
        User currentUser = new User("test@gmail.com", hashedPassword, "Curie");
        User submittedUser = new User("test@gmail.com", "password123","Curie");

        //mock password encoder
        when(passwordEncoder.encode(anyString())).thenReturn(hashedPassword);

        //Apply change to current user and check if change was made
        boolean userAppliedChange = userUpdater.updateAuthFields(currentUser, submittedUser);

        Assertions.assertFalse(userAppliedChange, "There was no data change but updateAuthFields returns True.");
        Assertions.assertEquals("Curie", currentUser.getName(),"The user name should have stayed the same but it changed.");
    }

    @Test
    public void testModifyProfileWithoutDataChange(){

        //Create User
        User currentUser = new User("male", LocalDate.parse("1901-12-05"), "Germany", "Physicist");
        User submittedUser = new User("male", LocalDate.parse("1901-12-05"), "Germany", "Physicist");

        //Apply change to current user and check if change was made
        boolean userAppliedChange = userUpdater.updateProfileFields(currentUser, submittedUser);

        Assertions.assertFalse(userAppliedChange, "There was no data change but updateProfileFields returns True.");
        Assertions.assertEquals("Physicist", currentUser.getOccupation(),"The occupation should have stayed the same but it changed.");
    }

    @Test
    public void testModifyAuthWithDataChange(){

        String hashedPassword = "$2a$12$65Fi4l1I7iUW3Vy7fAb2P.UmcLlT5vSF0In7.y6bGQk01oy1XLiJK";

        //Create User
        User currentUser = new User("test@gmail.com", hashedPassword, "Curie");
        User submittedUser = new User("test@gmail.com", "password123","Heisenberg");

        //mock password encoder
        when(passwordEncoder.encode(anyString())).thenReturn(hashedPassword);

        //Apply change to current user and check if change was made
        boolean userAppliedChange = userUpdater.updateAuthFields(currentUser, submittedUser);

        Assertions.assertTrue(userAppliedChange, "There was a data change but updateAuthFields returns False.");
        Assertions.assertEquals("Heisenberg", currentUser.getName(),"The user name should have changed to Heisenberg but it didn't.");
    }

    @Test
    public void testModifyProfileWithDataChange(){

        //Create User
        User currentUser = new User("male", LocalDate.parse("1901-12-05"), "Germany", "Physicist");
        User submittedUser = new User("male", LocalDate.parse("1901-12-05"), "Germany", "Clown");

        //Apply change to current user and check if change was made
        boolean userAppliedChange = userUpdater.updateProfileFields(currentUser, submittedUser);

        Assertions.assertTrue(userAppliedChange, "There was a data change but updateProfileFields returns False.");
        Assertions.assertEquals("Clown", currentUser.getOccupation(),"The occupation should have become Clown but it didn't.");
    }

    @Test
    public void testModifyProfileWithNullChange(){

        //Create User
        User currentUser = new User("male", LocalDate.parse("1901-12-05"), "Germany", "Physicist");
        User submittedUser = new User("male", LocalDate.parse("1901-12-05"), "Germany", null);

        //Apply change to current user and check if change was made
        boolean userAppliedChange = userUpdater.updateProfileFields(currentUser, submittedUser);

        Assertions.assertTrue(userAppliedChange, "There was a data change but updateProfileFields returns False.");
        Assertions.assertEquals(null, currentUser.getOccupation(),"The occupation should have become null but it didn't.");
    }
}