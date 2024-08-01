package com.example.likelion12.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String likelion(){
        return "testㅇㅇㅇㅇ";
    }
}
