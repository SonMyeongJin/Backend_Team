package com.example.likelion12.dto.crew;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCrewSearchFilterResponse {
    /**
     * 크루 검색결과 필터링 response dto
     */
    private Long crewId;
    private String crewName;
    private String crewImg;
    private int crewCost;
    private String activityRegionName;
    private String exerciseName;
    private int currentRecruits;
    private int totalRecruits;
    private String commentSimple;
}
