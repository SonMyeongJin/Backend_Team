package com.example.likelion12.controller;

import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.common.response.status.BaseExceptionResponseStatus;
import com.example.likelion12.dto.socialring.*;
import com.example.likelion12.service.SocialringService;
import com.example.likelion12.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/socialring")

public class SocialringController {

    private final SocialringService socialringService;
    private final JwtProvider jwtProvider;

    /**
     * 소셜링 등록
     */
    @PostMapping("")
    public BaseResponse<PostSocialringResponse> createSocialring(
                                                                 @RequestBody PostSocialringRequest postSocialringRequest) throws IOException {   //헤더에서 소셜링 등록하는 멤버아이디 찾기
        Long memberId = 1007L;
        return new BaseResponse<>(socialringService.createSocialring(memberId,postSocialringRequest));
    }

    /**
     * 소셜링 수정
     */
    @PatchMapping("")
    public BaseResponse<Void> modifySocialring( @RequestParam String socialringName,
                                                                 @RequestBody PatchSocialringModifyRequest patchSocialringModifyRequest) throws IOException {

        Long memberId = 1007L;
        socialringService.modifySocialring(memberId,socialringName,patchSocialringModifyRequest);
        return new BaseResponse<>(BaseExceptionResponseStatus.SUCCESS, null);
    }

    /**
     * 소셜링 상세 조회
     */
    @GetMapping("")
    public BaseResponse<GetSocialringDetailResponse> getSocialringDetail(
                                                                         @RequestParam String socialringName){
        log.info("[SocialringController.getSocialringDetail]");
        Long memberId = 1007L;
        return new BaseResponse<>(socialringService.getSocialringDetail(memberId, socialringName));
    }

    /**
     * 소셜링 참여하기
     */
    @PostMapping("/join")
    public BaseResponse<Void> joinSocialring(@RequestParam String socialringName){
        log.info("[SocialringController.joinSocialring]");
        Long memberId = 1007L;
        socialringService.joinSocialring(memberId, socialringName);
        return new BaseResponse<>(BaseExceptionResponseStatus.SUCCESS, null);
    }

    /**
     * 소셜링 검색결과 필터링
     */
    @GetMapping("/search/filter")
    public BaseResponse<List<GetSocialringSearchFilterResponse>> searchFilterSocialring(
                                                                                        @RequestBody GetSocialringSearchFilterRequest getSocialringSearchFilterRequest){
        log.info("[SocialringController.searchFilterSocialring]");
        Long memberId = 1007L;
        return new BaseResponse<>(socialringService.searchFilterSocialring(memberId, getSocialringSearchFilterRequest));
    }
  
    /**
     * 참가 예정인 소셜링
     */
    @GetMapping("/join/before")
    public BaseResponse<List<GetSocialringJoinStatusResponse>> joinBeforeSocialring() {
        log.info("[SocialringController.joinBeforeSocialring]");
        Long memberId = 1007L;
        return new BaseResponse<>(socialringService.joinBeforeSocialring(memberId));
    }

    /**
     * 참가 완료한 소셜링
     */
    @GetMapping("/join/complete")
    public BaseResponse<List<GetSocialringJoinStatusResponse>> joinCompleteSocialring(){
        log.info("[SocialringController.joinCompleteSocialring]");
        Long memberId = 1007L;
        return new BaseResponse<>(socialringService.joinCompleteSocialring(memberId));
    }

   /**
     * 소셜링 삭제하기
     */
    @PatchMapping("/delete")
    public BaseResponse<Void> deleteSocialring(
                                               @RequestParam("socialringName") String socialringName) {
        log.info("[SocialringController.deleteSocialring]");
        Long memberId = 1007L;
        socialringService.deleteSocialring(memberId, socialringName);
        return new BaseResponse<>(BaseExceptionResponseStatus.SUCCESS, null);
    }

    /**
     * 소셜링 검색하기
     */
    @GetMapping("/search")
    public BaseResponse<Page<GetSocialringSearchResponse>> searchSocialrings(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(required = false) String keyWord,
            @RequestParam(required = false) LocalDate socialringDate,
            @RequestParam(required = false) String activityRegionName,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<GetSocialringSearchResponse> responses = socialringService.searchSocialrings(keyWord, socialringDate, activityRegionName, page, 9);
        return new BaseResponse<>(BaseExceptionResponseStatus.SUCCESS, responses);
    }
  
    /**
     * 소셜링 취소하기
     */
    @PatchMapping("/cancel")
    public BaseResponse<Void> cancelSocialring(
                                               @RequestParam("socialringName") String socialringName) {
        log.info("[SocialringController.cancelSocialring]");
        Long memberId = 1007L;
        socialringService.cancelSocialring(memberId, socialringName);
        return new BaseResponse<>(BaseExceptionResponseStatus.SUCCESS, null);
    }

    /**
     * 소셜링 조회
     */
    @GetMapping("/inquiry")
    public BaseResponse<List<GetSocialringResponse>> getSocialringInquiries(
                                                                       @RequestParam List<Long> socialringId){
        log.info("[SocialringController.getSocialringInquiries]");
        Long memberId = 1007L;
        return new BaseResponse<>(socialringService.getSocialringInquiries(1007, socialringId));
    }
}
