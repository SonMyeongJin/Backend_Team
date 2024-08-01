package com.example.likelion12.repository;

import com.example.likelion12.domain.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CrewRepository extends JpaRepository<Crew, Long> {

    // "크루"가 가지고 있는 "크루_멤버"가 많은 순으로 4개 반환 (홈화면 보여주기용)
    @Query("SELECT c FROM Crew c LEFT JOIN c.memberCrewList m GROUP BY c ORDER BY COUNT(m) DESC LIMIT 4")
    Optional<List<Crew>> findTop4ByMemberCrewListSize();
}