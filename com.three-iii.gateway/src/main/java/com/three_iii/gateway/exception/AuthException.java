package com.three_iii.gateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {

    private final AuthErrorCode authErrorCode;
    private final String message;
    private final HttpStatus httpStatus;

    public AuthException(AuthErrorCode authErrorCode) {
        this.authErrorCode = authErrorCode;
        this.message = authErrorCode.getMessage();
        this.httpStatus = authErrorCode.getStatus();
    }

    @Override
    public String toString() {
        return "ApplicationException{" +
            "errorCode=" + authErrorCode +
            ", message='" + message + '\'' +
            ", httpStatus=" + httpStatus +
            '}';
    }
}
