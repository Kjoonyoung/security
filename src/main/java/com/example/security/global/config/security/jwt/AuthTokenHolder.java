package com.example.security.global.config.security.jwt;

public record AuthTokenHolder(
        String header,
        String scheme,
        String token
) {
}
