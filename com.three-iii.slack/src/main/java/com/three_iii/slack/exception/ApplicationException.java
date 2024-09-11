package com.three_iii.slack.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    public ApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getStatus();
    }

    @Override
    public String toString() {
        return "ApplicationException{" +
            "errorCode=" + errorCode +
            ", message='" + message + '\'' +
            ", httpStatus=" + httpStatus +
            '}';
    }
}
