package org.example.outlivryteamproject.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.config.JwtUtil;
import org.example.outlivryteamproject.config.PasswordEncoder;
import org.example.outlivryteamproject.domain.auth.dto.request.SigninRequest;
import org.example.outlivryteamproject.domain.auth.dto.request.SignupRequest;
import org.example.outlivryteamproject.domain.auth.dto.response.SigninResponse;
import org.example.outlivryteamproject.domain.auth.dto.response.SignupResponse;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.enums.UserRole;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 유저 회원가입
    @Transactional
    public SignupResponse usersignup(SignupRequest signupRequest) {

        UserRole userRole = UserRole.USER;
        if(userRepository.findAllByEmailIncludingDeleted(signupRequest.getEmail()).isPresent()) {
            throw new CustomException(ExceptionCode.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        User newUser = new User(
                signupRequest.getEmail(),
                encodedPassword,
                signupRequest.getName(),
                signupRequest.getPhone(),
                signupRequest.getBirth(),
                userRole
        );

        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(savedUser.getId(), userRole);

        return new SignupResponse(bearerToken);
    }

    // 사장님 회원가입
    @Transactional
    public SignupResponse ownersignup(SignupRequest signupRequest) {

        UserRole userRole = UserRole.OWNER;
        if(userRepository.findAllByEmailIncludingDeleted(signupRequest.getEmail()).isPresent()) {
            throw new CustomException(ExceptionCode.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        User newUser = new User(
                signupRequest.getEmail(),
                encodedPassword,
                signupRequest.getName(),
                signupRequest.getPhone(),
                signupRequest.getBirth(),
                userRole
        );

        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(savedUser.getId(), userRole);

        return new SignupResponse(bearerToken);
    }

    // 로그인
    @Transactional(readOnly = true)
    public SigninResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new CustomException(ExceptionCode.ACCOUNT_NOT_FOUND));

        if (!passwordEncoder.matchs(signinRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ExceptionCode.PASSWORD_MISMATCH);
        }

        String bearerToken = jwtUtil.createToken(user.getId(), user.getUserRole());

        return new SigninResponse(bearerToken);
    }
}
