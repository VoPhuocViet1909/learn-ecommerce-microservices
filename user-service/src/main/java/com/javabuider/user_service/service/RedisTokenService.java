package com.javabuider.user_service.service;

import com.javabuider.user_service.entity.RedisToken;

public interface RedisTokenService {

    void saveToken(RedisToken token);

    void deleteTokenByJwtId(String jwtId);

    boolean existsByJwtId(String jwtId);
}