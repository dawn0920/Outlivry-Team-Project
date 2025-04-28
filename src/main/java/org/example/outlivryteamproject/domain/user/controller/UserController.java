package org.example.outlivryteamproject.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.response.ApiResponse;
import org.example.outlivryteamproject.domain.user.dto.request.UserDeleteRequest;
import org.example.outlivryteamproject.domain.user.dto.request.UserPasswordRequest;
import org.example.outlivryteamproject.domain.user.dto.request.UserRequest;
import org.example.outlivryteamproject.domain.user.dto.response.UserReponse;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.example.outlivryteamproject.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<UserReponse>> getInfo(
            HttpServletRequest request) {
        UserReponse userReponse = userService.getUser(request);
        return new ResponseEntity<>(new ApiResponse<>("유저 정보 수정 완료", userReponse), HttpStatus.OK);
    }

    // 유저 정보 수정
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> changeUserInfo(
            HttpServletRequest httpServletRequest,
            @Validated @RequestBody UserRequest request ) {
        userService.changeUserInfo(httpServletRequest, request);
        return new ResponseEntity<>(new ApiResponse<>("유저 정보 수정 완료"), HttpStatus.OK);
    }

    // 유저 패스워드 변경
    @PutMapping("/changePW")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            HttpServletRequest httpServletRequest,
            @Validated @RequestBody UserPasswordRequest request ) {
        userService.changePassword(httpServletRequest, request);
        return new ResponseEntity<>(new ApiResponse<>("패스워드 변경 완료"), HttpStatus.OK);
    }

    // 유저 탈퇴
    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> deleteUser(
            HttpServletRequest httpServletRequest,
            @RequestBody UserDeleteRequest request) {
        userService.deleteUser(httpServletRequest, request);
        return new ResponseEntity<>(new ApiResponse<>("회원 탈퇴 완료"), HttpStatus.OK);
    }
}
