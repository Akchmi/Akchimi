package com.quokka.classmusic.common.exception;

public class NotAuthorExeception extends RuntimeException{
    public NotAuthorExeception() {
    }
    public NotAuthorExeception(String message) {
        super(message);
    }
}
