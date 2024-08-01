package com.example.likelion12.repository;


import com.example.likelion12.domain.Socialring;
import com.example.likelion12.domain.base.BaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialringRepository extends JpaRepository<Socialring, Long> {

    Optional<Socialring> findBySocialringIdAndStatus(Long socialringId, BaseStatus status);
}
