package com.javabuider.user_service.controller;

import com.javabuider.user_service.dto.request.CreateUserRequest;
import com.javabuider.user_service.dto.response.ApiResponse;
import com.javabuider.user_service.dto.response.CreateUserResponse;
import com.javabuider.user_service.dto.response.UserDetailResponse;
import com.javabuider.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    
    private final UserService userService;
    
    @PostMapping
    ApiResponse<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        var data = userService.createUser(request);
        return ApiResponse.<CreateUserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(data)
                .build();
    }
    @GetMapping("/me")
    ApiResponse<UserDetailResponse> getMyInfo(@AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
        var data = userService.myInfo(userId);
        return ApiResponse.<UserDetailResponse>builder()
                .code(HttpStatus.OK.value())
                .message("User info retrieved successfully")
                .data(data)
                .build();
    }
    @GetMapping
    ApiResponse<List<UserDetailResponse>> getAllUsers() {
        var data = userService.getAllUsers();
        return ApiResponse.<List<UserDetailResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Users retrieved successfully")
                .data(data)
                .build();
    }
}