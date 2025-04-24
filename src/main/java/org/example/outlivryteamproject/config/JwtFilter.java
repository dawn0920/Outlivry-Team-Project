package org.example.outlivryteamproject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.outlivryteamproject.domain.user.enums.UserRole;

import javax.swing.*;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String url = httpServletRequest.getRequestURI();

        // 만약 url의 경로가 /auth로 작하면 JWT 검사 없이 필터로 넘김
        if (url.startsWith("/auth")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 요청헤더에서 Authorization 헤더를 꺼냄 (JWT 토큰을 꺼냄)
        String bearerJwt = httpServletRequest.getHeader("Authorization");

        if (bearerJwt == null) {
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT 토큰이 필요합니다.");
            return;
        }

        // 가지고 온 토큰에서 "Bearer " 분리
        String jwt = jwtUtil.substringToken(bearerJwt);

        try {
            //  JWT 내부 Payload(Claims)를 추출
            Claims claims = jwtUtil.extractClaims(jwt);
            if (claims == null) {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰입니다.");
                return;
            }

            UserRole userRole = UserRole.valueOf(claims.get("userRole", String.class));

            // request에 사용자 정보를 저장
            httpServletRequest.setAttribute("userId", Long.parseLong(claims.getSubject()));
            httpServletRequest.setAttribute("userRole", claims.get("userRole"));

            if (url.startsWith("/owners")) {
                if (!UserRole.OWNER.equals(userRole)) {
                    httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "사장님 권한이 없습니다.");
                    return;
                }
                filterChain.doFilter(servletRequest, servletResponse); // 조건에 맞으면 다음 필터나 컨트롤러로 넘김
                return;
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (SecurityException | MalformedJwtException e) {
            // JWT 서명이 잘못되었을 경우 발생
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            // JWT가 만료된 경우
            log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            // 서버에서 지원하지 않는 형식의 JWT일 경우
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
        } catch (Exception e) {
            // 그 외 모든 예외
            log.error("Invalid JWT token, 유효하지 않는 JWT 토큰 입니다.", e);
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않는 JWT 토큰입니다.");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
