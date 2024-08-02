package com.example.likelion12.service;

import com.example.likelion12.common.exception.MemberException;
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

import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.CANNOT_FOUND_MEMBER;

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
                .orElseThrow(() -> new IllegalArgumentException("Invalid facility ID"));

        //리뷰에 받아온 값들 넣고
        Review review = new Review();
        review.setReview(facility, ranking, comment, member);

        //레퍼지토리에 저장
        Review savedReview = reviewRepository.save(review);

        return savedReview.getReviewId();
    }
}
