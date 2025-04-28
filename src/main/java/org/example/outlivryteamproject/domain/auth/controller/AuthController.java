package org.example.outlivryteamproject.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.auth.dto.request.SigninRequest;
import org.example.outlivryteamproject.domain.auth.dto.request.SignupRequest;
import org.example.outlivryteamproject.domain.auth.dto.response.NaverUserInfoReponse;
import org.example.outlivryteamproject.domain.auth.dto.response.SigninResponse;
import org.example.outlivryteamproject.domain.auth.dto.response.SignupResponse;
import org.example.outlivryteamproject.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 로그인
    @PostMapping("/signin")
    public SigninResponse signin(
            @Valid @RequestBody SigninRequest request) {
        return authService.signin(request);
    }

    // 네이버 소셜 로그인
    @GetMapping("/naver/callback")
    public SignupResponse naversignin(
            @RequestParam String code) {
        return authService.naverLogin(code);
    }

    // http://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=oWjzWkfgNEUAX2nl9Yy1&redirect_uri=http://localhost:8080/auth/naver/callback

}
