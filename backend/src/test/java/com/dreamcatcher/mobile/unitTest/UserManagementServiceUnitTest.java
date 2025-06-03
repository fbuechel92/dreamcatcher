package com.dreamcatcher.mobile.unitTest;

import static org.mockito.Mockito.when;

import com.dreamcatcher.mobile.dto.UserAccountCreationDTO;
import com.dreamcatcher.mobile.repository.UserRepository;
import com.dreamcatcher.mobile.service.UserManagementService;
import com.dreamcatcher.mobile.utilities.ReflectionUpdater;
import com.dreamcatcher.mobile.utilities.UserDTOMapper;
import com.dreamcatcher.mobile.utilities.UserEntityMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserManagementServiceUnitTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private UserEntityMapper userEntityMapper = Mockito.mock(UserEntityMapper.class);
    private UserDTOMapper userDTOMapper = Mockito.mock(UserDTOMapper.class);
    private BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
    private ReflectionUpdater reflectionUpdater = Mockito.mock(ReflectionUpdater.class);

    @Test
    public void testDuplicateEmailThrowsException(){
        //mock repo and method
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        //create dto
        UserAccountCreationDTO dto = new UserAccountCreationDTO("test@gmail.com", "1234", "Heisenberg");

        // Instantiate service
        UserManagementService userManagementService = new UserManagementService(
            userRepository, userEntityMapper, userDTOMapper, passwordEncoder, reflectionUpdater
        );

        // Assert exception
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userManagementService.createUser(dto);
        });

        // Verify exception message
        Assertions.assertEquals("Email already exists. Please use another email address.", exception.getMessage());
    }
}
