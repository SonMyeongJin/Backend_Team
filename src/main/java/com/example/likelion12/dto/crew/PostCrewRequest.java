package com.example.likelion12.dto.crew;

import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class PostCrewRequest {
    /**
     * 크루 등록 request dto
     */
    private String crewName;
    private MultipartFile crewImg;
    private Long activityRegionId;
    private Long facilityId;
    private Long exerciseId;
    private int totalRecruits;
    private int crewCost;
    private String simpleComment;
    private String comment;
    private BaseGender gender;
    private BaseLevel level;
}
