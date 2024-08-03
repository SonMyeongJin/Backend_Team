package com.example.likelion12.repository;

import com.example.likelion12.domain.Facility;
import com.example.likelion12.domain.Member;
import com.example.likelion12.domain.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰 작성
    // 기본적으로 JpaRepository의 save() 메서드를 사용하여 작성할 수 있습니다.
    // 예: reviewRepository.save(review);

    // 리뷰 삭제 -> 스프링에서 자동으로 구현해줌
    void deleteById(Long reviewId);

    // 리뷰 수정 -> 기본적인 CRUD외에는 내가 직접 구현해야함 -> 쿼리로 수정하는 방식 선택
    @Modifying
    @Query("UPDATE Review r SET r.ranking = :ranking, r.comment = :comment, r.facility = :facility WHERE r.id = :reviewId")
    void updateReview(@Param("reviewId") Long reviewId,
                      @Param("ranking") int ranking,
                      @Param("comment") String comment,
                      @Param("facility") Facility facility);

    // 중복 검사
    boolean existsByMemberAndFacility(Member member, Facility facility);
}
