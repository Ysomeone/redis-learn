package com.yuan.redis.controller.admin.exception;

public class UnauthorizedException extends RuntimeException {


    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
