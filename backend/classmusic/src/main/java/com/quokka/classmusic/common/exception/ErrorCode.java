package com.quokka.classmusic.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효한 요청이 아닙니다."),
    DUPLICATED_ID(HttpStatus.BAD_REQUEST, "중복된 아이디입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    ID_EMAIL_MISMATCH(HttpStatus.BAD_REQUEST, "아이디와 이메일이 일치하지 않습니다."),
    LIKE_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 존재하는 즐겨찾기입니다."),
    CONTACT_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 존재하는 강의입니다."),
    REVIEW_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 존재하는 리뷰입니다."),
    /*
     * 401 UNAUTHORIZED: 인증되지 않은 사용자의 요청
     */
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "인증된 사용자가 아닙니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT 토큰이 만료되었습니다."),

    /*
     * 403 FORBIDDEN: 권한이 없는 사용자의 요청
     */
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NOT_AUTHOR(HttpStatus.FORBIDDEN, "작성자가 아닙니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "ID와 일치하는 사용자가 없습니다."),
    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 method입니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류..."),
    MAILSERVICERROR(HttpStatus.INTERNAL_SERVER_ERROR, "메일 서비스 오류...");

    private final HttpStatus httpStatus;
    private final String message;
}
