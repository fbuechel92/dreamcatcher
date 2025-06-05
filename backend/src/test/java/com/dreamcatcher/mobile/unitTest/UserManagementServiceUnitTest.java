package com.dreamcatcher.mobile.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.dreamcatcher.mobile.dto.UserAccountCreationDTO;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.UserRepository;
import com.dreamcatcher.mobile.service.UserManagementService;
import com.dreamcatcher.mobile.utilities.ReflectionUpdater;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UserManagementServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserManagementService userManagementService;

    @Test
    public void testDuplicateEmailThrowsException(){

        //mock method
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        //create dto
        UserAccountCreationDTO dto = new UserAccountCreationDTO("test@gmail.com", "1234", "Heisenberg");

        // Assert exception
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userManagementService.createUser(dto);
        });

        // Verify exception message
        assertEquals("Email already exists. Please use another email address.", exception.getMessage());
    }

    @Test
    public void testDifferingUserData(){
        //Create User
        User currentUser = new User("test@gmail.com", "Heisenberg", "male", LocalDate.parse("1901-12-05"), "Germany", "Physicist");
        User submittedUser = new User("test@gmail.com", "Curie", "female", LocalDate.parse("1934-07-04"), "Germany", "Physicist");

        //Apply change to current user and check if change was made
        ReflectionUpdater reflectionUpdater = new ReflectionUpdater();
        boolean userAppliedChange = reflectionUpdater.updateFields(currentUser, submittedUser);

        Assertions.assertTrue(userAppliedChange, "The result of userAppliedChange should be true but it's false");
        Assertions.assertEquals("Curie", currentUser.getName(),"The user name should have changed to Curie but it didn't");
    }

    @Test
    public void testSameUserData(){
        //Create User
        User currentUser = new User("test@gmail.com", "Heisenberg", "male", LocalDate.parse("1901-12-05"), "Germany", "Physicist");
        User submittedUser = new User("test@gmail.com", "Heisenberg", "male", LocalDate.parse("1901-12-05"), "Germany", "Physicist");

        //Apply change to current user and check if change was made
        ReflectionUpdater reflectionUpdater = new ReflectionUpdater();
        boolean userAppliedChange = reflectionUpdater.updateFields(currentUser, submittedUser);

        Assertions.assertFalse(userAppliedChange, "The result of userAppliedChange should be false but it's true");
    }
}