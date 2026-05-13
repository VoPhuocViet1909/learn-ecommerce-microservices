package com.javabuider.user_service.service;

import com.javabuider.user_service.dto.request.LoginRequest;
import com.javabuider.user_service.dto.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
}