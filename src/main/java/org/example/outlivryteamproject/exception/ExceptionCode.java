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
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "잘봇된 비밀번호입니다."),

    // user - 조회
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
    DUPLICATE_PASSWORD(HttpStatus.BAD_REQUEST, "새 비밀번호는 기존 비밀번호와 같을 수 없습니다."),
    ALREADY_DELETE(HttpStatus.CONFLICT, "삭제된 유저입니다."),

    // store - 생성
    STORE_LIMIT_EXCEEDED(HttpStatus.FORBIDDEN, "가게는 3개까지 생성 가능 합니다."),

    // store - 조회
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 가게를 찾을 수 없습니다."),

    // 수정 및 삭제 권한
    NOT_EQUALS_OWNER(HttpStatus.FORBIDDEN,"해당 가게의 사장이 아닙니다.")

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
