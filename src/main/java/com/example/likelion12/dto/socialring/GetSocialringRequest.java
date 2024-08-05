package com.example.likelion12.dto.socialring;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetSocialringRequest {

    /**
     * 소셜링 요청 dto
     */
    private int page;
    private int recordSize;
    private int pageSize;

}
