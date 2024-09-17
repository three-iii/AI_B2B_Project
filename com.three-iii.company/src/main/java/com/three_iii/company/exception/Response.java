package com.three_iii.company.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response<T> {

    private String resultCode;
    private T result;

    public static <T> Response<T> success(T result) {
        return Response.<T>builder()
            .resultCode("SUCCESS")
            .result(result)
            .build();
    }

    public static <T> Response<T> error(String errorCode, T result) {
        return Response.<T>builder()
            .resultCode(errorCode)
            .result(result)
            .build();
    }

    public static <T> Response<T> error(T result) {
        return Response.<T>builder()
            .resultCode("ERROR")
            .result(result)
            .build();
    }
}
