package com.forzzle.hodeum.common.exception;

import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public RestApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    public RestApiException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
