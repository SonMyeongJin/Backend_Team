package com.example.likelion12.dto.socialring;

import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PostSocialringRequest {
    /**
     * 소셜링 등록 request dto
     */
    private String socialringName;
    private MultipartFile socialringImg;
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
