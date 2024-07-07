package com.example.docker_spring.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World .com";
    }

    @PostMapping("/hello2")
    public String test(@Valid @RequestBody String a){
        return a ;

    }
}
