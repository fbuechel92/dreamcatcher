package com.dreamcatcher.mobile.dto;

public record Dream(
    Integer id,
    String name,
    String visitor,
    String plot,
    String location,
    String mood
) {}