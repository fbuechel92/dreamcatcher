package com.dreamcatcher.mobile.utilities;

import com.dreamcatcher.mobile.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class UserUpdater {

    //Method updates profile fields if changes are detected
    @Transactional
    public boolean updateProfileFields(User currentUser, User submittedUser) {

        boolean userAppliedChange = false;

        try {
            if (!currentUser.getGender().equals(submittedUser.getGender())) {
                currentUser.setGender(submittedUser.getGender());
                userAppliedChange = true;
            }

            if (!currentUser.getBirthdate().equals(submittedUser.getBirthdate())) {
                currentUser.setBirthdate(submittedUser.getBirthdate());
                userAppliedChange = true;
            }

            if (!currentUser.getCountry().equals(submittedUser.getCountry())) {
                currentUser.setCountry(submittedUser.getCountry());
                userAppliedChange = true;
            }

            if (!currentUser.getOccupation().equals(submittedUser.getOccupation())) {
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

    //Method updates auth fields if changes are detected
    @Transactional
    public boolean updateAuthFields(User currentUser, User submittedUser) {

        boolean userAppliedChange = false;

        try {
            if (!currentUser.getEmail().equals(submittedUser.getEmail())) {
                currentUser.setEmail(submittedUser.getEmail());
                userAppliedChange = true;
            }

            if (!currentUser.getName().equals(submittedUser.getName())) {
                currentUser.setName(submittedUser.getName());
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