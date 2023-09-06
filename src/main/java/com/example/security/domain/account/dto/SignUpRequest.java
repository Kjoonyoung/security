package com.example.security.domain.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class SignUpRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
