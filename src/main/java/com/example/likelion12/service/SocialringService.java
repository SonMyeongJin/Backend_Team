package com.example.likelion12.service;

import com.example.likelion12.common.exception.ActivityRegionException;
import com.example.likelion12.common.exception.ExerciseException;
import com.example.likelion12.common.exception.FacilityException;
import com.example.likelion12.common.exception.MemberException;
import com.example.likelion12.domain.*;
import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.dto.PostSocialringRequest;
import com.example.likelion12.dto.PostSocialringResponse;
import com.example.likelion12.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialringService {
    private final SocialringRepository socialringRepository;
    private final MemberRepository memberRepository;
    private final FacilityRepository facilityRepository;
    private final ExerciseRepository exerciseRepository;
    private final MemberSocialringService memberSocialringService;
    private final ActivityRegionRepository activityRegionRepository;

    //소셜링등록
    @Transactional
    public PostSocialringResponse createSocialring(Long memberId, PostSocialringRequest postSocialringRequest) {
        log.info("[SocialringService.createSocialring]");
        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new MemberException(CANNOT_FOUND_MEMBER));

        String socialringName  = postSocialringRequest.getSocialringName();
        String socialringImg = postSocialringRequest.getSocialringImg();
        long activityRegionId = postSocialringRequest.getActivityRegionId();
        long facilityId = postSocialringRequest.getFacilityId();
        long exerciseId = postSocialringRequest.getExerciseId();
        int totalRecruits = postSocialringRequest.getTotalRecruits();
        LocalDate socialringDate = postSocialringRequest.getSocialringDate();
        int socialringCost = postSocialringRequest.getSocialringCost();
        String comment = postSocialringRequest.getComment();
        String commentSimple = postSocialringRequest.getCommentSimple();
        BaseGender gender = postSocialringRequest.getGender();
        BaseLevel level = postSocialringRequest.getLevel();

        ActivityRegion activityRegion = activityRegionRepository.findByActivityRegionIdAndStatus(activityRegionId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new ActivityRegionException(CANNOT_FOUND_ACTIVITYREGION));
        Facility facility = facilityRepository.findByFacilityIdAndStatus(facilityId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new FacilityException(CANOOT_FOUND_FACILITY));
        Exercise exercise = exerciseRepository.findByExerciseIdAndStatus(exerciseId,BaseStatus.ACTIVE)
                .orElseThrow(()-> new ExerciseException(CANNOT_FOUND_EXERCISE));

        Socialring socialring = new Socialring(socialringName,socialringImg,totalRecruits,socialringDate,socialringCost,comment,commentSimple,
                gender,level,activityRegion,facility, exercise, BaseStatus.ACTIVE);
        socialringRepository.save(socialring);

        //소셜링을 만든 사람이 CAPTAIN 이 되도록
        memberSocialringService.createMemberSocialring(member,socialring);
        return new PostSocialringResponse(socialring.getSocialringId());
    }
}
