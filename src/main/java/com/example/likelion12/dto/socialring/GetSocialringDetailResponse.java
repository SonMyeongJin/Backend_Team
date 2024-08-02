package com.example.likelion12.dto.socialring;

import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import com.example.likelion12.domain.base.BaseRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetSocialringDetailResponse {

    /**
     * 소셜링 상세 조회 response dto
     */
    private BaseRole memberRole;
    private String socialringName;
    private String socialringImg;
    private String activityRegionName;
    private String facilityName;
    private String exerciseName;
    private int totalRecruits;
    private LocalDate socialringDate;
    private int socialringCost;
    private String comment;
    private String commentSimple;
    private BaseGender gender;
    private BaseLevel level;
    private List<GetSocialringDetailResponse.Socialrings> members;
    private List<GetSocialringDetailResponse.Recommands> recommands;

    @Getter
    @AllArgsConstructor
    public static class Socialrings {
        private String memberImg;
    }

    @Getter
    @AllArgsConstructor
    public static class Recommands{
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
}
