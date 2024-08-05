package com.example.likelion12.dto.crew;

import com.example.likelion12.domain.base.BaseLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetCrewSearchResponse {

    private long crewId;
    private String crewName;
    private String crewImg;
    private String activeAreaName;
    private String commentSimple;
    private BaseLevel level;
    private String exerciseName;
}
