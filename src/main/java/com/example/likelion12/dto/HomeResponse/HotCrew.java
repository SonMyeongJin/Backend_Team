package com.example.likelion12.dto.HomeResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HotCrew {
    private String crew_name;
    private String crew_img;
    private Long active_area_id;
    private Integer total_recruits;
    private Integer crew_cost;
    private String comment_simple;
}
