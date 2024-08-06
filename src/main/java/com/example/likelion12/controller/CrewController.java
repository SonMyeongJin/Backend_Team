package com.example.likelion12.controller;

import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.common.response.status.BaseExceptionResponseStatus;
import com.example.likelion12.dto.crew.*;
import com.example.likelion12.service.CrewService;
import com.example.likelion12.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public BaseResponse<PostCrewResponse> createCrew(@ModelAttribute PostCrewRequest postCrewRequest) throws IOException {
        log.info("[CrewController.createCrew]");
        Long memberId = 1007L;
        return new BaseResponse<>(crewService.createCrew(memberId, postCrewRequest));
    }

    /**
     * 크루 조회
     */
    @GetMapping("/inquiry")
    public BaseResponse<List<GetCrewInquiryResponse>> getCrewInquiries(@RequestParam int page){
        log.info("[CrewController.getCrewInquiries]");
        Long memberId = 1007L;
        return new BaseResponse<>(crewService.getCrewInquiries(1007,page));
    }


    /**
     * 크루 상세 조회
     */
    @GetMapping("")
    public BaseResponse<GetCrewDetailResponse> getCrewDetail(
                                                             @RequestParam String crewName){
        log.info("[CrewController.getCrewDetail]");
        Long memberId = 1007L;
        return new BaseResponse<>(crewService.getCrewDetail(memberId, crewName));
    }

    /**
     * 크루 참여하기
     */
    @PostMapping("/join")
    public BaseResponse<Void> joinCrew(
                                                        @RequestParam String crewName){
        log.info("[CrewController.joinCrew]");
        Long memberId = 1007L;
        crewService.joinCrew(memberId,crewName);
        return new BaseResponse<>(null);
    }

    /**
     * 크루 검색결과 필터링
     */
    @GetMapping("/search/filter")
    public BaseResponse<List<GetCrewSearchFilterResponse>> searchFilterCrew(
                                                                            @RequestBody GetCrewSearchFilterRequest getCrewSearchFilterRequest) {
        log.info("[CrewController.searchFilterCrew]");
        Long memberId = 1007L;
        return new BaseResponse<>(crewService.searchFilterCrew(memberId, getCrewSearchFilterRequest));
    }

    /**
     * 크루 삭제하기
     */
    @PatchMapping("/delete")
    public BaseResponse<Void> deleteCrew(
                                         @RequestParam String crewName){
        log.info("[CrewController.deleteCrew]");
        Long memberId = 1007L;
        crewService.deleteCrew(memberId,crewName);
        return new BaseResponse<>(null);
    }

    /**
     * 크루 탈퇴하기
     */
    @PatchMapping("/cancel")
    public BaseResponse<Void> cancelCrew(
                                         @RequestParam String crewName){
        log.info("[CrewController.deleteCrew]");
        Long memberId = 1007L;
        crewService.cancelCrew(memberId,crewName);
        return new BaseResponse<>(null);
    }

    /**
     * 크루 검색
     */
    @GetMapping("/search")
    public BaseResponse<Page<GetCrewSearchResponse>> searchCrews(
            @RequestParam(required = false) String keyWord,
            @RequestParam(required = false) String activityRegionName,
            @RequestParam(required = false) String exerciseName,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<GetCrewSearchResponse> responses = crewService.searchCrews(keyWord, activityRegionName, exerciseName,page, 9);
        return new BaseResponse<>(BaseExceptionResponseStatus.SUCCESS, responses);
    }
  /**
     * 참여중인 크루 조회
     */
    @GetMapping("/join")
    public BaseResponse<List<GetJoinCrewResponse>> getJoinCrew(String authorization) {
        log.info("[CrewController.getJoinCrew]");
        Long memberId = 1007L;
        List<GetJoinCrewResponse> joinCrewResponses = crewService.getJoinCrew(memberId);
        return new BaseResponse<>(joinCrewResponses);
    }
}
