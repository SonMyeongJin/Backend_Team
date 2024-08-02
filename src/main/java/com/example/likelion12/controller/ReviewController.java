package com.example.likelion12.controller;
import com.example.likelion12.common.response.BaseResponse;
import com.example.likelion12.dto.PostReviewRequest;
import com.example.likelion12.dto.PostReviewResponse;
import com.example.likelion12.service.ReviewService;
import com.example.likelion12.util.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public BaseResponse<PostReviewResponse> createReview(@RequestHeader("Authorization") String authorization , @RequestBody PostReviewRequest postReviewRequest) {

        Long memberId = jwtProvider.extractIdFromHeader(authorization);

        Long reviewId = reviewService.createReview(postReviewRequest.getFacilityId(),
                postReviewRequest.getRanking(),
                postReviewRequest.getComment(),
                memberId);
        PostReviewResponse response = new PostReviewResponse(reviewId);
        return new BaseResponse<>(response);
    }
}
