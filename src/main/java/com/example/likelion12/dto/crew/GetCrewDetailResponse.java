package com.example.likelion12.dto.crew;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetCrewDetailResponse {
    /**
     * 크루 상세 조회 response dto
     */
    private String crewName;
    private String crewImg;
    private String activityRegionName;
    private String exerciseName;
    private int totalRecruits;
    private int crewCost;
    private List<Crews> members;
    private List<Recommands> recommands;

    @Getter
    @AllArgsConstructor
    public static class Crews{
        private String memberImg;
    }

    @Getter
    @AllArgsConstructor
    public static class Recommands{
        private Long crewId;
        private String crewName;
        private String crewImg;
        private int crewCost;
        private String activityRegionName;
        private String exerciseName;
        private int currentRecruits;
        private int totalRecruits;
    }
}
