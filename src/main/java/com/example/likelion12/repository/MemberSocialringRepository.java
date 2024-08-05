package com.example.likelion12.repository;

import com.example.likelion12.domain.MemberSocialring;
import com.example.likelion12.domain.base.BaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberSocialringRepository extends JpaRepository<MemberSocialring, Long> {

    Optional<MemberSocialring> findByMember_MemberIdAndSocialring_SocialringIdAndStatus(Long memberId, Long socialringId, BaseStatus status);
    Optional<List<MemberSocialring>> findBySocialring_SocialringIdAndStatus(Long socialringId, BaseStatus baseStatus);
    boolean existsByMember_MemberIdAndSocialring_SocialringIdAndStatus(Long memberId, Long socialringId, BaseStatus status);
    Optional<List<MemberSocialring>> findByMember_MemberIdAndAndStatus(Long memberId, BaseStatus baseStatus);
}


