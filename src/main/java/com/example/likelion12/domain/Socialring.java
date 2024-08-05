package com.example.likelion12.domain;

import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseLevel;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.domain.base.BaseTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Socialring extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "socialring_id", nullable = false)
    private long socialringId;

    @Column(nullable = false)
    private String socialringName;

    private String socialringImg;

    @Column(nullable = false)
    private int totalRecruits;

    @Column(nullable = false)
    private LocalDate socialringDate;

    @Column(nullable = false)
    private int socialringCost;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String commentSimple;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseGender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseLevel level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "socialring")
    private List<MemberSocialring> memberSocialringList = new ArrayList<>();

    /** 체육시설 과의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    /** 활동지역 과의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "activity_region_id")
    private ActivityRegion activityRegion;

    /** 운동종목 과의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    public Socialring(String socialringName, String socialringImg, int totalRecruits, LocalDate socialringDate,
                      int socialringCost, String comment, String commentSimple, BaseGender gender, BaseLevel level,
                      ActivityRegion activityRegion, Facility facility, Exercise exercise, BaseStatus baseStatus) {
        this.socialringName = socialringName;
        this.socialringImg = socialringImg;
        this.totalRecruits = totalRecruits;
        this.socialringDate = socialringDate;
        this.socialringCost = socialringCost;
        this.commentSimple = commentSimple;
        this.comment = comment;
        this.gender = gender;
        this.level = level;
        this.activityRegion = activityRegion;
        this.facility = facility;
        this.exercise = exercise;
        this.status =  baseStatus;
    }

    public void UpdateSocialringInfo(String newSocialringName, String newSocialringImg, Integer newTotalRecruits, LocalDate newSocialringDate,
                                     Integer newSocialringCost, String newComment, String newCommentSimple, BaseGender newGender,
                                     BaseLevel newLevel, ActivityRegion activityRegion, Facility facility,Exercise exercise) {
        this.socialringName = newSocialringName;
        this.socialringImg = newSocialringImg;
        this.totalRecruits = newTotalRecruits;
        this.socialringDate = newSocialringDate;
        this.socialringCost = newSocialringCost;
        this.commentSimple = newCommentSimple;
        this.comment = newComment;
        this.gender = newGender;
        this.level = newLevel;
        this.activityRegion = activityRegion;
        this.facility = facility;
        this.exercise = exercise;

    }

    public void setStatus(BaseStatus status) {
        this.status = status;
    }
}
