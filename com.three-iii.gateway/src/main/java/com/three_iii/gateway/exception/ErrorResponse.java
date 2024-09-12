package com.three_iii.gateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final AuthErrorCode authErrorCode;
    private final String message;
}
