package org.example.outlivryteamproject.domain.auth.weblogin.naver;

import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.auth.dto.response.NaverUserInfoReponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class NaverOAuthClient {

    @Value("${oauth.naver.client-id}")
    private String clientId;

    @Value("${oauth.naver.client-secret}")
    private String clientSecret;

    @Value("${oauth.naver.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    // 1. 네이버에서 Access Token 요청
    public String getAccessToken(String code) {
        String url = "https://nid.naver.com/oauth2.0/token?" +
                "grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&code=" + code +
                "&redirect_uri=" + redirectUri;

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Map.class
        );
        // 응답에서 access_token 추출
        return (String) (Objects.requireNonNull(response.getBody())).get("access_token");
    }

    // 2. Access Token으로 유저 정보 요청
    public NaverUserInfoReponse getUserInfo(String accessToken) {
        String url = "https://openapi.naver.com/v1/nid/me";

        // Bearer Token을 사용해 헤더를 설정합니다.
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);  // 'Bearer' 토큰 방식으로 헤더 설정
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody().get("response");

        // 네이버 사용자 정보 처리
        String birthYear = (String) responseBody.get("birthyear");
        String birthday = (String) responseBody.get("birthday");
        String birth = "0000-00-00"; // birth가 null일 경우 기본값 설정
        if (birthYear != null && birthday != null) {
            birth = birthYear + "-" + birthday;
        }

        return NaverUserInfoReponse.builder()
                .email((String) responseBody.get("email"))
                .name((String) responseBody.get("name"))
                .nickname((String) responseBody.get("nickname"))
                .birth(birth)
                .phone((String) responseBody.get("mobile"))
                .build();
    }
}
