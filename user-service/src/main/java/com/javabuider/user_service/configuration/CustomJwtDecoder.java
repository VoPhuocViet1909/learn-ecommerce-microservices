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
            SignedJWT signedJWT = SignedJWT.parse(token);
            return new Jwt(
                    token,
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signedJWT.getHeader().toJSONObject(),      // CHÚ Ý: Headers nằm ở số 4
                    signedJWT.getJWTClaimsSet().getClaims()    // CHÚ Ý: Claims nằm ở số 5
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}