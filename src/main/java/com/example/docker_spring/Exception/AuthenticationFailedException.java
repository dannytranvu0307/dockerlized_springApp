package com.example.docker_spring.Exception;

public class AuthenticationFailedException extends FlyException {
    private static final long serialVersionUID = 1L;

    public AuthenticationFailedException(String code, String message) {
        super(code, message);
    }
    public AuthenticationFailedException(String email) {
        super("01",String.format("Email or password invalid with email: %s", email));

    }

}

