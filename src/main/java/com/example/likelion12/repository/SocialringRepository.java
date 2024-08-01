package com.example.likelion12.repository;

import com.example.likelion12.domain.Socialring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SocialringRepository extends JpaRepository<Socialring, Long> {

    // 홈화면에 보여줄 4개를 마감기한 입박한 순으로 반환
    @Query("SELECT s FROM Socialring s ORDER BY s.socialringDate ASC")
    List<Socialring> findTop4ByOrderBySocialringDate();
}
