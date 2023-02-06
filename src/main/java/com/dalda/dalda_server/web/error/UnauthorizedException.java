package com.dalda.dalda_server.web.error;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
    private final Integer errorCode;

    public UnauthorizedException(String message, Integer errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
