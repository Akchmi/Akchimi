package com.quokka.classmusic.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private final LocalDateTime localDateTime = LocalDateTime.now();
    private final int statusCode;
    private final String error;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.statusCode = errorCode.getHttpStatus().value();
        this.error = errorCode.getHttpStatus().name();
        this.message = errorCode.getMessage();
    }
}
