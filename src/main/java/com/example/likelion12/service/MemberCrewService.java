package com.example.likelion12.service;

import com.example.likelion12.common.exception.MemberCrewException;
import com.example.likelion12.domain.Crew;
import com.example.likelion12.domain.Member;
import com.example.likelion12.domain.MemberCrew;
import com.example.likelion12.domain.base.BaseRole;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.repository.MemberCrewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.NOT_MEMBERCREW_CAPTAIN;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCrewService {

    private final MemberCrewRepository memberCrewRepository;

    /**
     * 크루 등록 시 크루 만든 사람이 CAPTAIN 이 되도록
     */
    @Transactional
    public void createMemberCaptain(Member member, Crew crew){
        log.info("[MemberCrewService.createMemberCaptain]");
        MemberCrew memberCrew = new MemberCrew(BaseRole.CAPTAIN,crew,member, BaseStatus.ACTIVE);
        memberCrewRepository.save(memberCrew);
    }

    /**
     * 크루 참여 시 CREW 로 들어가도록
     */
    @Transactional
    public void createMemberCrew(Member member, Crew crew){
        log.info("[MemberCrewService.createMemberCrew]");
        MemberCrew memberCrew = new MemberCrew(BaseRole.CREW ,crew,member, BaseStatus.ACTIVE);
        memberCrewRepository.save(memberCrew);
    }

    /**
     * 크루 수정,삭제 시 접근하는 member가 CAPTAIN 인지 확인
     */
    public boolean ConfirmCaptainMemberCrew(MemberCrew memberCrew) {
        log.info("[MemberCrewService.ConfirmCaptainMemberCrew]");

        if (!BaseRole.CAPTAIN.equals(memberCrew.getRole())) {
            throw new MemberCrewException(NOT_MEMBERCREW_CAPTAIN);
        }
        return true;
    }
}
