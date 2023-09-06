package com.example.security.domain.account.controller;

import com.example.security.domain.account.dto.SignInRequest;
import com.example.security.domain.account.dto.SignInResponse;
import com.example.security.domain.account.dto.SignUpRequest;
import com.example.security.domain.account.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor //생성자
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(@RequestBody @Valid SignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(userService.signIn(request));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> check() {
        return ResponseEntity.ok().build();
    }
}