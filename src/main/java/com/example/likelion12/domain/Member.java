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
    private String memberName;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String password;

    private String memberImg;

    @Column(nullable = false)
    private String memberPhone;

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
