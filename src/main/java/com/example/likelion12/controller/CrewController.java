package com.example.likelion12.controller;

import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.common.response.status.BaseExceptionResponseStatus;
import com.example.likelion12.dto.crew.GetCrewDetailResponse;
import com.example.likelion12.dto.crew.GetCrewSearchResponse;
import com.example.likelion12.dto.crew.PostCrewRequest;
import com.example.likelion12.dto.crew.PostCrewResponse;
import com.example.likelion12.service.CrewService;
import com.example.likelion12.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/crew")
public class CrewController {

    private final JwtProvider jwtProvider;
    private final CrewService crewService;

    /**
     * 크루 등록
     */
    @PostMapping("")
    public BaseResponse<PostCrewResponse> createCrew(@RequestHeader("Authorization") String authorization,
                                                     @RequestBody PostCrewRequest postCrewRequest){
        log.info("[CrewController.createCrew]");
        Long memberId = jwtProvider.extractIdFromHeader(authorization);
        return new BaseResponse<>(crewService.createCrew(memberId, postCrewRequest));
    }

    /**
     * 크루 상세 조회
     */
    @GetMapping("")
    public BaseResponse<GetCrewDetailResponse> getCrewDetail(@RequestHeader("Authorization") String authorization,
                                                             @RequestParam Long crewId){
        log.info("[CrewController.getCrewDetail]");
        Long memberId = jwtProvider.extractIdFromHeader(authorization);
        return new BaseResponse<>(crewService.getCrewDetail(memberId, crewId));
    }

    /**
     * 크루 참여하기
     */
    @PostMapping("/join")
    public BaseResponse<Void> joinCrew(@RequestHeader("Authorization") String authorization,
                                                        @RequestParam Long crewId){
        log.info("[CrewController.joinCrew]");
        Long memberId = jwtProvider.extractIdFromHeader(authorization);
        crewService.joinCrew(memberId,crewId);
        return new BaseResponse<>(null);
    }

    /**
     * 크루 삭제하기
     */
    @PatchMapping("/delete")
    public BaseResponse<Void> deleteCrew(@RequestHeader("Authorization") String authorization,
                                         @RequestParam Long crewId){
        log.info("[CrewController.deleteCrew]");
        Long memberId = jwtProvider.extractIdFromHeader(authorization);
        crewService.deleteCrew(memberId,crewId);
        return new BaseResponse<>(null);
    }

    /**
     * 크루 탈퇴하기
     */
    @PatchMapping("/cancel")
    public BaseResponse<Void> cancelCrew(@RequestHeader("Authorization") String authorization,
                                         @RequestParam Long crewId){
        log.info("[CrewController.deleteCrew]");
        Long memberId = jwtProvider.extractIdFromHeader(authorization);
        crewService.cancelCrew(memberId,crewId);
        return new BaseResponse<>(null);
    }

    /**
     * 크루 검색
     */
    @GetMapping("/search")
    public BaseResponse<Page<GetCrewSearchResponse>> searchSocialrings(
            @RequestParam(required = false) String keyWord,
            @RequestParam(required = false) String activityRegionName,
            @RequestParam(required = false) String exerciseName,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<GetCrewSearchResponse> responses = crewService.searchCrews(keyWord, activityRegionName, exerciseName,page, 9);
        return new BaseResponse<>(BaseExceptionResponseStatus.SUCCESS, responses);
    }
}
