package com.javabuider.user_service.controller;

import com.javabuider.user_service.dto.request.LoginRequest;
import com.javabuider.user_service.dto.response.ApiResponse;
import com.javabuider.user_service.dto.response.LoginResponse;
import com.javabuider.user_service.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request, HttpServletResponse response) {
        var data = authenticationService.login(request);
        
        Cookie cookie = new Cookie("refresh_token", data.refreshToken());
        cookie.setHttpOnly(true); // Prevents JavaScript from accessing the cookie (XSS protection)
        cookie.setSecure(false); // Change to true in production
        cookie.setPath("/"); // Cookie is accessible across all paths in the app
        cookie.setMaxAge(14 * 24 * 60 * 60); // Cookie expiry: 14 days — matches refresh token TTL
        response.addCookie(cookie);

            // Tạo response mới không chứa refresh token
        LoginResponse responseData = LoginResponse.builder()
        .accessToken(data.accessToken())
        .refreshToken(null) // Không trả về refresh token
        .roles(data.roles())
        .build();
        
        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Login successful")
                .data(data)
                .build();
    }

    @PostMapping("/refresh-token")
    ApiResponse<LoginResponse> refreshToken(@CookieValue("refresh_token") String refreshToken) {
        var data = authenticationService.refreshToken(refreshToken);
        
        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Token refreshed successfully")
                .data(data)
                .build();
    }
}