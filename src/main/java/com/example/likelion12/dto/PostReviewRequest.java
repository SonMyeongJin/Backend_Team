package com.example.likelion12.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostReviewRequest {
    private Long facilityId;
    private int ranking;
    private String comment;
}
