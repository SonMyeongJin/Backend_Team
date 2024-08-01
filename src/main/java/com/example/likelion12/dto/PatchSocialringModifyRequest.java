package com.example.likelion12.dto;

import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PatchSocialringModifyRequest {
    private String socialringName;
    private String socialringImg;
    private Long activityRegionId;
    private Long facilityId;
    private Long exerciseId;
    private Integer totalRecruits;
    private LocalDate socialringDate;
    private Integer  socialringCost;
    private String comment;
    private String commentSimple;
    private BaseGender gender;
    private BaseLevel level;
}
