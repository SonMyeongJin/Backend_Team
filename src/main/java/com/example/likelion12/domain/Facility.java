package com.example.likelion12.domain;

import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.domain.base.BaseTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Facility extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id", nullable = false)
    private Long facilityId;

    @Column(nullable = false)
    private String facilityName;

    @Column(nullable = false)
    private String facilityCity; // 시 정보

    private String facilityGu; // 구 정보

    private String facilityDong; // 동 정보

    private String facilityPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "facility")
    private List<Review> reviewList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "facility")
    private List<Socialring> socialringList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "facility")
    private List<Crew> crewList = new ArrayList<>();
}
