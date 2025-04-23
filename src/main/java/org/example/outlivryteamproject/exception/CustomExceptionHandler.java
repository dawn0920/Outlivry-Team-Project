package org.example.outlivryteamproject.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    // CustomException 이 발생했을때 실행 될 메서드
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        ErrorCode errorCode = e.getExceptionCode();

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new CustomExceptionResponse(
                        errorCode.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }

    // Valid, @NotBlank, @Email, @Pattern 등에서 유효성 검사를 통과하지 못할 경우 발생하는 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("유효성 검사에 실패했습니다.");

        return ResponseEntity.badRequest()
                .body(new CustomExceptionResponse(
                        errorMessage,
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }

}
