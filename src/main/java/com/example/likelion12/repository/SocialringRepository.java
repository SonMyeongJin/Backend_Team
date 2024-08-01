package com.example.likelion12.repository;

import com.example.likelion12.domain.Socialring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SocialringRepository extends JpaRepository<Socialring, Long> {

    // 홈화면에 보여줄 4개를 마감기한 입박한 순으로 반환
    @Query(value = "SELECT * FROM socialring ORDER BY socialring_date ASC LIMIT 4", nativeQuery = true)
    Optional<List<Socialring>> findTop4ByOrderBySocialringDate();
}
