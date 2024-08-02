package com.example.likelion12.repository;

import com.example.likelion12.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰 작성
    // 기본적으로 JpaRepository의 save() 메서드를 사용하여 작성할 수 있습니다.
    // 예: reviewRepository.save(review);

    // 리뷰 수정
    // 기본적으로 JpaRepository의 save() 메서드를 사용하여 수정할 수 있습니다.
    // 예: reviewRepository.save(updatedReview);

    // 리뷰 삭제
    void deleteById(Long reviewId);
}
