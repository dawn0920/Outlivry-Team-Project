package org.example.outlivryteamproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode implements ErrorCode{
    // auth - 회원가입
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),

    // auth - 로그인
    ACCOUNT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "가입되지 않은 유저입니다."),
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "잘봇된 비밀번호입니다.")
    ;



    private final HttpStatus httpStatus;
    private final String errorMessage;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
