package com.example.likelion12.dto;

import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PostSocialringRequest {
    private String socialringName;
    private String socialringImg;
    private long activityRegionId;
    private long facilityId;
    private long exerciseId;
    private int totalRecruits;
    private LocalDate socialringDate;
    private int  socialringCost;
    private String comment;
    private String commentSimple;
    private BaseGender gender;
    private BaseLevel level;
}
