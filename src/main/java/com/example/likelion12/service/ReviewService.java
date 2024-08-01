package com.example.likelion12.service;

import com.example.likelion12.domain.Facility;
import com.example.likelion12.domain.Review;
import com.example.likelion12.repository.FacilityRepository;
import com.example.likelion12.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Transactional
    public Long createReview(Long facilityId, int ranking, String comment) {

        // facilityId로 facility 찾고
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid facility ID"));

        //리뷰에 받아온 값들 넣고
        Review review = new Review();
        review.setFacility(facility);
        review.setRanking(ranking);
        review.setComment(comment);

        //레퍼지토리에 저장
        Review savedReview = reviewRepository.save(review);

        return savedReview.getReviewId();
    }
}
