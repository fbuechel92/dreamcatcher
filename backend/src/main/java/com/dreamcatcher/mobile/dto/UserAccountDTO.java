package com.dreamcatcher.mobile.dto;

import java.time.LocalDate;

public record UserAccountDTO(
    String email,
    String name,
    String gender,
    LocalDate birthdate,
    String country,
    String occupation
) {}