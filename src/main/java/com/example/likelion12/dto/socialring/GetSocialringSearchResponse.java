package com.example.likelion12.dto.socialring;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
public class GetSocialringSearchResponse {

    private long socialringId;
    private String socialringName;
    private String socialringImg;
    private int totalRecruits;
    private int currentMembers;
    private LocalDate socialringDate;
    private int socialringCost;
    private String commentSimple;
    private String ActivityRegionName;

}
