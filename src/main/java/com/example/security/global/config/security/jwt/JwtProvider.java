package com.example.security.global.config.security.jwt;

import com.example.security.domain.account.entity.Account;
import com.example.security.domain.account.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtEnvironment jwtEnvironment;

    private Key secretKey;

    // 만료시간 : 1Hour
    private final long exp = 1000L * 60 * 60;

    private final CustomUserDetailService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(jwtEnvironment.publicKey.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(String email, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", authorities);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) {
        Account account = userDetailsService.loadUserByUsername(this.getPhoneNumber(token));
        return new UsernamePasswordAuthenticationToken(account, "", account.getAuthorities());
    }

    // 토큰에 담겨있는 유저 account 획득
    public String getPhoneNumber(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Authorization Header를 통해 인증을 한다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // Bearer 검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
