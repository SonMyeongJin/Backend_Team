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

import static jakarta.persistence.FetchType.LAZY;

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

    /** exercise 와의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    public Member(String memberName, String email, String memberImg, BaseGender gender, BaseStatus status, Exercise exercise){
        this.memberName = memberName;
        this.email = email;
        this.memberImg = memberImg;
        this.gender = gender;
        this.status = status;
        this.exercise = exercise;
    }
}
