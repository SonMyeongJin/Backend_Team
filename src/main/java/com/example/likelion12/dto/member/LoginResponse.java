package com.example.likelion12.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    /**
     * 카카오 소셜 로그인
     */
    private boolean memberStatus;
    private Long memberId;
    private String profileImage;
    private String nickname;
    private String email;
    private String accessToken;
}
