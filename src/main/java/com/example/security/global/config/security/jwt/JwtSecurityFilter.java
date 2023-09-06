package com.example.security.global.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtEnvironment jwtEnvironment;
    private final JwtProvider jwtProvider;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String header = request.getHeader(jwtEnvironment.AUTH_KEY_NAME);
        Cookie cookie = WebUtils.getCookie(request, jwtEnvironment.AUTH_KEY_NAME);

        if (ObjectUtils.isEmpty(cookie) && (header == null || !header.startsWith(jwtEnvironment.AUTH_SCHEME))) {
            filterChain.doFilter(request, response);
            return;
        }

        switch (request.getRequestURI()) {
            case "/login" -> log.debug("login");
            case "/api/user/sign-up" -> log.debug("가입");
            case "/api/user/sign-in" -> log.debug("로그인");
            case "/api/user/sign-out" -> log.debug("로그아웃");
            default -> {
                var authTokenHolder = getTokenForRequest(request);
                log.debug("Authorization Cookie : {}",
                        ObjectUtils.isEmpty(WebUtils.getCookie(request, jwtEnvironment.AUTH_KEY_NAME)) ?
                                null :
                                WebUtils.getCookie(request, jwtEnvironment.AUTH_KEY_NAME).getValue());

                if (authTokenHolder != null) {
                    Authentication authentication = null;
                    try {
                        authentication = jwtProvider.getAuthentication(authTokenHolder.token());
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    } catch (Exception e) {
                        // TODO. JWT 처리가 문제 생길때 쿠키 삭제 처리?
                        log.error(e.getMessage());
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private AuthTokenHolder getTokenForRequest(HttpServletRequest request) throws UnsupportedEncodingException {

        var authCookie = WebUtils.getCookie(request, jwtEnvironment.AUTH_KEY_NAME);
        var authHeader = request.getHeader(jwtEnvironment.AUTH_KEY_NAME);

        if (ObjectUtils.isNotEmpty(authCookie) && StringUtils.isNotEmpty(authCookie.getValue())) {
            return getAuthTokenHolder(authCookie.getValue());
        } else if (StringUtils.isNotEmpty(authHeader)) {
            return getAuthTokenHolder(authHeader);
        } else {
            return null;
        }

    }
    private AuthTokenHolder getAuthTokenHolder(String token) throws UnsupportedEncodingException {
        var keyDecoded = URLDecoder.decode(token, "UTF-8");
        var schemeAndToken = keyDecoded.split(" ");
        return new AuthTokenHolder(
                jwtEnvironment.AUTH_KEY_NAME,
                schemeAndToken[0],
                schemeAndToken[1]
        );
    }
}
