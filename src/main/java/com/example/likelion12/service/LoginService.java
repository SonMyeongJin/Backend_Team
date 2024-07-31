package com.example.likelion12.service;

import com.example.likelion12.domain.Member;
import com.example.likelion12.domain.base.BaseStatus;
import com.example.likelion12.dto.LoginResponse;
import com.example.likelion12.repository.MemberRepository;
import com.example.likelion12.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    @Value("${CLIENT_ID}")
    private String clientId;

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    /**
     * 카카오 소셜로그인
     */
    public LoginResponse kakaoLogin(String code){
        log.info("[LoginService.kakaoLogin]");
        String accessToken = getAccessToken(code);
        String[] userInfo = getUserInfo(accessToken);
        boolean memberStatus = false;
        Long memberId = null;
        String profileImage = userInfo[1];
        String nickname = userInfo[0];
        String email = userInfo[2];
        String jwtToken = null;

        // 만약에 데이터베이스에 userInfo[2] 에 있는 이메일을 가진 유저가 없으면 회원가임
        Optional<Member> member = memberRepository.findByEmailAndStatus(email, BaseStatus.ACTIVE);

        // 있으면 그냥 정보 반환
        if(member.isPresent()){
            memberStatus = true;
            Member myUser = member.get();
            memberId = myUser.getMemberId();
            profileImage = myUser.getMemberImg();
            nickname = myUser.getMemberName();
            jwtToken = jwtProvider.createAccessToken(myUser.getEmail(), myUser.getMemberId());
            return new LoginResponse(memberStatus, memberId, profileImage, nickname, jwtToken);
        } else {
            return new LoginResponse(memberStatus, memberId, profileImage, nickname, jwtToken);
        }
    }

    private String getAccessToken(String code){
        log.info("[Loginservice.kakaoLogin.getAccessToken]");
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://kauth.kakao.com/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", "http://localhost:8080/auth/kakao/callback")
                .queryParam("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                String.class
        );
        log.info(response.getBody());
        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getString("access_token"); // access_token 값만 반환
    }

    private String[] getUserInfo(String accessToken) {
        log.info("[LoginService.kakaoLogin.getUserInfo]");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                entity,
                String.class
        );
        log.info(response.getBody());
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONObject properties = jsonObject.getJSONObject("properties");
        JSONObject elements = jsonObject.getJSONObject("kakao_account");
        String[] userInfo = new String[3];
        userInfo[0] = properties.getString("nickname");
        userInfo[1] = properties.getString("profile_image");
        userInfo[2] = elements.getString("email");
        return userInfo;
    }

}
