package com.dreamcatcher.mobile.dto;

public record UserAuthDTO(
    String email,
    String password,
    String name
) {}