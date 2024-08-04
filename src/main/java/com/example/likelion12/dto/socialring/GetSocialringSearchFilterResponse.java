package com.example.likelion12.dto.socialring;

import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import com.example.likelion12.domain.base.BaseRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class GetSocialringSearchFilterResponse {

    /**
     * 소셜링 검색결과필터링 response dto
     */
    private Long socialringId;
    private String socialringName;
    private String socialringImg;
    private String activityRegionName;
    private LocalDate socialringDate;
    private int socialringCost;
    private String commentSimple;
    private int currentRecruits;
    private int totalRecruits;

}
