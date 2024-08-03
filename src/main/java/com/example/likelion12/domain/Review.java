package com.example.likelion12.domain;

import com.example.likelion12.common.exception.ReviewException;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.domain.base.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.CANNOT_SET_SCORE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private int ranking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseStatus status = BaseStatus.ACTIVE;

    /** 멤버 와의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /** 체육시설 과의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    public void setReview(Facility facility , int ranking, String comment , Member member)
    {
        // 리뷰 점수 1~5 로 한정하기위해 검증하는 함수
        if (ranking < 1 || ranking > 5) {
            throw new ReviewException(CANNOT_SET_SCORE);
        }

        this.facility = facility;
        this.ranking = ranking;
        this.comment = comment;
        this.member = member;
    }

    public void updateReview(Facility facility , int ranking, String comment )
    {
        this.facility = facility;
        this.ranking = ranking;
        this.comment = comment;
    }

    public void setStatus(BaseStatus status) {
        this.status = status;
    }

    public void setRanking(Integer ranking) {
        if (ranking != null) {
            // 랭킹이 1~5 범위에 있어야 함
            if (ranking < 1 || ranking > 5) {
                throw new ReviewException(CANNOT_SET_SCORE);
            }
            this.ranking = ranking;
        }
        // ranking이 null인 경우, 기존 값을 그대로 유지합니다.
    }

    // 코멘트 설정 메서드
    public void setComment(String comment) {
        this.comment = comment != null ? comment : this.comment; // 기존 값 유지
    }

    // 시설 설정 메서드
    public void setFacility(Facility facility) {
        this.facility = facility != null ? facility : this.facility; // 기존 값 유지
    }
}
