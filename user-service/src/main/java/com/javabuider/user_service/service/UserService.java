package com.javabuider.user_service.service;

import com.javabuider.user_service.dto.request.CreateUserRequest;
import com.javabuider.user_service.dto.response.CreateUserResponse;
import com.javabuider.user_service.dto.response.UserDetailResponse;
import java.util.List;
public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
    UserDetailResponse myInfo(String userId);
    List<UserDetailResponse> getAllUsers();
}