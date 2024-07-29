package com.example.likelion12.domain;

import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.domain.base.BaseTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Crew extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_id", nullable = false)
    private long crewId;

    @Column(nullable = false)
    private String crewName;

    private String crewImg;

    @Column(nullable = false)
    private int totalRecruits;

    @Column(nullable = false)
    private int crewCost;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String commentSimple;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "crew")
    private List<MemberCrew> memberCrewList = new ArrayList<>();

    /** 체육시설 과의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    /** 활동지역 과의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "activity_region_id")
    private ActivityRegion activityRegion;

    /** 운동종목 과의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
}
