package com.fluentenglish.web.auth.token.jwt;

public class InvalidJWTException extends RuntimeException {
    public InvalidJWTException(String message) {
        super(message);
    }
}
