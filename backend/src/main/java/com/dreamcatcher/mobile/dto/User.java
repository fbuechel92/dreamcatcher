package com.dreamcatcher.mobile.dto;

import java.util.Date;

public record User(
    Integer userId,
    String email,
    String name,
    String gender,
    Date birthdate,
    String country,
    String occupation
) {}