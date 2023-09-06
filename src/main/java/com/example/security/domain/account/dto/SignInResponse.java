package com.example.security.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
    private String token;
    private Long id;
    private String name;
    private String email;
    private List<String> roles;

}
