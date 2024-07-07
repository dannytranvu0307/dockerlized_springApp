package com.example.docker_spring.Payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailPayload {
    @NotBlank
    @Size(min = 16, max = 128)
    private String email;
}
