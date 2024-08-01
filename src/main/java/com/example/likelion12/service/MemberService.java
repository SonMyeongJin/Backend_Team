package com.example.likelion12.service;

import com.example.likelion12.common.exception.ExerciseException;
import com.example.likelion12.common.exception.MemberException;
import com.example.likelion12.domain.Exercise;
import com.example.likelion12.domain.Member;
import com.example.likelion12.domain.base.BaseGender;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.dto.member.PostSignupRequest;
import com.example.likelion12.dto.member.PostSignupResponse;
import com.example.likelion12.repository.ExerciseRepository;
import com.example.likelion12.repository.MemberRepository;
import com.example.likelion12.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.ALREADY_EXIST_EMAIL;
import static com.example.likelion12.common.response.status.BaseExceptionResponseStatus.CANNOT_FOUND_EXERCISE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ExerciseRepository exerciseRepository;
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;

    /**
     * 회원가입
     */
    @Transactional
    public PostSignupResponse signUp(PostSignupRequest postSignupRequest){
        log.info("[MemberService.signUp]");
        String nickname = postSignupRequest.getNickname();
        String profileImage = postSignupRequest.getProfileImage();
        String email = postSignupRequest.getEmail();
        BaseGender gender = postSignupRequest.getGender();
        String exerciseName = postSignupRequest.getExercise();

        //동일한 이메일을 가지고 있는 회원이 있는지 확인
        if(memberRepository.existsByEmailAndStatus(email, BaseStatus.ACTIVE)){
            throw new MemberException(ALREADY_EXIST_EMAIL);
        }else{
            log.info("[MemberService.signUp] email 검증 완료");
            // 운동명을 가지고 exercise 객체 찾기
            Exercise exercise = exerciseRepository.findByExerciseNameAndStatus(exerciseName, BaseStatus.ACTIVE)
                    .orElseThrow(()-> new ExerciseException(CANNOT_FOUND_EXERCISE));

            // 데이터베이스에 저장하기
            Member member = new Member(nickname, email, profileImage, gender, BaseStatus.ACTIVE, exercise);
            memberRepository.save(member);

            // 토큰 발급, 레디스에 저장하기
            String accessToken = jwtProvider.createAccessToken(email,member.getMemberId());
            // 반환하기
            tokenService.storeToken(accessToken,member.getMemberId());
            return new PostSignupResponse(member.getMemberId(), accessToken);
        }
    }
}
