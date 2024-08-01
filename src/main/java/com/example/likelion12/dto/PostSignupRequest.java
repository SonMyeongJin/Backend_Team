package com.example.likelion12.dto;

import com.example.likelion12.domain.base.BaseGender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSignupRequest {
    /**
     * 회원가입 request dto
     */
    private String nickname;
    private String profileImage;
    private String email;
    private BaseGender gender;
    private String exercise;
}
