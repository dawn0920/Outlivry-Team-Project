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

    // 가게 수정, 삭제 권한 및 메뉴 추가
    NOT_EQUALS_OWNER(HttpStatus.FORBIDDEN,"해당 가게의 사장이 아닙니다."),

    // image - 업로드
    IMAGE_FILE_REQUIRED(HttpStatus.BAD_REQUEST, "이미지 파일을 첨부해주세요."),

    // menu - 조회
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),

    // cart
    CART_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 장바구니에 접근할 권한이 없습니다."),
    CART_EMPTY(HttpStatus.BAD_REQUEST, "장바구니가 비어있습니다."),
    SOLD_OUT(HttpStatus.BAD_REQUEST, "재고가 소진되었습니다"),
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 장바구니가 존재하지 않습니다."),
    CART_EXPIRED(HttpStatus.CONFLICT, "장바구니가 만료되었습니다"),

    // order
    STORE_CLOSED(HttpStatus.CONFLICT, "영업시간이 아닙니다."),
    ORDER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 주문에 접근할 권한이 없습니다."),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "주소를 등록해주새요."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),
    DELIVERY_PRICE_LOW(HttpStatus.BAD_REQUEST, "최소 주문 금액을 맞춰주세요."),

    //review
    REVIEW_NOT_ALLOWED_BEFORE_ORDER_COMPLETION(HttpStatus.CONFLICT, "주문이 완료된 후 리뷰를 작성해주세요."),
    REVIEW_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 리뷰에 접근할 권한이 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다."),
    INVALID_REVIEW_UPDATE(HttpStatus.BAD_REQUEST, "내용과 별점 중 하나는 수정해야합니다."),
    EMPTY_CONTENT(HttpStatus.BAD_REQUEST, "내용을 입력해주세요.")

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
