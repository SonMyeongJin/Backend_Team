package com.example.likelion12.repository;

import com.example.likelion12.domain.Member;
import com.example.likelion12.domain.base.BaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndStatus(String email, BaseStatus status);
    Optional<Member> findByMemberIdAndStatus(Long memberId, BaseStatus status);
}
