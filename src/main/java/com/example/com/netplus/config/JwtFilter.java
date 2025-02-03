package com.example.com.netplus.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //1. 토큰 추출
        String token = extractBearerToken(request);

        //2.jwt null & 만료기간
        if (token != null && jwtUtil.validateToken(token)) {
            //사용자 정보 추출
            Long userId = jwtUtil.extractUserId(token);

            // Authentication 객체 생성 (Spring Security에서 사용되는 인증 객체)
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userId, null,
                    new ArrayList<>());
            //SecurityContext에 인증 객체 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    // private methods
    private String extractBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7).trim();
        }
        return null;
    }


}

