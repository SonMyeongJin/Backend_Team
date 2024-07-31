package com.example.likelion12.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String likelion(){
        return "test";
    }

    @GetMapping("/test2")
    public String test2(){
        return "성공~~~";
    }
}
