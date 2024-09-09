package com.three_iii.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    DUPLICATED_NAME(HttpStatus.BAD_REQUEST, "중복된 이름입니다.");
    private HttpStatus status;
    private String message;
}
