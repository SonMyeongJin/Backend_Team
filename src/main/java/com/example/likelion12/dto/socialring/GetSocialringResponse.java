package com.example.likelion12.dto.socialring;

import com.example.likelion12.domain.base.BaseLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
public class GetSocialringResponse {

    /**
     * 소셜링 조회결과 dto
     */
    private String socialringName;
    private String socialringImg;
    private String activityRegionName;
    private String exerciseName;
    private BaseLevel level;
    private String commentSimple;
}



