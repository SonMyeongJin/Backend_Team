package com.example.likelion12.controller;

import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.dto.PostSocialringRequest;
import com.example.likelion12.dto.PostSocialringResponse;
import com.example.likelion12.service.SocialringService;
import com.example.likelion12.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/socialring")

public class SocialringController {

    private final SocialringService socialringService;
    private final JwtProvider jwtProvider;

    @PostMapping("")
    public BaseResponse<PostSocialringResponse> createSocialring(@RequestHeader("Authorization") String authorization , @RequestBody PostSocialringRequest postSocialringRequest)
    {   //헤더에서 소셜링 등록하는 멤버아이디 찾기
        Long memberId = jwtProvider.extractIdFromHeader(authorization);
        return new BaseResponse<>(socialringService.createSocialring(memberId,postSocialringRequest));
    }
}
