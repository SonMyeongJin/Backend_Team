package com.example.likelion12.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSignupResponse {
    /**
     * 회원가입 response dto
     */
    private Long memberId;
    private String accessToken;
}
