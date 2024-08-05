package com.example.likelion12.dto.crew;

import com.example.likelion12.domain.base.BaseLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCrewInquiryResponse {
    /**
     * 크루 조회 response dto
     */
    private String crewName;
    private String crewImg;
    private String activityRegionName;
    private String exerciseName;
    private BaseLevel level;
    private String commentSimple;
}
