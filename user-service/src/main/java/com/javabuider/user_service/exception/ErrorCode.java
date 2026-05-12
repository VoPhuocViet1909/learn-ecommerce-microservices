package com.javabuider.user_service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_ALREADY_EXISTS(400, "User already exists", HttpStatus.BAD_REQUEST);
    
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}