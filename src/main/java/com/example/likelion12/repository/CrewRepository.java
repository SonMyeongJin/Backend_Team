package com.example.likelion12.repository;

import com.example.likelion12.domain.Crew;
import com.example.likelion12.domain.base.BaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CrewRepository extends JpaRepository<Crew, Long> {

    // "크루"가 가지고 있는 "크루_멤버"가 많은 순으로 4개 반환 (홈화면 보여주기용)
    @Query("SELECT c FROM Crew c LEFT JOIN c.memberCrewList m GROUP BY c ORDER BY COUNT(m) DESC LIMIT 4")
    Optional<List<Crew>> findTop4ByMemberCrewListSize();

    Optional<Crew> findByCrewNameAndStatus(String crewName, BaseStatus status);

    List<Crew> findAllByStatus(BaseStatus baseStatus);

    // 특정 exercise_id 값을 가진 상위 3개의 Crew를 반환하는 쿼리
    @Query("SELECT c FROM Crew c WHERE c.exercise.id = :exerciseId AND c.status = :status ORDER BY c.crewId ASC")
    List<Crew> findTop3ByExerciseIdAndStatus(@Param("exerciseId") Long exerciseId, @Param("status") BaseStatus status, Pageable pageable);

    /** 정적쿼리가 동적쿼리보다 빠르기때문에 정적쿼리
     * JPA에서 제공하는 페이징 이용!*/
    @Query("SELECT c FROM Crew c " +
            "WHERE (:keyWord IS NULL OR c.crewName LIKE %:keyWord% " +
            "OR c.commentSimple LIKE %:keyWord%) " +
            "AND (:exerciseId IS NULL OR (c.exercise.id = :exerciseId AND c.exercise.status = :status)) " +
            "AND (:activityRegionId IS NULL OR (c.activityRegion.id = :activityRegionId AND c.activityRegion.status = :status)) " +
            "AND c.status = :status ")
    Page<Crew> searchCrew(
            @Param("keyWord") String keyWord,
            @Param("exerciseId") Long exerciseId,
            @Param("activityRegionId") Long activityRegionId,
            @Param("status") BaseStatus status,
            Pageable pageable
    );
}