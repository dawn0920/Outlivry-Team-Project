package org.example.outlivryteamproject.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.config.JwtUtil;
import org.example.outlivryteamproject.config.PasswordEncoder;
import org.example.outlivryteamproject.domain.auth.dto.request.SigninRequest;
import org.example.outlivryteamproject.domain.auth.dto.request.SignupRequest;
import org.example.outlivryteamproject.domain.auth.dto.response.NaverUserInfoReponse;
import org.example.outlivryteamproject.domain.auth.dto.response.SigninResponse;
import org.example.outlivryteamproject.domain.auth.dto.response.SignupResponse;
import org.example.outlivryteamproject.domain.auth.weblogin.naver.NaverOAuthClient;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.enums.LoginType;
import org.example.outlivryteamproject.domain.user.enums.UserRole;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final NaverOAuthClient naverOAuthClient; // 네이버 API 요청

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

    // 네이버 로그인
    @Transactional
    public SignupResponse naverLogin(String code) {
        try {// 1. 네이버로 부터 Access Token 받기
            String accessToken = naverOAuthClient.getAccessToken(code);

            // 2. 네이버 사용자 정보 가져오기
            NaverUserInfoReponse userInfo = naverOAuthClient.getUserInfo(accessToken);

            String email = userInfo.getEmail();
            String name = userInfo.getName();
            String nickname = userInfo.getNickname();
            String birth = userInfo.getBirth();
            String phone = userInfo.getPhone();

            // DB에 사용자가 있는지 확인
            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> {
                        // 없으면 새로 생성
                        User newUser = new User();
                        newUser.setEmail(email);
                        newUser.setName(name);
                        newUser.setNickname(nickname);  // 수정: 'name'이 아닌 'nickname'으로 설정
                        newUser.setBirth(birth);  // 수정: 'birth' 설정
                        newUser.setPhone(phone);  // 수정: 'phone' 설정
                        newUser.setUserRole(UserRole.USER);
                        newUser.setLoginType(LoginType.NAVER);  // 수정: 'NANER' 오타 수정
                        return userRepository.save(newUser);
                    });

            // 4. JWT 토큰 발급
            String token = jwtUtil.createToken(user.getId(), user.getUserRole());

            return new SignupResponse(token);
        } catch (RestClientException e) {
            // 네이버 API 통신 실패 처리
            throw new CustomException(ExceptionCode.NAVER_API_ERROR, e.getMessage());
        } catch (Exception e) {
            // 기타 예기치 못한 오류 처리
            throw new CustomException(ExceptionCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    private static final String NAVER_API_URL = "https://openapi.naver.com/v1/nid/me";

    // Naver API로부터 사용자 정보 가져오기
    public String getUserInfoFromNaver(String accessToken) {
        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // RestTemplate을 사용해 GET 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(NAVER_API_URL, HttpMethod.GET,
                new org.springframework.http.HttpEntity<>(headers), String.class);

        return responseEntity.getBody();  // 응답 본문 반환 (JSON 형태)
    }

}
