package com.example.likelion12.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCrewRequest {
    /**
     * 크루 등록 request dto
     */
    private String crewName;
    private String crewImg;
    private Long activityRegionId;
    private Long facilityId;
    private Long exerciseId;
    private int totalRecruits;
    private int crewCost;
    private String simpleComment;
    private String comment;
}
