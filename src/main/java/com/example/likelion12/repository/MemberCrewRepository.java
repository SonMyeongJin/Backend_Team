package com.example.likelion12.repository;

import com.example.likelion12.domain.MemberCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCrewRepository extends JpaRepository<MemberCrew, Long> {

}
