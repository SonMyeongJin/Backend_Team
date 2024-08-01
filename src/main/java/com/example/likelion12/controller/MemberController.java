package com.example.likelion12.controller;

import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.dto.PostSignupRequest;
import com.example.likelion12.dto.PostSignupResponse;
import com.example.likelion12.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public BaseResponse<PostSignupResponse> signUp(@RequestBody PostSignupRequest postSignupRequest){
        log.info("[MemberController.signUp]");
        return new BaseResponse<>(memberService.signUp(postSignupRequest));
    }
}
