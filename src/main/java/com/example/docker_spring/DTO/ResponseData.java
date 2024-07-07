package com.example.docker_spring.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {
    public enum ResponseType {
        INFO,WARINING,ERROR;
    }
    private ResponseType type;
    private String message;
    private String code;
    private Object data;


}