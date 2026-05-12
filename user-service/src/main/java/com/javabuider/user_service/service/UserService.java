package com.javabuider.user_service.service;

import com.javabuider.user_service.dto.request.CreateUserRequest;
import com.javabuider.user_service.dto.response.CreateUserResponse;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
}