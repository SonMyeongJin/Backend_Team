package com.example.likelion12.repository;

import com.example.likelion12.domain.Socialring;
import com.example.likelion12.domain.base.BaseStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface SocialringRepository extends JpaRepository<Socialring, Long> {

    // 홈화면에 보여줄 4개를 마감기한 입박한 순으로 반환
    @Query(value = "SELECT * FROM socialring ORDER BY socialring_date ASC LIMIT 4", nativeQuery = true)
    Optional<List<Socialring>> findTop4ByOrderBySocialringDate();

    Optional<Socialring> findBySocialringIdAndStatus(Long socialringId, BaseStatus status);

    // 특정 activity_region_id 값을 가진 상위 3개의 Socialring를 반환하는 쿼리
    @Query("SELECT c FROM Socialring c WHERE c.activityRegion.id = :activityRegionId AND c.status = :status ORDER BY c.socialringId ASC")
    List<Socialring> findTop3ByActivityRegionIdAndStatus(@Param("activityRegionId") Long activityRegionId, @Param("status") BaseStatus status, Pageable pageable);

}
