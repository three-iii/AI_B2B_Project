package com.three_iii.slack.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    ERROR_SENDING_MESSAGE(HttpStatus.INTERNAL_SERVER_ERROR, "슬랙 메시지 전송에 실패하였습니다.");

    private HttpStatus status;
    private String message;
}
