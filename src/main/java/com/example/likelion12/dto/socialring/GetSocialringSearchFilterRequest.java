package com.example.likelion12.dto.socialring;

import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GetSocialringSearchFilterRequest {

    /**
     * 소셜링 검색결과 필터링  request dto
     */

    private String exerciseName;
    private BaseGender gender;
    private BaseLevel level;
    private Integer socialringCostMin;
    private Integer socialringCostMax;
    private Integer totalRecruitsMin;
    private Integer totalRecruitsMax;

}
