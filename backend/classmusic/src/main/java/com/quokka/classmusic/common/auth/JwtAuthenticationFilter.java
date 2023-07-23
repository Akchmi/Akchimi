package com.quokka.classmusic.common.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.api.service.UserService;
import com.quokka.classmusic.common.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;


    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(jwtTokenUtil.HEADER_STRING);
        log.debug("JWT 필터 시작 Authorization 헤더 : {}", header);

        if (header == null || !header.startsWith(jwtTokenUtil.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = getAuthentication(header.replace(jwtTokenUtil.TOKEN_PREFIX, ""));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e){
            // Exception 처리
            log.debug("JWT 토큰 인증 과정 Exception 발생");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Transactional(readOnly = true)
    public Authentication getAuthentication(String jwtToken) {
        DecodedJWT decodedJWT = jwtTokenUtil.validateJwtToken(jwtToken);
        String userId = decodedJWT.getClaim("userId").toString();

        log.debug("JWT 필터 userId : {}", userId);
        if (userId != null) {
            UserVo userVo = userService.findUserByUserId(Integer.parseInt(userId));
            log.debug("JWT 필터 UserVo id : {} name : {} role : {}", userVo.getId(), userVo.getName(), userVo.getType());
            if (userVo != null) {
                // 유저 정보가 존재하는 경우 security context에 저장
                log.debug("JWT 필터 UserDetailsVo 생성 시작...");
                UserDetailsVo userDetailsVo = new UserDetailsVo(userVo);
                UsernamePasswordAuthenticationToken jwtAuthentication =
                        new UsernamePasswordAuthenticationToken(userDetailsVo, null, userDetailsVo.getAuthorities());
                log.debug("JWT 필터 UserDetailsVo 생성 완료 : {}", userDetailsVo);
                return jwtAuthentication;
            }
        }
        return null;
    }

}
