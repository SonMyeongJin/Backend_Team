package com.example.likelion12.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Crew {
    private Long crew_id;
    private String crew_name;
    private String crew_img;
    private String active_area_name;
    private Integer total_recruits;
    private Integer crew_cost;
    private String comment_simple;
}