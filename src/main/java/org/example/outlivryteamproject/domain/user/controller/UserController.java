package org.example.outlivryteamproject.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.user.dto.request.UserDeleteRequest;
import org.example.outlivryteamproject.domain.user.dto.request.UserPasswordRequest;
import org.example.outlivryteamproject.domain.user.dto.request.UserRequest;
import org.example.outlivryteamproject.domain.user.dto.response.UserReponse;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.example.outlivryteamproject.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // 유저 조회
    @GetMapping
    public ResponseEntity<UserReponse> getInfo(
            HttpServletRequest request) {
        UserReponse userReponse = userService.getUser(request);
        return ResponseEntity.ok(userReponse);
    }

    // 유저 정보 수정
    @PutMapping
    public void changeUserInfo(
            HttpServletRequest httpServletRequest,
            @Validated @RequestBody UserRequest request ) {
        userService.changeUserInfo(httpServletRequest, request);
    }

    // 유저 패스워드 변경
    @PutMapping("/changePW")
    public void changePassword(
            HttpServletRequest httpServletRequest,
            @Validated @RequestBody UserPasswordRequest request ) {
        userService.changePassword(httpServletRequest, request);
    }

    // 유저 탈퇴
    @DeleteMapping
    public ResponseEntity<?> deleteUser(
            HttpServletRequest httpServletRequest,
            @RequestBody UserDeleteRequest request) {
        userService.deleteUser(httpServletRequest, request);
        return ResponseEntity.ok("회원 탈퇴 완료");
    }
}
