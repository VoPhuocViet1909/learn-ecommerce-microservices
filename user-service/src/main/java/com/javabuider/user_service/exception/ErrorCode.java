package com.javabuider.user_service.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INTERNAL_ERROR(500, "Unexpected error occurred while processing request in backend service", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_ALREADY_EXISTS(400, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(404, "User not found", HttpStatus.NOT_FOUND),
    TOKEN_GENERATION_FAILED(500, "Failed to generate JWT token", HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN_EXPIRED(401, "Token has expired", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID(401, "Invalid token", HttpStatus.UNAUTHORIZED);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
