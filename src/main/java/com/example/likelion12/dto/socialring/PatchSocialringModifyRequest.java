package com.example.likelion12.dto.socialring;

import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PatchSocialringModifyRequest {
    /**
     * 소셜링 수정 request dto
     */
    private String socialringName;
    private MultipartFile socialringImg;
    private String activityRegionName;
    private String facilityName;
    private String exerciseName;
    private Integer totalRecruits;
    private LocalDate socialringDate;
    private Integer  socialringCost;
    private String comment;
    private String commentSimple;
    private BaseGender gender;
    private BaseLevel level;
}
