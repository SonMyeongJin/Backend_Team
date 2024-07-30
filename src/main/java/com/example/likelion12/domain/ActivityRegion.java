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

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRegion extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_region_id", nullable = false)
    private long activityRegionId;

    @Column(nullable = false)
    private String activityRegionName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "activityRegion")
    private List<Crew> crewList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "activityRegion")
    private List<Socialring> socialringList = new ArrayList<>();
}
