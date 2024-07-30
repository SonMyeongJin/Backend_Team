package com.example.likelion12.controller;

import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.dto.LoginResponse;
import com.example.likelion12.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * 카카오 소셜로그인 및 회원가입
     */
    @GetMapping("/auth/kakao/callback")
    public BaseResponse<Void> kakaoLogin(@RequestParam String code) {
        log.info("[MemberController.githubLogin]");
        return new BaseResponse<>(loginService.kakaoLogin(code));
    }
}
