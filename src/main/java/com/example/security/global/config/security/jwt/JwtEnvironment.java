package com.example.security.global.config.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtEnvironment {

    @Value("${auth.publicKey:changeMe}")
    String publicKey;

    @Value("${auth.privateKey:changeMe}")
    String privateKey;

    @Value("Bearer")
    String AUTH_SCHEME;

    @Value("Authorization")
    String AUTH_KEY_NAME;

}
