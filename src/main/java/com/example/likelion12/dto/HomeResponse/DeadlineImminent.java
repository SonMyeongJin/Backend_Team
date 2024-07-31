package com.example.likelion12.dto.HomeResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeadlineImminent {
    private String socialring_name;
    private String socialring_img;
    private String socialring_date;
    private Long active_area_id;
    private Integer total_recruits;
    private Integer socialring_cost;
    private String comment_simple;
}
