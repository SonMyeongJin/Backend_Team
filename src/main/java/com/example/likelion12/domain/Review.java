package com.example.likelion12.domain;

import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.domain.base.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private long reviewId;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private int ranking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseStatus status;

    /** 멤버 와의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /** 체육시설 과의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
