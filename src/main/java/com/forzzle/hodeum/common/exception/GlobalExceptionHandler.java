package com.forzzle.hodeum.common.exception;

import com.forzzle.hodeum.common.payload.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    protected ResponseEntity<Response> handleCustomException(RestApiException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        String message = ex.getMessage();
        return handleExceptionInternal(errorCode, message);
    }

    private ResponseEntity<Response> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(new Response(errorCode.getCode(), errorCode.getMessage(), message));
    }
}

