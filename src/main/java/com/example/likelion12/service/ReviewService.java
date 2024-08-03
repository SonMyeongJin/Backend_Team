package com.example.likelion12.service;

import com.example.likelion12.common.exception.FacilityException;
import com.example.likelion12.common.exception.MemberException;
import com.example.likelion12.common.exception.ReviewException;
import com.example.likelion12.domain.Facility;
import com.example.likelion12.domain.Member;
import com.example.likelion12.domain.Review;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.repository.FacilityRepository;
import com.example.likelion12.repository.MemberRepository;
import com.example.likelion12.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.*;
import static com.example.likelion12.domain.base.BaseStatus.DELETE;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public Long createReview(Long facilityId, int ranking, String comment , Long memberId) {

        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new MemberException(CANNOT_FOUND_MEMBER));

        // facilityId로 facility 찾고
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new FacilityException(CANOOT_FOUND_FACILITY));

        // 이미 같은 멤버가 같은 체육관에 리뷰를 작성하면 예외를 발생시키고
        if (reviewRepository.existsByMemberAndFacility(member, facility)) {
            throw new ReviewException(ALREADY_EXIST_REVIEW);
        }

        //리뷰에 받아온 값들 넣고
        Review review = new Review();
        review.setReview(facility, ranking, comment, member);

        //레퍼지토리에 저장
        Review savedReview = reviewRepository.save(review);

        return savedReview.getReviewId();
    }

    @Transactional
    public boolean deleteReview(Long reviewId, Long memberId) {

        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(()-> new MemberException(CANNOT_FOUND_MEMBER));

        // 리뷰가 존재하는지 확인
        boolean reviewExists = reviewRepository.existsById(reviewId);
        if (!reviewExists) {
            throw new ReviewException(CANNOT_FOUND_REVIEW);
        }

        //리뷰id로 리뷰 찾고
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(CANNOT_FOUND_REVIEW));

        // 찾은 리뷰의 상태를 Delete로 변경하고
        review.setStatus(DELETE);

        return true;
    }

    @Transactional
    public void updateReview(Long reviewId , Long facilityId, int ranking, String comment, Long memberId) {

        // review_id로 review찾고
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException("리뷰를 찾을 수 없습니다."));
        // memberId로 member 확인
        Member member = memberRepository.findByMemberIdAndStatus(memberId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new MemberException(CANNOT_FOUND_MEMBER));
        // facilityId로 facility 찾고
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid facility ID"));

        // 리뷰가 작성한 member와 동일한지 확인
        //if (!review.getMember().equals(member)) {
        //    throw new ReviewException("리뷰를 수정할 권한이 없습니다.");
        //}

        // 받은값들로 review 수정해서
        review.updateReview(facility, ranking, comment);

        // 레퍼지토리에 저장하고
        reviewRepository.updateReview(reviewId, ranking, comment , facility);

        // 성공Response 반환

    }
}
