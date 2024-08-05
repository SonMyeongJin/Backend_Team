package com.example.likelion12.repository;

import com.example.likelion12.domain.Member;
import com.example.likelion12.domain.base.BaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndStatus(String email, BaseStatus status);
    Optional<Member> findByMemberIdAndStatus(Long memberId, BaseStatus status);
    boolean existsByEmailAndStatus(String email, BaseStatus status);

    @Modifying
    @Query("Delete From Member m Where m.memberId = :memberId")
    void deleteByMemberId(Long memberId);

}
