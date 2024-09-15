package com.three_iii.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "중복된 사용자 입니다."),
    DUPLICATE_SLACK_ID(HttpStatus.BAD_REQUEST, "중복된 Slack Id 입니다."),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "사용자 정보가 올바르지 않습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    DUPLICATE_SHIPPER(HttpStatus.BAD_REQUEST, "중복된 배송담당자 입니다."),
    NOT_FOUND_SHIPPER(HttpStatus.NOT_FOUND, "존재하지 않는 배송담당자 입니다.");

    private HttpStatus status;
    private String message;
}
