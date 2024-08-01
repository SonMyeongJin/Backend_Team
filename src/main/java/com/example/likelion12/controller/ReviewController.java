package com.example.likelion12.controller;
import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.dto.PostReviewRequest;
import com.example.likelion12.dto.PostReviewResponse;
import com.example.likelion12.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public BaseResponse<PostReviewResponse> createReview(@RequestBody PostReviewRequest postReviewRequest) {
        Long reviewId = reviewService.createReview(postReviewRequest.getFacilityId(),
                postReviewRequest.getRanking(),
                postReviewRequest.getComment());
        PostReviewResponse response = new PostReviewResponse(reviewId);
        return new BaseResponse<>(response);
    }
}
