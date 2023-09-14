package com.example.security.global.config.security;

import com.example.security.domain.account.entity.Account;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityHelper {
    public static Account getAccount() {
        return getAccountFromContext().orElseThrow(() -> new AccessDeniedException("인증 정보가 없습니다."));
    }

    public static Optional<Account> getAccountFromContext() {
        try {
            Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Optional.of(account); // Optional 안에 account를 넣는것, of
        } catch (Exception e) {
            return Optional.empty(); // 빈 값 전송
        }
    }
}
