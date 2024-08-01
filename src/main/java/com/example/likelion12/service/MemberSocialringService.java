package com.example.likelion12.service;

import com.example.likelion12.domain.*;
import com.example.likelion12.domain.base.BaseRole;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.repository.MemberSocialringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSocialringService {

    private final MemberSocialringRepository memberSocialringRepository;

    /**
     * 소셜링 등록 시 소셜링 만든 사람이 CAPTAIN 이 되도록
     */
    @Transactional
    public void createMemberSocialring(Member member, Socialring socialring){
        log.info("[MemberSocialringService.createMemberSocialring]");
        MemberSocialring memberSocialring = new MemberSocialring(BaseRole.CAPTAIN,socialring,member,BaseStatus.ACTIVE);
        memberSocialringRepository.save(memberSocialring);
    }
}
