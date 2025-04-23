package org.example.outlivryteamproject.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.auth.dto.request.SigninRequest;
import org.example.outlivryteamproject.domain.auth.dto.request.SignupRequest;
import org.example.outlivryteamproject.domain.auth.dto.response.SigninResponse;
import org.example.outlivryteamproject.domain.auth.dto.response.SignupResponse;
import org.example.outlivryteamproject.domain.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 유저 회원가입
    @PostMapping("/user/signup")
    public SignupResponse usersignup(
            @Valid @RequestBody SignupRequest request) {
        return authService.usersignup(request);
    }

    // 사장님 회원가입
    @PostMapping("/owner/signup")
    public SignupResponse ownersignup(
            @Valid @RequestBody SignupRequest request) {
        return authService.ownersignup(request);
    }

    @PostMapping("/signin")
    public SigninResponse signin(
            @Valid @RequestBody SigninRequest request) {
        return authService.signin(request);
    }
}
