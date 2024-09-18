package com.three_iii.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final ErrorCode errorCode;
    private final String message;
}
