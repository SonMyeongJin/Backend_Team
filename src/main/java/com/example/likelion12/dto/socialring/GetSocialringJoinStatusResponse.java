package com.example.likelion12.dto.socialring;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class GetSocialringJoinStatusResponse {

    /**
     * 소셜링 참여상태 response dto
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
