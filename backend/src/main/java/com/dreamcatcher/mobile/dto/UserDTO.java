package com.dreamcatcher.mobile.dto;

import java.util.Date;

public record UserDTO(
    Integer userId,
    String email,
    String password,
    String name,
    String gender,
    Date birthdate,
    String country,
    String occupation
) {}