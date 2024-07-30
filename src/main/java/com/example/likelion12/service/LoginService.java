package com.example.likelion12.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    @Value("${CLIENT_ID}")
    private String clientId;

    /**
     * 카카오 소셜로그인
     */
    public Void kakaoLogin(String code){
        log.info("[LoginService.kakaoLogin]");
        String accessToken = getAccessToken(code);
        String[] userInfo = getUserInfo(accessToken);
        // 아래 로그는 지워질 부분
        log.info(userInfo[0]);
        log.info(userInfo[1]);
        log.info(userInfo[2]);

        // 만약에 데이터베이스에 userInfo[2] 에 있는 이메일을 가진 유저가 없으면 회원가임

        // 있으면 그냥 정보 반환
        // 1. 소셜로그인에 따른 member 도메인 변경 필요
        // 2. 해당 이메일로 멤버 찾고 null 이면 회원가입, 있다면 정보 반환하도록 코드 구현
        // 3. jwt 토큰 반환하도록 구현
        return null;
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
