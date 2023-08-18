package com.quokka.classmusic.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({RestApiException.class})
    public ResponseEntity<ErrorResponse> restApiExceptionHandler(RestApiException e) {
        log.warn("NotAuthorExeception 발생 , trace:{}", e.getStackTrace());
        ErrorCode errorCode = e.getErrorCode();
        return generateErrorResponse(errorCode);
    }

    private ResponseEntity<ErrorResponse> generateErrorResponse(ErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getHttpStatus().value())
                .body(new ErrorResponse(errorCode));
    }
}
