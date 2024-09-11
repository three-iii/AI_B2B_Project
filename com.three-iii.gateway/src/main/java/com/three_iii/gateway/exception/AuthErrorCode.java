package com.three_iii.gateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthErrorCode {
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 서명 정보입니다."),
    TOKEN_IS_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰 유효기간이 만료되었습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "토큰 형식이 잘못되었습니다.");

    private HttpStatus status;
    private String message;
}
