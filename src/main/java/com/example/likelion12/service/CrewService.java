package com.example.likelion12.service;

import com.example.likelion12.common.exception.ActivityRegionException;
import com.example.likelion12.common.exception.ExerciseException;
import com.example.likelion12.common.exception.FacilityException;
import com.example.likelion12.common.exception.MemberException;
import com.example.likelion12.domain.*;
import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.dto.crew.GetCrewDetailResponse;
import com.example.likelion12.dto.crew.PostCrewRequest;
import com.example.likelion12.dto.crew.PostCrewResponse;
import com.example.likelion12.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewService {

    private final ActivityRegionRepository activityRegionRepository;
    private final FacilityRepository facilityRepository;
    private final ExerciseRepository exerciseRepository;
    private final CrewRepository crewRepository;
    private final MemberRepository memberRepository;
    private final MemberCrewService memberCrewService;

    /**
     * 크루 등록
     */
    @Transactional
    public PostCrewResponse createCrew(Long memberId, PostCrewRequest postCrewRequest){
        log.info("[CrewService.createCrew]");

        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new MemberException(CANNOT_FOUND_MEMBER));

        String crewName = postCrewRequest.getCrewName();
        String crewImg = postCrewRequest.getCrewImg();
        Long activityRegionId = postCrewRequest.getActivityRegionId();
        Long facilityId = postCrewRequest.getFacilityId();
        Long exerciseId = postCrewRequest.getExerciseId();
        int totalRecruits = postCrewRequest.getTotalRecruits();
        int crewCost = postCrewRequest.getCrewCost();
        String simpleComment = postCrewRequest.getSimpleComment();
        String comment = postCrewRequest.getComment();
        BaseGender gender = postCrewRequest.getGender();
        BaseLevel level = postCrewRequest.getLevel();

        ActivityRegion activityRegion = activityRegionRepository.findByActivityRegionIdAndStatus(activityRegionId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new ActivityRegionException(CANNOT_FOUND_ACTIVITYREGION));

        Facility facility = facilityRepository.findByFacilityIdAndStatus(facilityId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new FacilityException(CANOOT_FOUND_FACILITY));

        Exercise exercise = exerciseRepository.findByExerciseIdAndStatus(exerciseId,BaseStatus.ACTIVE)
                .orElseThrow(()-> new ExerciseException(CANNOT_FOUND_EXERCISE));

        Crew crew = new Crew(crewName, crewImg, totalRecruits, crewCost,simpleComment,comment
                ,gender,level,activityRegion,facility, exercise, BaseStatus.ACTIVE);
        crewRepository.save(crew);
        //크루를 만든 사람이 CAPTAIN 이 되도록
        memberCrewService.createMemberCrew(member,crew);
        return new PostCrewResponse(crew.getCrewId());
    }

    /**
     * 크루 상세 조회
     */
    public GetCrewDetailResponse getCrewDetail(Long memberId, Long crewId){
        log.info("[CrewService.getCrewDetail]");
        
    }
}
