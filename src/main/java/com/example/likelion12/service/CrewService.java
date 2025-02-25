package com.example.likelion12.service;

import com.example.likelion12.common.exception.*;
import com.example.likelion12.domain.*;
import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import com.example.likelion12.domain.base.BaseRole;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.dto.crew.*;
import com.example.likelion12.repository.*;
import com.example.likelion12.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final MemberCrewRepository memberCrewRepository;
    private final S3Uploader s3Uploader;

    /**
     * 크루 등록
     */
    @Transactional
    public PostCrewResponse createCrew(Long memberId, PostCrewRequest postCrewRequest) throws IOException {
        log.info("[CrewService.createCrew]");

        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new MemberException(CANNOT_FOUND_MEMBER));

        String crewName = postCrewRequest.getCrewName();
        String activityRegionName = postCrewRequest.getActivityRegionName();
        String facilityName = postCrewRequest.getFacilityName();
        String exerciseName = postCrewRequest.getExerciseName();
        int totalRecruits = postCrewRequest.getTotalRecruits();
        int crewCost = postCrewRequest.getCrewCost();
        String simpleComment = postCrewRequest.getSimpleComment();
        String comment = postCrewRequest.getComment();
        BaseGender gender = postCrewRequest.getGender();
        BaseLevel level = postCrewRequest.getLevel();

        ActivityRegion activityRegion = activityRegionRepository.findByActivityRegionNameAndStatus(activityRegionName, BaseStatus.ACTIVE)
                .orElseThrow(()-> new ActivityRegionException(CANNOT_FOUND_ACTIVITYREGION));

        Facility facility = facilityRepository.findByFacilityNameAndStatus(facilityName, BaseStatus.ACTIVE)
                .orElseThrow(()-> new FacilityException(CANOOT_FOUND_FACILITY));

        Exercise exercise = exerciseRepository.findByExerciseNameAndStatus(exerciseName,BaseStatus.ACTIVE)
                .orElseThrow(()-> new ExerciseException(CANNOT_FOUND_EXERCISE));

        String crewImg = s3Uploader.uploadFileToS3(postCrewRequest.getCrewImg(),"crew/");
        Crew crew = new Crew(crewName, crewImg, totalRecruits, crewCost,simpleComment,comment
                ,gender,level,activityRegion,facility, exercise, BaseStatus.ACTIVE);
        crewRepository.save(crew);
        //크루를 만든 사람이 CAPTAIN 이 되도록
        memberCrewService.createMemberCaptain(member,crew);
        return new PostCrewResponse(crew.getCrewId());
    }


    /**
     * 크루  조회
     */
    public List<GetCrewInquiryResponse> getCrewInquiries(int memberId, int page) {
        log.info("[CrewService.getCrewInquiries]");

        List<Crew> allCrews = crewRepository.findAllByStatus(BaseStatus.ACTIVE);
        List<GetCrewInquiryResponse> getCrewInquiryResponses = new ArrayList<>();

        // offset과 limit 계산
        int offset = (page - 1) * 9;
        int recordSize = 9 ;

        // 페이징 처리된 크루 목록 생성
        for (int i = offset; i < Math.min(offset + recordSize, allCrews.size()); i++) {

            Crew crew = allCrews.get(i);

            GetCrewInquiryResponse response = new GetCrewInquiryResponse(
                    crew.getCrewName(),
                    crew.getCrewImg(),
                    crew.getActivityRegion().getActivityRegionName(),
                    crew.getExercise().getExerciseName(),
                    crew.getLevel(),
                    crew.getCommentSimple()
            );

            getCrewInquiryResponses.add(response);
        }

        return getCrewInquiryResponses;
    }

    /**
     * 크루 상세 조회
     */
    public GetCrewDetailResponse getCrewDetail(Long memberId, String crewName){
        log.info("[CrewService.getCrewDetail]");

        Crew crew = crewRepository.findByCrewNameAndStatus(crewName, BaseStatus.ACTIVE)
                .orElseThrow(()->new CrewException(CANNOT_FOUND_CREW));
        MemberCrew memberCrew = memberCrewRepository.findByMember_MemberIdAndCrew_CrewIdAndStatus(memberId, crew.getCrewId(), BaseStatus.ACTIVE)
                .orElseThrow(()-> new CrewException(CANNOT_FOUND_MEMBERCREW));

        // 가입한 멤버 리스트 추출
        List<MemberCrew> memberCrewList = memberCrewRepository.findByCrew_CrewIdAndStatus(crew.getCrewId(), BaseStatus.ACTIVE)
                .orElseThrow(()-> new MemberCrewException(CANNOT_FOUND_MEMBERCREW_LIST));
        // 그 멤버 중에서 사진만 추출해서 반환
        List<GetCrewDetailResponse.Crews> memberImgList = memberCrewList.stream()
                .map(MemberCrew -> new GetCrewDetailResponse.Crews(memberCrew.getMember().getMemberImg()))
                .collect(Collectors.toList());

        List<GetCrewDetailResponse.Recommands> recommandsList = crewRepository.findTop3ByExerciseIdAndStatus(
                crew.getExercise().getExerciseId(),
                BaseStatus.ACTIVE,
                Pageable.ofSize(3)
        ).stream().map(crews -> new GetCrewDetailResponse.Recommands(
                crews.getCrewId(),
                crews.getCrewName(),
                crews.getCrewImg(),
                crews.getCrewCost(),
                crews.getActivityRegion().getActivityRegionName(),
                crews.getExercise().getExerciseName(),
                crews.getMemberCrewList().size(),
                crews.getTotalRecruits()
        )).collect(Collectors.toList());

        GetCrewDetailResponse getCrewDetailResponse = new GetCrewDetailResponse(memberCrew.getRole(), crew.getCrewName(),
                crew.getCrewImg(), crew.getActivityRegion().getActivityRegionName(),crew.getExercise().getExerciseName(),
                crew.getTotalRecruits(),crew.getCrewCost(),crew.getComment(),memberImgList,recommandsList);

        return getCrewDetailResponse;
    }

    /**
     * 크루 참여하기
     */
    @Transactional
    public void joinCrew(Long memberId, String crewName){
        log.info("[CrewService.joinCrew]");
        // 참여하려는 member 찾기
        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new MemberException(CANNOT_FOUND_MEMBER));

        //크루 찾기
        Crew crew = crewRepository.findByCrewNameAndStatus(crewName, BaseStatus.ACTIVE)
                .orElseThrow(()->new CrewException(CANNOT_FOUND_CREW));

        //크루 아이디로 참여하려는 크루 찾기
        if(memberCrewRepository.existsByMember_MemberIdAndCrew_CrewIdAndStatus(memberId,crew.getCrewId(), BaseStatus.ACTIVE)){
            throw new MemberCrewException(ALREADY_EXIST_IN_CREW);
        }

        // 참여하려는 크루의 총 모집 인원 확인하기
        int totalRecruits = crew.getTotalRecruits();

        // 현재 참여중인 크루원 수 확인하기
        List<MemberCrew> memberCrewList = memberCrewRepository.findByCrew_CrewIdAndStatus(crew.getCrewId(), BaseStatus.ACTIVE)
                .orElseThrow(()->new MemberCrewException(CANNOT_FOUND_MEMBERCREW_LIST));
        int currentCrews = memberCrewList.size();

        if(totalRecruits > currentCrews){
            // 모집인원이 현재인원보다 많으니까 가입 가능
            memberCrewService.createMemberCrew(member,crew); // member_crew 테이블에 crew 로 저장
        }else{
            // 같으면 전원 모집인 경우라서 가입 불가능
            throw new CrewException(ALREADY_FULL_CREW);
        }
    }

    /**
     * 크루 검색결과 필터링
     */
    @Transactional
    public List<GetCrewSearchFilterResponse> searchFilterCrew(Long memberId, GetCrewSearchFilterRequest getCrewSearchFilterRequest) {
        log.info("[CrewService.searchFilterCrew]");

        // 전체 소셜링 목록 조회
        List<Crew> allCrews = crewRepository.findAllByStatus(BaseStatus.ACTIVE);
        List<GetCrewSearchFilterResponse> responseList = new ArrayList<>();

        // 요청값의 범위 확인 후 필터링
        for (Crew crew : allCrews) {
            boolean matchesCriteria = true;

            if (matchesCriteria && getCrewSearchFilterRequest.getGender() != null) {
                if (!crew.getGender().equals(getCrewSearchFilterRequest.getGender())) {
                    matchesCriteria = false;
                }
            }
            if (matchesCriteria && getCrewSearchFilterRequest.getLevel() != null) {
                if (!crew.getLevel().equals(getCrewSearchFilterRequest.getLevel())) {
                    matchesCriteria = false;
                }
            }

            // 최소, 최대 범위가 둘 다 들어왔을 때
            if (matchesCriteria && getCrewSearchFilterRequest.getCrewCostMin() != null && getCrewSearchFilterRequest.getCrewCostMax() != null) {
                if (!(crew.getCrewCost() >= getCrewSearchFilterRequest.getCrewCostMin()
                        && crew.getCrewCost() <= getCrewSearchFilterRequest.getCrewCostMax())) {
                    matchesCriteria = false;
                }
            }
            // 최소 범위만 들어왔을 때
            else if (matchesCriteria && getCrewSearchFilterRequest.getCrewCostMin() != null) {
                if (crew.getCrewCost() < getCrewSearchFilterRequest.getCrewCostMin()) {
                    matchesCriteria = false;
                }
            }
            // 최대 범위만 들어왔을 때
            else if (matchesCriteria && getCrewSearchFilterRequest.getCrewCostMax() != null) {
                if (crew.getCrewCost() > getCrewSearchFilterRequest.getCrewCostMax()) {
                    matchesCriteria = false;
                }
            }

            // 최소, 최대 모집 인원 범위가 둘 다 들어왔을 때
            if (matchesCriteria && getCrewSearchFilterRequest.getTotalRecruitsMin() != null && getCrewSearchFilterRequest.getTotalRecruitsMax() != null) {
                if (!(crew.getTotalRecruits() >= getCrewSearchFilterRequest.getTotalRecruitsMin()
                        && crew.getTotalRecruits() <= getCrewSearchFilterRequest.getTotalRecruitsMax())) {
                    matchesCriteria = false;
                }
            }
            // 최소 모집 인원만 들어왔을 때
            else if (matchesCriteria && getCrewSearchFilterRequest.getTotalRecruitsMin() != null) {
                if (crew.getTotalRecruits() < getCrewSearchFilterRequest.getTotalRecruitsMin()) {
                    matchesCriteria = false;
                }
            }
            // 최대 모집 인원만 들어왔을 때
            else if (matchesCriteria && getCrewSearchFilterRequest.getTotalRecruitsMax() != null) {
                if (crew.getTotalRecruits() > getCrewSearchFilterRequest.getTotalRecruitsMax()) {
                    matchesCriteria = false;
                }
            }

            // 현재 참여중인 크루원 수 확인하기
            int currentCrews = memberCrewRepository.findByCrew_CrewIdAndStatus(crew.getCrewId(), BaseStatus.ACTIVE)
                    .orElseThrow(()->new MemberCrewException(CANNOT_FOUND_MEMBERCREW_LIST)).size();

            // 조건에 맞으면 응답 리스트에 추가
            if (matchesCriteria) {
                GetCrewSearchFilterResponse response =
                        new GetCrewSearchFilterResponse(crew.getCrewId(),crew.getCrewName(),
                                crew.getCrewImg(),crew.getCrewCost(),crew.getActivityRegion().getActivityRegionName(),
                                crew.getExercise().getExerciseName(),currentCrews,crew.getTotalRecruits(),
                                crew.getCommentSimple());
                responseList.add(response);
            }
        }

        // 해당하는 크루 리스트가 하나도 없을 때 예외 처리
        if (responseList.isEmpty()) {
            throw new CrewException(CANNOT_FOUND_CREW);
        }

        return responseList;
      
    }
   
     /** 
     * 크루 삭제하기
     */
    @Transactional
    public void deleteCrew(Long memberId, String crewName) {
        log.info("[CrewService.deleteCrew]");

        // 크루를 삭제하고자 하는  member
        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new MemberException(CANNOT_FOUND_MEMBER));

        //삭제하고자 하는 크루
        Crew crew = crewRepository.findByCrewNameAndStatus(crewName, BaseStatus.ACTIVE)
                .orElseThrow(()->new CrewException(CANNOT_FOUND_CREW));

        //삭제하고자 하는 크루의 멤버크루
        //해당크루와 관계없음(해당크루에 등록되있지 않음), 멤버크루가 존재하지않음
        MemberCrew memberCrew = memberCrewRepository.findByMember_MemberIdAndCrew_CrewIdAndStatus( memberId, crew.getCrewId(), BaseStatus.ACTIVE)
                .orElseThrow(()->new MemberCrewException(NOT_CREW_MEMBERCREW));

        //멤버가 CAPTAIN 권한인지 유효성 검사
        memberCrewService.ConfirmCaptainMemberCrew(memberCrew);

        //삭제하고자 하는 크루의 멤버크루리스트
        List<MemberCrew> memberCrewList = memberCrewRepository.findByCrew_CrewIdAndStatus(crew.getCrewId(), BaseStatus.ACTIVE)
                .orElseThrow(()->new MemberCrewException(CANNOT_FOUND_MEMBERCREW_LIST));

        // 멤버 크루리스트 삭제
        for(MemberCrew membercrew: memberCrewList) {
            membercrew.DeleteMemberCrewInfo(BaseStatus.DELETE);
            memberCrewRepository.save(membercrew);
        }
        //크루 삭제
        crew.DeleteCrewInfo(BaseStatus.DELETE);
        crewRepository.save(crew);
    }

    /**
     * 크루 탈퇴하기
     */
    @Transactional
    public void cancelCrew(Long memberId, String crewName) {
        log.info("[CrewService.cancelCrew]");

        // 크루를 탈퇴하고자 하는  member
        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new MemberException(CANNOT_FOUND_MEMBER));

        //탈퇴하고자 하는 크루
        Crew crew = crewRepository.findByCrewNameAndStatus(crewName, BaseStatus.ACTIVE)
                .orElseThrow(()->new CrewException(CANNOT_FOUND_CREW));

        //탈퇴하고자 하는 크루의 멤버크루
        //해당크루와 관계없음(해당크루에 등록되있지 않음), 멤버크루가 존재하지않음
        MemberCrew memberCrew = memberCrewRepository.findByMember_MemberIdAndCrew_CrewIdAndStatus( memberId, crew.getCrewId(), BaseStatus.ACTIVE)
                .orElseThrow(()->new MemberCrewException(NOT_CREW_MEMBERCREW));

        //CAPTAIN일 경우 예외처리 --> 크루삭제
        if (BaseRole.CAPTAIN.equals(memberCrew.getRole())){
            throw new MemberCrewException(CANNOT_CREW_CANCEL);
        }
        //크루 탈퇴
        else {
            //멤버크루 삭제
            memberCrew.DeleteMemberCrewInfo(BaseStatus.DELETE);
            memberCrewRepository.save(memberCrew);
       }

    }

    /**
     * 참여중인 크루 조회하기
     */
    @Transactional
    public List<GetJoinCrewResponse> getJoinCrew(Long memberId) {
        log.info("[CrewService.getJoinCrew]");

        // 멤버Id로 멤버찾고
        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new MemberException(CANNOT_FOUND_MEMBER));

        // 멤버가 가지고 있는 멤버크루를 리스트로 가져오고
        List<MemberCrew> memberCrewList = member.getMemberCrewList();

        // 그 멤버크루로 속해있는 크루들을 가져와서 필요한 정보만 dto에 담고
        List<GetJoinCrewResponse> joinCrewResponses = memberCrewList.stream()
                .map(memberCrew -> {
                    Crew crew = memberCrew.getCrew();
                    return new GetJoinCrewResponse(
                            crew.getCrewName(),
                            crew.getCrewImg(),
                            crew.getCommentSimple(),
                            crew.getActivityRegion().getActivityRegionName(),
                            crew.getExercise().getExerciseName(),
                            crew.getLevel()
                    );
                })
                .collect(Collectors.toList());

        // 반환하기
        return joinCrewResponses;
    }

    /**
     * 크루 검색
     */
    public Page<GetCrewSearchResponse> searchCrews(String keyWord, String activityRegionName, String exerciseName, int page, int size) {
        Long activityRegionId = getActivityRegionIdByName(activityRegionName, BaseStatus.ACTIVE);
        Long exerciseId = getActivityExerciseIdByName(exerciseName, BaseStatus.ACTIVE);

        Pageable pageable = PageRequest.of(page, size);
        Page<Crew> results = crewRepository.searchCrew(
                keyWord, exerciseId, activityRegionId, BaseStatus.ACTIVE, pageable
        );
        return results.map(crew -> {
            return new GetCrewSearchResponse(
                    crew.getCrewId(),
                    crew.getCrewName(),
                    crew.getCrewImg(),
                    crew.getActivityRegion().getActivityRegionName(),
                    crew.getCommentSimple(),
                    crew.getLevel(),
                    crew.getExercise().getExerciseName()
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

    private Long getActivityExerciseIdByName(String name, BaseStatus status){
        Long ExerciseId = null;
        /** 이름으로 활동아이디 얻기 */
        if (name != null) {
            Optional<Exercise> activityRegionOpt = exerciseRepository.findByExerciseNameAndStatus(name, status);
            if (activityRegionOpt.isPresent()) {
                ExerciseId = activityRegionOpt.get().getExerciseId();
            }
        }
        return ExerciseId;
    }
}
