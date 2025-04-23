package org.example.outlivryteamproject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.outlivryteamproject.common.exception.ServerException;
import org.example.outlivryteamproject.domain.user.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JWTUtil")
@Component
public class JwtUtil {

    // Authorization 헤더에 붙는 JWT 토큰의 접두사 ->
    // 토큰을 추추할때 "Bearer " 부분을 잘라오고 순수 토큰값만 가지고옴
    private static final String BEARER_PREFIX = "Bearer ";

    // 토큰 유효시간(만료시간) 60분 (60초 * 60분 * 1000 밀리초)
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    // 위의 secretKey를 가지고 만든 암호화 키 객체
    private Key key;
    // JWT를 만들때 사용할 서명 알고리즘
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // Bean이 생성되고 의존성 주입이 끝난 후 자동으로 호출되는 메서드에 붙임
    @PostConstruct
    public void init() {
        // Base64.getDecoder().decode(secretKey); -> application에 저장 저장된 key
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        // Keys.hmacShaKeyFor(bytes); -> HMAC SHA 알고리즘에 사용할 수 있는 Key 객체
        key = Keys.hmacShaKeyFor(bytes);
    }

    // user의 아이디, 이메일, 역할을 받아서 JWT 문자열 반환 -> 반환값 : Bearer 접두사가 붙은 토큰 문자열
    // JWT 구성요소 : 1.Header : 서명 알고리즘 정보, 2. Payload : 토큰에 담고싶은 정보(claim) 3. Signature : 위변조 검증을 위한 서명
    public String createToken(Long userId, UserRole userRole) {
        // 현재 시간을 기준으로 Date 객체를 만듦
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        // .setSubject(String.valueOf(userId)) -> sub 클라임에 해당
                        .setSubject(String.valueOf(userId))
                        .claim("userRole", userRole)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    // HTTP 요청 헤더에서 전달받은 JWT 토근 문자열에 "Bearer " 접두사 제거 역할
    public String substringToken(String tokenValue) {
        // StringUtils.hasText(tokenValue) 값이 null이 아니고 빈 문자열이 아닉 경우 true 반환
        // tokenValue.startsWith(BEARER_PREFIX) -> 값이 "Bearer "로 시작하는지 확인
        if(StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)){
            return tokenValue.substring(7); // "Bearer "는 정확히 7글자. -> 7자 이후부터 잘라서 반환
        }
       throw new ServerException("Token을 찾을 수 없습니다.");
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                // 서명 검증 과정
                .setSigningKey(key)
                // JWT 파서 빌더
                .build()
                // 토큰을 파싱하고 유효 확인
                .parseClaimsJws(token)
                // 토큰에서 Payload 추출
                .getBody();
    }

}
