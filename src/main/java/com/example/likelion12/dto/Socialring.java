package com.example.likelion12.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Socialring {
    private Long socialring_id;
    private String socialring_name;
    private String socialring_img;
    private LocalDate socialring_date;
    private String active_area_name;
    private Integer total_recruits;
    private Integer socialring_cost;
    private String comment_simple;
}
