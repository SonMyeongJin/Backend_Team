package com.example.likelion12.controller;

import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.common.response.status.BaseExceptionResponseStatus;
import com.example.likelion12.dto.PatchSocialringModifyRequest;
import com.example.likelion12.dto.PostSocialringRequest;
import com.example.likelion12.dto.PostSocialringResponse;
import com.example.likelion12.service.SocialringService;
import com.example.likelion12.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/socialring")

public class SocialringController {

    private final SocialringService socialringService;
    private final JwtProvider jwtProvider;

    //소셜링 등록
    @PostMapping("")
    public BaseResponse<PostSocialringResponse> createSocialring(@RequestHeader("Authorization") String authorization ,
                                                                 @RequestBody PostSocialringRequest postSocialringRequest)
    {   //헤더에서 소셜링 등록하는 멤버아이디 찾기
        Long memberId = jwtProvider.extractIdFromHeader(authorization);
        return new BaseResponse<>(socialringService.createSocialring(memberId,postSocialringRequest));
    }

    //소셜링 수정
    @PatchMapping("")
    public BaseResponse<Void> modifySocialring(@RequestHeader("Authorization") String authorization, @RequestParam long socialringId,
                                                                 @RequestBody PatchSocialringModifyRequest patchSocialringModifyRequest)
    {
        Long memberId = jwtProvider.extractIdFromHeader(authorization);
        socialringService.modifySocialring(memberId,socialringId,patchSocialringModifyRequest);
        return new BaseResponse<>(BaseExceptionResponseStatus.SUCCESS, null);
    }

}
