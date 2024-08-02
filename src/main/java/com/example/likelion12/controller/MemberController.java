package com.example.likelion12.controller;

import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.dto.member.PostSignupRequest;
import com.example.likelion12.dto.member.PostSignupResponse;
import com.example.likelion12.service.MemberService;
import com.example.likelion12.util.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    public BaseResponse<PostSignupResponse> signUp(@RequestBody PostSignupRequest postSignupRequest){
        log.info("[MemberController.signUp]");
        return new BaseResponse<>(memberService.signUp(postSignupRequest));
    }

    @PatchMapping("/signout")
    public BaseResponse<String> signOut(@RequestHeader("Authorization") String authorization){
        Long memberId = jwtProvider.extractIdFromHeader(authorization);
        memberService.signOut(memberId);
        return new BaseResponse<>("회원탈퇴 되었습니다");
    }
}
