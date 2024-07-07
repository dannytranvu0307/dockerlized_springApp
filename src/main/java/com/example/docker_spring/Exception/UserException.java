package com.example.docker_spring.Exception;

public class UserException extends FlyException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, String message) {
        super(code, message);
    }
}
