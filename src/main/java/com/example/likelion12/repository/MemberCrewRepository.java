package com.example.likelion12.repository;

import com.example.likelion12.domain.MemberCrew;
import com.example.likelion12.domain.base.BaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberCrewRepository extends JpaRepository<MemberCrew, Long> {

    Optional<MemberCrew> findByMember_MemberIdAndCrew_CrewIdAndStatus(Long memberId, Long crewId, BaseStatus status);
    Optional<List<MemberCrew>> findByCrew_CrewIdAndStatus(Long crewId, BaseStatus status);
}
