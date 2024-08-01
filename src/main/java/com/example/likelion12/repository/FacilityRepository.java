package com.example.likelion12.repository;

import com.example.likelion12.domain.Facility;
import com.example.likelion12.domain.base.BaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    Optional<Facility> findByFacilityIdAndStatus(Long facilityId, BaseStatus status);
}
