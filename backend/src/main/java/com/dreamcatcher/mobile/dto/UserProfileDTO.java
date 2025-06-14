package com.dreamcatcher.mobile.dto;

import java.time.LocalDate;

public record UserProfileDTO(
    String gender,
    LocalDate birthdate,
    String country,
    String occupation
) {}