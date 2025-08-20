package com.dreamcatcher.mobile.dto;

public record UserAuthDTO(
    String auth0Id,
    String email
) {}