package org.example.outlivryteamproject.auth.service;

import com.amazonaws.services.kms.model.SignRequest;
import org.example.outlivryteamproject.config.JwtUtil;
import org.example.outlivryteamproject.config.PasswordEncoder;
import org.example.outlivryteamproject.domain.auth.dto.request.SigninRequest;
import org.example.outlivryteamproject.domain.auth.dto.request.SignupRequest;
import org.example.outlivryteamproject.domain.auth.dto.response.NaverUserInfoReponse;
import org.example.outlivryteamproject.domain.auth.dto.response.SigninResponse;
import org.example.outlivryteamproject.domain.auth.dto.response.SignupResponse;
import org.example.outlivryteamproject.domain.auth.service.AuthService;
import org.example.outlivryteamproject.domain.auth.weblogin.naver.NaverOAuthClient;
import org.example.outlivryteamproject.domain.user.entity.User;
import org.example.outlivryteamproject.domain.user.enums.UserRole;
import org.example.outlivryteamproject.domain.user.repository.UserRepository;
import org.example.outlivryteamproject.exception.CustomException;
import org.example.outlivryteamproject.exception.ExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private NaverOAuthClient naverOAuthClient;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("유저 회원가입 - 실패, 이미 존재하는 이메일입니다.")
    public void 유저_회원가입_시_이미_존재한는_이메일이면_예외_처리() {
        // given
        SignupRequest request = new SignupRequest("test@test.com", "password", "test", "010-0000-0000", "0000-00-00");

        given(userRepository.findAllByEmailIncludingDeleted(anyString()))
                .willReturn(Optional.of(new User("test@test.com", "encodedPassword", "test", "010-0000-0000", "0000-00-00", UserRole.USER)));

        // when
        CustomException exception = assertThrows(CustomException.class, () ->
                authService.usersignup(request));

        // then
        assertEquals(ExceptionCode.EMAIL_ALREADY_EXISTS, exception.getExceptionCode());
    }

    @Test
    @DisplayName("유저 회원가입 - 성공, 토큰 발급")
    public void 유저_회원가입_성공() {
        // given
        SignupRequest request = new SignupRequest("test@test.com", "password", "test", "010-0000-0000", "0000-00-00");

        given(userRepository.findAllByEmailIncludingDeleted(anyString()))
                .willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString())).willReturn("encodePassword");
        given(userRepository.save(any())).willReturn(new User("test@test.com", "encodedPassword", "test", "010-0000-0000", "0000-00-00", UserRole.USER));
        given(jwtUtil.createToken(any(), any())).willReturn("mocked-token");

        // when
        SignupResponse response = authService.usersignup(request);

        // then
        assertNotNull(response);
        assertEquals("mocked-token", response.getBearerToken());
    }

    @Test
    @DisplayName("사장님 회원가입 - 실패, 이미 존재하는 이메일입니다.")
    public void 사장님_회원가입_시_이미_존재한는_이메일이면_예외_처리() {
        // given
        SignupRequest request = new SignupRequest("test@test.com", "password", "test", "010-0000-0000", "0000-00-00");

        given(userRepository.findAllByEmailIncludingDeleted(anyString()))
                .willReturn(Optional.of(new User("test@test.com", "encodedPassword", "test", "010-0000-0000", "0000-00-00", UserRole.OWNER)));

        // when
        CustomException exception = assertThrows(CustomException.class, () ->
                authService.ownersignup(request));

        // then
        assertEquals(ExceptionCode.EMAIL_ALREADY_EXISTS, exception.getExceptionCode());
    }

    @Test
    @DisplayName("사장님 회원가입 - 성공, 토큰 발급")
    public void 사장님_회원가입_성공() {
        // given
        SignupRequest request = new SignupRequest("test@test.com", "password", "test", "010-0000-0000", "0000-00-00");

        given(userRepository.findAllByEmailIncludingDeleted(anyString()))
                .willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString())).willReturn("encodePassword");
        given(userRepository.save(any())).willReturn(new User("test@test.com", "encodedPassword", "test", "010-0000-0000", "0000-00-00", UserRole.OWNER));
        given(jwtUtil.createToken(any(), any())).willReturn("mocked-token");

        // when
        SignupResponse response = authService.ownersignup(request);

        // then
        assertNotNull(response);
        assertEquals("mocked-token", response.getBearerToken());
    }

    @Test
    @DisplayName("로그인 - 실패, 존재하지 않는 계정")
    public void 로그인_시_가입하지_않는_이메일이면_예외_처리() {
        // given
        SigninRequest request = new SigninRequest("test@test.com", "password");

        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(CustomException.class, () -> {
            authService.signin(request);
        });

        // then
        assertEquals(ExceptionCode.ACCOUNT_NOT_FOUND, exception.getExceptionCode());
    }

    @Test
    @DisplayName("로그인 - 실패, 비밀번호 미일치시")
    public void 로그인_시_비밀번호가_일치하지_않으면_예외_처리() {
        // given
        SigninRequest request = new SigninRequest("test@test.com", "wrongPassword");
        User user = new User("test@test.com", "encodedPassword", "test", "010-0000-0000", "0000-00-00", UserRole.USER);

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.matchs(anyString(), anyString())).willReturn(false);

        // when
        CustomException exception = assertThrows(CustomException.class, () -> {
            authService.signin(request);
        });

        // then
        assertEquals(ExceptionCode.PASSWORD_MISMATCH, exception.getExceptionCode());
    }

    @Test
    @DisplayName("로그인 - 성공, 정상 작동")
    public void 로그인_성공() {
        // given
        SigninRequest request = new SigninRequest("test@test.com", "password");
        User user = new User("test@test.com", "encodedPassword", "test", "010-0000-0000", "0000-00-00", UserRole.USER);

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.matchs(anyString(), anyString())).willReturn(true);
        given(jwtUtil.createToken(any(), any())).willReturn("mocked-token");

        // when
        SigninResponse response = authService.signin(request);

        // then
        assertNotNull(response);
        assertEquals("mocked-token", response.getBearerToken());
    }

    private String validCode = "validCode";
    private String invalidCode = "invalidCode";

    @Test
    @DisplayName("네이버 - 로그인 성공")
    public void 네이버_로그인_성공() {
        // given
        String email = "test@naver.com";
        String name = "test";
        String nickname = "nickname";
        String birth = "0000-00-00";
        String phone = "010-0000-0000";

        NaverUserInfoReponse userInfo = new NaverUserInfoReponse();
        userInfo.setEmail(email);
        userInfo.setName(name);
        userInfo.setNickname(nickname);
        userInfo.setBirth(birth);
        userInfo.setPhone(phone);

        // API 호출 성공시 반환
        given(naverOAuthClient.getAccessToken(anyString())).willReturn("mocked-access-token");
        given(naverOAuthClient.getUserInfo(anyString())).willReturn(userInfo);

        // DB에 사용자가 없을 경우 새로 생성
//        given(userRepository.findAllByEmailIncludingDeleted(email)).willReturn(Optional.empty());
        given(userRepository.save(any())).willReturn(new User(email, "encodedPassword", name, phone, birth, UserRole.USER));

        given(jwtUtil.createToken(any(), any())).willReturn("mocked-token");

        // when
        SignupResponse response = authService.naverLogin(validCode);

        // then
        assertNotNull(response);
        assertEquals("mocked-token", response.getBearerToken());

    }

    @Test
    @DisplayName("네이버 - 로그인 실패, 네이버 API 통신 오류")
    public void 네이버_로그인_실패_API_통신_오류() {
        // given
        given(naverOAuthClient.getAccessToken(anyString())).willThrow(RestClientException.class);

        // when
        CustomException exception = assertThrows(CustomException.class, () -> {
            authService.naverLogin(invalidCode);
        });

        // then
        assertEquals(ExceptionCode.NAVER_API_ERROR, exception.getExceptionCode());

    }

    @Test
    @DisplayName("네이버 - 로그인 실패, 유효하지 않은 코드")
    public void 네이버_로그인_실패_유효하지_않은_코드() {
        // given
        given(naverOAuthClient.getAccessToken(anyString())).willThrow(RestClientException.class);

        // when
        CustomException exception = assertThrows(CustomException.class, () -> {
           authService.naverLogin(invalidCode);
        });
    }
}
