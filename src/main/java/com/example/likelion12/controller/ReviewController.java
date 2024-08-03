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

    @PatchMapping("/delete")
    public BaseResponse<String> deleteReview(@RequestHeader("Authorization") String authorization,
                                             @RequestParam("review-id") Long reviewId) {
        Long memberId = jwtProvider.extractIdFromHeader(authorization);


        boolean isDeleted = reviewService.deleteReview(reviewId, memberId);
        if (isDeleted) {
            // 삭제 성공 시 응답 생성
            return new BaseResponse<>("리뷰를 성공적으로 삭제했습니다");
        }
        else {
                // 삭제 실패 시 응답 생성
            return new BaseResponse<>("리뷰가 삭제되지 않았습니다");
        }



    }

}
