package com.javabuider.user_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javabuider.user_service.common.Gender;
import com.javabuider.user_service.common.UserStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDetailResponse(
        String email,
        String firstName,
        String lastName,
        String phone,
        String avatarKey,
        Gender gender,
        LocalDate birthDate,
        UserStatus userStatus
) {}