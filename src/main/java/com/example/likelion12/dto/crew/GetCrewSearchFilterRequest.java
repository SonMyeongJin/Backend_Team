package com.example.likelion12.dto.crew;

import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCrewSearchFilterRequest {

    /**
     * 크루 검색결과 필터링 request dto
     */
    private BaseGender gender;
    private BaseLevel level;
    private Integer crewCostMin;
    private Integer crewCostMax;
    private Integer totalRecruitsMin;
    private Integer totalRecruitsMax;
}
