package com.example.likelion12.service;

import com.example.likelion12.common.exception.*;
import com.example.likelion12.domain.*;
import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.dto.PatchSocialringModifyRequest;
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
    private final MemberSocialringRepository memberSocialringRepository;

    //소셜링등록
    @Transactional
    public PostSocialringResponse createSocialring(Long memberId, PostSocialringRequest postSocialringRequest) {
        log.info("[SocialringService.createSocialring]");
        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new MemberException(CANNOT_FOUND_MEMBER));

        String socialringName = postSocialringRequest.getSocialringName();
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
                .orElseThrow(() -> new ActivityRegionException(CANNOT_FOUND_ACTIVITYREGION));
        Facility facility = facilityRepository.findByFacilityIdAndStatus(facilityId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new FacilityException(CANOOT_FOUND_FACILITY));
        Exercise exercise = exerciseRepository.findByExerciseIdAndStatus(exerciseId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new ExerciseException(CANNOT_FOUND_EXERCISE));

        Socialring socialring = new Socialring(socialringName, socialringImg, totalRecruits, socialringDate, socialringCost, comment, commentSimple,
                gender, level, activityRegion, facility, exercise, BaseStatus.ACTIVE);
        socialringRepository.save(socialring);

        //소셜링을 만든 사람이 CAPTAIN 이 되도록
        memberSocialringService.createMemberSocialring(member, socialring);
        return new PostSocialringResponse(socialring.getSocialringId());
    }

    //소셜링 수정
    @Transactional
    public void modifySocialring(Long memberId, Long socialringId, PatchSocialringModifyRequest patchSocialringModifyRequest) {
        log.info("[SocialringService.modifySocialring]");

        //소셜링을 수정하고자 하는 멤버
        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new MemberException(CANNOT_FOUND_MEMBER));

        //수정하고자 하는 소셜링
        Socialring socialring = socialringRepository.findBySocialringIdAndStatus(socialringId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new SocialringException(CANNOT_FOUND_SOCIALRING));

        //수정하고자 하는 멤버의 멤버소셜링
        MemberSocialring memberSocialring = memberSocialringRepository.findByMember_MemberIdAndSocialring_SocialringIdAndStatus(memberId,
                socialringId, BaseStatus.ACTIVE).orElseThrow(() -> new MemberSocialringException(CANNOT_FOUND_MEMBERSOCIALRING));

        //접근 멤버가 captain인지 유효성 검사
        memberSocialringService.ConfirmCaptainMemberSocialring(memberSocialring);

        String newSocialringName = patchSocialringModifyRequest.getSocialringName()
                == null ? socialring.getSocialringName() : patchSocialringModifyRequest.getSocialringName();
        String newSocialringImg = patchSocialringModifyRequest.getSocialringImg()
                == null ? socialring.getSocialringImg() : patchSocialringModifyRequest.getSocialringImg();
        Integer newTotalRecruits = patchSocialringModifyRequest.getTotalRecruits()
                == null ? socialring.getTotalRecruits() : patchSocialringModifyRequest.getTotalRecruits();
        LocalDate newSocialringDate = patchSocialringModifyRequest.getSocialringDate()
                == null ? socialring.getSocialringDate() : patchSocialringModifyRequest.getSocialringDate();
        Integer newSocialringCost = patchSocialringModifyRequest.getSocialringCost()
                == null ? socialring.getSocialringCost() : patchSocialringModifyRequest.getSocialringCost();
        String newComment = patchSocialringModifyRequest.getComment()
                == null ? socialring.getComment() : patchSocialringModifyRequest.getComment() ;
        String newCommentSimple = patchSocialringModifyRequest.getCommentSimple()
                == null ? socialring.getCommentSimple() : patchSocialringModifyRequest.getCommentSimple() ;
        BaseGender newGender = patchSocialringModifyRequest.getGender()
                == null ? socialring.getGender() : patchSocialringModifyRequest.getGender();
        BaseLevel newLevel = patchSocialringModifyRequest.getLevel()
                == null ? socialring.getLevel() : patchSocialringModifyRequest.getLevel();

        long newActivityRegionId = patchSocialringModifyRequest.getActivityRegionId()
                == null ?  socialring.getActivityRegion().getActivityRegionId() :
                //리퀘스트로 들어온값이 존재하는 값인지
                activityRegionRepository.findByActivityRegionIdAndStatus(
                        patchSocialringModifyRequest.getActivityRegionId(), BaseStatus.ACTIVE)
                .orElseThrow(() -> new ActivityRegionException(CANNOT_FOUND_ACTIVITYREGION)).getActivityRegionId();
        long newFacilityId = patchSocialringModifyRequest.getFacilityId()
                == null ? socialring.getFacility().getFacilityId() :
                facilityRepository.findByFacilityIdAndStatus(
                                patchSocialringModifyRequest.getFacilityId(), BaseStatus.ACTIVE)
                        .orElseThrow(() -> new ActivityRegionException(CANOOT_FOUND_FACILITY)).getFacilityId();
        long newExerciseId = patchSocialringModifyRequest.getExerciseId()
                == null ? socialring.getExercise().getExerciseId() :
                exerciseRepository.findByExerciseIdAndStatus
                                (patchSocialringModifyRequest.getExerciseId(), BaseStatus.ACTIVE)
                        .orElseThrow(() -> new ExerciseException(CANNOT_FOUND_EXERCISE)).getExerciseId();

        ActivityRegion activityRegion = activityRegionRepository.findByActivityRegionIdAndStatus(newActivityRegionId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new ActivityRegionException(CANNOT_FOUND_ACTIVITYREGION));
        Facility facility = facilityRepository.findByFacilityIdAndStatus(newFacilityId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new FacilityException(CANOOT_FOUND_FACILITY));
        Exercise exercise = exerciseRepository.findByExerciseIdAndStatus(newExerciseId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new ExerciseException(CANNOT_FOUND_EXERCISE));


        //변경사항 업데이트 및 저장
        socialring.UpdateSocialringInfo(newSocialringName, newSocialringImg, newTotalRecruits, newSocialringDate, newSocialringCost,
                newComment, newCommentSimple, newGender, newLevel, activityRegion, facility, exercise);
        socialringRepository.save(socialring);

    }



}
