package com.dreamcatcher.mobile.utilities;

import com.dreamcatcher.mobile.entity.User;
import jakarta.transaction.Transactional;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class UserUpdater {

    //Method updates profile fields if changes are detected
    @Transactional
    public boolean updateProfileFields(User currentUser, User submittedUser) {

        boolean userAppliedChange = false;

        try {
            if (!Objects.equals(currentUser.getName(), submittedUser.getName())) {
                currentUser.setName(submittedUser.getName());
                userAppliedChange = true;
            }
            
            if (!Objects.equals(currentUser.getGender(), submittedUser.getGender())) {
                currentUser.setGender(submittedUser.getGender());
                userAppliedChange = true;
            }

            if (!Objects.equals(currentUser.getBirthdate(), submittedUser.getBirthdate())) {
                currentUser.setBirthdate(submittedUser.getBirthdate());
                userAppliedChange = true;
            }

            if (!Objects.equals(currentUser.getCountry(), submittedUser.getCountry())) {
                currentUser.setCountry(submittedUser.getCountry());
                userAppliedChange = true;
            }

            if (!Objects.equals(currentUser.getOccupation(), submittedUser.getOccupation())) {
                currentUser.setOccupation(submittedUser.getOccupation());
                userAppliedChange = true;
            }

            return userAppliedChange;

        } catch (NullPointerException e) {
            throw new RuntimeException("Failed to update user profile fields due to null values.", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while updating user profile fields.", e);
        }
    }
}