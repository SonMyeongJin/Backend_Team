package com.example.likelion12.domain;

import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.domain.base.BaseTime;
import com.example.likelion12.domain.base.BaseGender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private long memberId;

    @Column(nullable = false)
    private String memberName; // 카카오 이름

    @Column(nullable = false)
    private String email; // 카카오 이메일

    @Column(nullable = false)
    private String memberImg; // 카카오 프로필 사진

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseGender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Review> reviewList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<MemberSocialring> memerSocialringList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<MemberCrew> memberCrewList = new ArrayList<>();

}
