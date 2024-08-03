package com.example.likelion12.service;

import com.example.likelion12.common.exception.MemberSocialringException;
import com.example.likelion12.domain.*;
import com.example.likelion12.domain.base.BaseRole;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.repository.MemberSocialringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.NOT_MEMBERSOCIALRING_CAPTAIN;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSocialringService {

    private final MemberSocialringRepository memberSocialringRepository;

    /**
     * 소셜링 등록 시 소셜링 만든 사람이 BaseRole.CAPTAIN 이 되도록
     */
    @Transactional
    public void createMemberSocialring(Member member, Socialring socialring){
        log.info("[MemberSocialringService.createMemberSocialring]");
        MemberSocialring memberSocialring = new MemberSocialring(BaseRole.CAPTAIN,socialring,member,BaseStatus.ACTIVE);
        memberSocialringRepository.save(memberSocialring);
    }

    /**
     * 소셜링 수정,삭제 시 접근하는 member가  CAPTAIN 인지 확인
     */
    public void ConfirmCaptainMemberSocialring(MemberSocialring memberSocialring){
        log.info("[MemberSocialringService.ConfirmCaptainMemberSocialring]");

        if (!BaseRole.CAPTAIN.equals(memberSocialring.getRole())) {
            throw new MemberSocialringException(NOT_MEMBERSOCIALRING_CAPTAIN);
        }
    }

    /**
     * 소셜링 참여 시 BaseRole.CREW로 들어가도록
     */
    @Transactional
    public void joinMemberSocialring(Member member, Socialring socialring){
        log.info("[MemberSocialringService.createMemberSocialring]");
        MemberSocialring memberSocialring = new MemberSocialring(BaseRole.CREW ,socialring,member, BaseStatus.ACTIVE);
        memberSocialringRepository.save(memberSocialring);
    }

}
