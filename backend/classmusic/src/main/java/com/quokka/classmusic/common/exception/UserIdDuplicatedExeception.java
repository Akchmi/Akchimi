package com.quokka.classmusic.common.exception;

public class UserIdDuplicatedExeception extends RuntimeException{
    public UserIdDuplicatedExeception() {
    }

    public UserIdDuplicatedExeception(String message) {
        super(message);
    }
}
