package com.example.docker_spring.Payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserActiveTokenPayload {
    @NotBlank
    private String verifyCode;
}
