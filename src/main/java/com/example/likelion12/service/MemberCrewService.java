package com.example.likelion12.service;

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
    public void createMemberCrew(Member member, Crew crew){
        log.info("[MemberCrewService.createMemberCrew]");
        MemberCrew memberCrew = new MemberCrew(BaseRole.CAPTAIN,crew,member, BaseStatus.ACTIVE);
        memberCrewRepository.save(memberCrew);
    }
}
