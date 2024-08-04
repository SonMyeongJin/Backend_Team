package com.example.likelion12.domain;

import com.example.likelion12.domain.base.BaseRole;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.domain.base.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSocialring extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_socialring_id", nullable = false)
    private long memberSocialringId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseStatus status;

    /** 크루 과의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "socialring_id")
    private Socialring socialring;

    /** 멤버 와의 연관관계의 주인 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberSocialring(BaseRole baseRole, Socialring socialring, Member member, BaseStatus baseStatus) {
        this.role = baseRole;
        this.socialring = socialring;
        this.member = member;
        this.status = baseStatus;
    }

    public void setStatus(BaseStatus status) {
        this.status = status;
    }
}
