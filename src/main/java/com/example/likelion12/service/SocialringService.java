package com.example.likelion12.service;

import com.example.likelion12.common.exception.*;
import com.example.likelion12.domain.*;
import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.dto.socialring.*;
import com.example.likelion12.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.*;
import static com.example.likelion12.domain.base.BaseStatus.DELETE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialringService {
    private final SocialringRepository socialringRepository;
    private final MemberRepository memberRepository;
    private final FacilityRepository facilityRepository;
    private final ExerciseRepository exerciseRepository;
    private final MemberSocialringService memberSocialringService;
    private final ActivityRegionRepository activityRegionRepository;
    private final MemberSocialringRepository memberSocialringRepository;

    /**
     * 소셜링 등록
     */
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

    /**
     * 소셜링 수정
     */
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

        long newActivityRegionId =
                patchSocialringModifyRequest.getActivityRegionName() == null ?
                        socialring.getActivityRegion().getActivityRegionId() :
                //리퀘스트로 들어온값이 존재하는 값인지 네임으로 찾아서 아이디값 반환 후 아이디값 수정
                activityRegionRepository.findByActivityRegionNameAndStatus(
                        patchSocialringModifyRequest.getActivityRegionName(), BaseStatus.ACTIVE)
                .orElseThrow(() -> new ActivityRegionException(CANNOT_FOUND_ACTIVITYREGION)).getActivityRegionId();
        long newFacilityId = patchSocialringModifyRequest.getFacilityName()
                == null ? socialring.getFacility().getFacilityId() :
                facilityRepository.findByFacilityNameAndStatus(
                                patchSocialringModifyRequest.getFacilityName(), BaseStatus.ACTIVE)
                        .orElseThrow(() -> new ActivityRegionException(CANOOT_FOUND_FACILITY)).getFacilityId();
        long newExerciseId = patchSocialringModifyRequest.getExerciseName()
                == null ? socialring.getExercise().getExerciseId() :
                exerciseRepository.findByExerciseNameAndStatus
                                (patchSocialringModifyRequest.getExerciseName(), BaseStatus.ACTIVE)
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

    /**
     * 소셜링 상세 조회
     */
    @Transactional
    public GetSocialringDetailResponse getSocialringDetail(Long memberId, Long socialringId) {
        log.info("[SocialringService.getSocialringDetail]");

        // 상세조회하고자 하는 소셜링
        Socialring socialring = socialringRepository.findBySocialringIdAndStatus(socialringId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new SocialringException(CANNOT_FOUND_SOCIALRING));

        // 상세조회하고자 하는 멤버의 멤버소셜링
        MemberSocialring memberSocialring = memberSocialringRepository.findByMember_MemberIdAndSocialring_SocialringIdAndStatus(memberId,
                socialringId, BaseStatus.ACTIVE).orElseThrow(() -> new MemberSocialringException(CANNOT_FOUND_MEMBERSOCIALRING));
        // 소셜링에 등록된 멤버 리스트 추출
        List<MemberSocialring> memberSocialringList = memberSocialringRepository.findBySocialring_SocialringIdAndStatus
                (socialringId, BaseStatus.ACTIVE).orElseThrow(()-> new MemberSocialringException( CANNOT_FOUND_MEMBERSOCIALRING_LIST));
        // 소셜링에 등록된 멤버 리스트에서 사진만 추출해서 반환
        List<GetSocialringDetailResponse.Socialrings> memberImgList = memberSocialringList.stream()
                .map(MemberSocialring -> new GetSocialringDetailResponse.Socialrings(memberSocialring.getMember().getMemberImg()))
                .collect(Collectors.toList());

        List<GetSocialringDetailResponse.Recommands> recommandsList = socialringRepository.findTop3ByActivityRegionIdAndStatus(
                socialring.getActivityRegion().getActivityRegionId(), BaseStatus.ACTIVE, Pageable.ofSize(3))
                .stream().map(socialrings -> new GetSocialringDetailResponse.Recommands(
                socialrings.getSocialringId(),
                socialrings.getSocialringName(),
                socialrings.getSocialringImg(),
                socialrings.getActivityRegion().getActivityRegionName(),
                socialrings.getSocialringDate(),
                socialrings.getSocialringCost(),
                socialrings.getCommentSimple(),
                socialrings.getMemberSocialringList().size(),
                socialrings.getTotalRecruits()
        )).collect(Collectors.toList());

        GetSocialringDetailResponse getSocialringDetailResponse = new GetSocialringDetailResponse(memberSocialring.getRole(),socialring.getSocialringName(),
                socialring.getSocialringImg(), socialring.getActivityRegion().getActivityRegionName(),socialring.getFacility().getFacilityName(),
                socialring.getExercise().getExerciseName(),socialring.getTotalRecruits(),socialring.getSocialringDate(),
                socialring.getSocialringCost(),socialring.getComment(),socialring.getCommentSimple(),socialring.getGender(),
                socialring.getLevel(),memberImgList,recommandsList);

        return getSocialringDetailResponse;

    }

    /**
     * 소셜링 참여하기
     */
    @Transactional
    public void joinSocialring(Long memberId, Long socialringId) {
        log.info("[SocialringService.joinSocialring]");

        //소셜링을 참여하고자 하는 멤버
        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new MemberException(CANNOT_FOUND_MEMBER));

        //참여하고자 하는 소셜링
        Socialring socialring = socialringRepository.findBySocialringIdAndStatus(socialringId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new SocialringException(CANNOT_FOUND_SOCIALRING));

        //해당 소셜링에 이미 등록되어있다면 예외처리
        if(memberSocialringRepository.existsByMember_MemberIdAndSocialring_SocialringIdAndStatus(memberId,socialringId,BaseStatus.ACTIVE)){
            throw new MemberSocialringException(ALREADY_EXIST_IN_SOCIALRING);
        }

        // 참여하려는 소셜링의 총 모집 인원 확인하기
        int totalRecruits = socialring.getTotalRecruits();

        // 현재 참여중인 소셜링원 수 확인하기
        List<MemberSocialring> memberSocialringList = memberSocialringRepository.findBySocialring_SocialringIdAndStatus(socialringId,BaseStatus.ACTIVE)
                .orElseThrow(()-> new MemberSocialringException(CANNOT_FOUND_MEMBERSOCIALRING_LIST));
        int currentSocialrings = memberSocialringList.size();

        if(totalRecruits > currentSocialrings){
            // 모집인원이 현재인원보다 많으니까 가입 가능
            memberSocialringService.joinMemberSocialring(member,socialring); // BaseRole.CREW로 멤버소셜링에 저장
        }else{
            // 같으면 전원 모집인 경우라서 가입 불가능
            throw new SocialringException(ALREADY_FULL_SOCIALRING);
        }
    }

    /**
     * 참가 예정인 소셜링
     */
    @Transactional
    public List<GetSocialringJoinStatusResponse> joinBeforeSocialring(Long memberId) {
        log.info("[SocialringService.joinBeforeSocialring]");

        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new MemberException(CANNOT_FOUND_MEMBER));

        //현재 날짜
        LocalDate nowDate = LocalDate.now();

        // 멤버의 멤버소셜링리스트 추출
        List<MemberSocialring> memberSocialringList = memberSocialringRepository.findByMember_MemberIdAndAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new MemberSocialringException(CANNOT_FOUND_MEMBERSOCIALRING));

        // 참가중인 소셜링이 없을때 예외처리
        if (memberSocialringList.isEmpty())
            throw new MemberSocialringException(N0T_EXIST_JOIN_SOCIALRING);

        // 멤버가 참여한 멤버소셜링 리스트 중에서 현재 날짜보다 더 나중인 소셜링을 추출
        List<Socialring> memberInjoinBeforeSocialrings = new ArrayList<>();
        for (MemberSocialring memberSocialring : memberSocialringList) {
            LocalDate socialringDate = memberSocialring.getSocialring().getSocialringDate();
            if (socialringDate.isAfter(nowDate)) {
                memberInjoinBeforeSocialrings.add(memberSocialring.getSocialring());
            }
        }

        //참가 예정인 소셜링이 존재하지않을떄 예외처리
        if (memberInjoinBeforeSocialrings.isEmpty())
            throw new MemberSocialringException(N0T_EXIST_JOIN_BEFORE_SOCIALRING);

        List<GetSocialringJoinStatusResponse> responseList = new ArrayList<>();
        for (Socialring socialring : memberInjoinBeforeSocialrings) {

            // 현재 참여중인 소셜링원 수 확인하기
            int currentSocialrings = memberSocialringRepository.findBySocialring_SocialringIdAndStatus(socialring.getSocialringId(), BaseStatus.ACTIVE)
                    .orElseThrow(() -> new MemberSocialringException(CANNOT_FOUND_MEMBERSOCIALRING_LIST)).size();

            GetSocialringJoinStatusResponse response = new GetSocialringJoinStatusResponse(socialring.getSocialringId(),
                    socialring.getSocialringName(), socialring.getSocialringImg(), socialring.getActivityRegion().getActivityRegionName(),
                    socialring.getSocialringDate(), socialring.getSocialringCost(), socialring.getCommentSimple(), currentSocialrings,
                    socialring.getTotalRecruits()
            );
            responseList.add(response);
        }

        return responseList;
    }
     /**
     * 소셜링 삭제하기
     */
    @Transactional
    public void deleteSocialring(Long memberId, Long socialringId) {
        log.info("[SocialringService.deleteSocialring]");

        // 소셜링을 삭제하고자 하는 멤버를 찾기
        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new MemberException(CANNOT_FOUND_MEMBER));

        // 삭제하고자 하는 소셜링이 존재하는지 확인
        Socialring socialring = socialringRepository.findBySocialringIdAndStatus(socialringId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new SocialringException(CANNOT_FOUND_SOCIALRING));

        // 소셜링을 삭제하고자 하는 멤버의 멤버소셜링을 찾기
        MemberSocialring memberSocialring = memberSocialringRepository.findByMember_MemberIdAndSocialring_SocialringIdAndStatus(memberId, socialringId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new MemberSocialringException(CANNOT_FOUND_MEMBERSOCIALRING));

        // 접근 멤버가 CAPTAIN인지 유효성 검사
        memberSocialringService.ConfirmCaptainMemberSocialring(memberSocialring);

        // 소셜링 상태를 DELETE로 변경
        socialring.setStatus(DELETE);
        socialringRepository.save(socialring);

        // 소설링이 참조하고 있는 멤버 소셜링의 상태를 전부 DELETE로 변경
            // 소셜링에 등록된 멤버 리스트 추출
        List<MemberSocialring> memberSocialringList = memberSocialringRepository.findBySocialring_SocialringIdAndStatus
                (socialringId, BaseStatus.ACTIVE).orElseThrow(()-> new MemberSocialringException( CANNOT_FOUND_MEMBERSOCIALRING_LIST));
            // 해당 리스트의 멤버의 상태를 DELETE로 변경
        for (MemberSocialring memberSocialringEntry : memberSocialringList) {
            memberSocialringEntry.setStatus(DELETE);
            memberSocialringRepository.save(memberSocialringEntry);
        }
    }

    /**
     * 소셜링 검색
     */
    public Page<GetSocialringSearchResponse> searchSocialrings(String keyWord, LocalDate socialringDate, String activityRegionName, int page, int size) {
        Long activityRegionId = getActivityRegionIdByName(activityRegionName, BaseStatus.ACTIVE);

        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> results = socialringRepository.searchSocialringsWithMemberCount(
                keyWord, socialringDate, activityRegionId, BaseStatus.ACTIVE, pageable
        );
        return results.map(result -> {
            Socialring socialring = (Socialring) result[0];
            long memberCount = (Long) result[1];
            return new GetSocialringSearchResponse(
                    socialring.getSocialringId(),
                    socialring.getSocialringName(),
                    socialring.getSocialringImg(),
                    socialring.getTotalRecruits(),
                    (int) memberCount,
                    socialring.getSocialringDate(),
                    socialring.getSocialringCost(),
                    socialring.getCommentSimple(),
                    socialring.getActivityRegion().getActivityRegionName()
            );
        });
    }

    private Long getActivityRegionIdByName(String name, BaseStatus status){
        Long activityRegionId = null;
        /** 이름으로 지역아이디 얻기 */
        if (name != null) {
            Optional<ActivityRegion> activityRegionOpt = activityRegionRepository.findByActivityRegionNameAndStatus(name, status);
            if (activityRegionOpt.isPresent()) {
                activityRegionId = activityRegionOpt.get().getActivityRegionId();
            }
        }
        return activityRegionId;
    }


}
