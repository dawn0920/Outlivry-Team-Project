package org.example.outlivryteamproject.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.common.SoftDelete;
import org.example.outlivryteamproject.config.PasswordEncoder;
import org.example.outlivryteamproject.domain.user.dto.request.UserDeleteRequest;
import org.example.outlivryteamproject.domain.user.dto.request.UserPasswordRequest;
import org.example.outlivryteamproject.domain.user.dto.request.UserRequest;
import org.example.outlivryteamproject.domain.user.dto.response.UserReponse;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 로그인 한 유저 정보 조회
    @Transactional(readOnly = true)
    public UserReponse getUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        if (userId == null) {
            throw new CustomException(ExceptionCode.USER_NOT_FOUND);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

        return new UserReponse(user);
    }

    // 유저 정보 수정
    @Transactional
    public void changeUserInfo(HttpServletRequest httpServletRequest, UserRequest request) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

        // String 변수 = 조건 ? 값1 : 값2; -> 조건이 true면 값1 사용 false면 값2 사용
        String nickname = isNullEmpty(request.getNickname()) ? user.getNickname() : request.getNickname();
        String name = isNullEmpty(request.getName()) ? user.getName() : request.getName();
        String phone = isNullEmpty(request.getPhone()) ? user.getPhone() : request.getPhone();
        String address = isNullEmpty(request.getAddress()) ? user.getAddress() : request.getAddress();

        user.update(nickname, name, phone, address);
    }

    // null인지 값이 비어있는지 체크 메서드 !! true -> 비어있음
    private boolean isNullEmpty(String str) {
        // str.trim().isEmpty(); -> trim()은 문자열 양쪽의 공백을 제거
        return str == null || str.trim().isEmpty();
    }

    // 유저 패스워드 변경
    @Transactional
    public void changePassword(HttpServletRequest httpServletRequest, UserPasswordRequest request) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

        if (passwordEncoder.matchs(request.getNewPassword(), user.getPassword())) {
            throw new CustomException(ExceptionCode.DUPLICATE_PASSWORD);
        }

        if(!passwordEncoder.matchs(request.getOldPassword(), user.getPassword())) {
            throw new CustomException(ExceptionCode.PASSWORD_MISMATCH);
        }

        user.changePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    // 유저 탈퇴
    @Transactional
    public void deleteUser(HttpServletRequest httpServletRequest, UserDeleteRequest request) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

        if (user.isDeleted()) {
            throw new CustomException(ExceptionCode.ALREADY_DELETE);
        }

        if (!passwordEncoder.matchs(request.getPassword(), user.getPassword())) {
            throw new CustomException(ExceptionCode.PASSWORD_MISMATCH);
        }

        user.isdelete();
        for (Store store : user.getStores()) {
            store.isdelete();
            for (Menu menu : store.getMenuList()) {
                menu.isdelete();
            }
        }

        userRepository.save(user);
    }

}
