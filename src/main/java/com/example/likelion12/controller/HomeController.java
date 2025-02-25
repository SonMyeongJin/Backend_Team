package com.example.likelion12.controller;

import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.dto.HomeResponse;
import com.example.likelion12.service.HomeService;
import com.example.likelion12.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final JwtProvider jwtProvider;
    private final HomeService homeService;

    @GetMapping("/main")
    public BaseResponse<HomeResponse> getHomeData(){
        Long memberId = 1007L;
        return new BaseResponse<>(homeService.getHomeData(memberId));
    }
}

