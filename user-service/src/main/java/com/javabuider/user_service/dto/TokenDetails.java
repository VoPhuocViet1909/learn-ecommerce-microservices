package com.javabuider.user_service.dto;


import lombok.Builder;

@Builder
public record TokenDetails(
        String value,
        String jwtId,
        long ttlSeconds
) {}