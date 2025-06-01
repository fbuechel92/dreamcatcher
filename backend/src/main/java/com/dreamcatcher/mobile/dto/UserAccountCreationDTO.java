package com.dreamcatcher.mobile.dto;

public record UserAccountCreationDTO(
    String email,
    String password,
    String name
) {}