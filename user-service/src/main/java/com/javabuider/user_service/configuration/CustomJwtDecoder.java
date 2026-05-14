package com.javabuider.user_service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm; // THÊM IMPORT NÀY VÀO TRÊN CÙNG
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.javabuider.user_service.service.RedisTokenService;
import com.nimbusds.jwt.SignedJWT;
import javax.crypto.spec.SecretKeySpec;
import jakarta.annotation.PostConstruct;
import java.text.ParseException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private NimbusJwtDecoder nimbusJwtDecoder = null;
        // Inject RedisTokenService để check blacklist
    private final RedisTokenService redisTokenService;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "HmacSHA512");
        
        // SỬA LẠI ĐOẠN NÀY ĐỂ KÍCH HOẠT THUẬT TOÁN HS512
        nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS512) 
                .build();
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            // Parse JWT để lấy jwtId
            SignedJWT signedJWT = SignedJWT.parse(token);
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            
            // Kiểm tra jwtId có trong Redis blacklist không
            // Nếu có → token đã bị thu hồi (user đã logout)
            if(redisTokenService.existsByJwtId(jwtId))
                throw new JwtException("Token is expired");
            
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        
        // Nếu token không trong blacklist → decode bình thường
        return nimbusJwtDecoder.decode(token);
    }
}