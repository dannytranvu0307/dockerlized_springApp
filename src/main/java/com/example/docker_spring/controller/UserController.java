package com.example.docker_spring.controller;

import com.example.docker_spring.DTO.ResponseData;
import com.example.docker_spring.Exception.FlyException;
import com.example.docker_spring.Payload.UserActiveTokenPayload;
import com.example.docker_spring.Service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/active")
    public ResponseEntity<?> activeUser(@Valid @RequestBody UserActiveTokenPayload payload) throws FlyException {
        userService.activeUser(payload.getVerifyCode());
        return ResponseEntity.ok().body(
                ResponseData.builder()
                        .type(ResponseData.ResponseType.INFO)
                        .code("")
                        .message("Account verify successfully")
                        .build());
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";

    }
}
