package com.quokka.classmusic;

import static org.junit.jupiter.api.Assertions.*;

import com.quokka.classmusic.common.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class JwtUtilTests {

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtUtilTests(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Test
    public void beanTest(){
        assertNotNull(jwtTokenUtil);
    }

    @Test
    public void accessTokenGenerateTest(){
        String accessToken = jwtTokenUtil.generateAccessJwt(1);
        log.debug("Generated Access Token : {}", accessToken);
    }

    @Test
    public void refreshTokenGenerateTest(){
        String refreshToken = jwtTokenUtil.generateRefreshJwt(1);
        log.debug("Generated Refresh Token : {}", refreshToken);
    }

    @Test
    public void validateJwtTokenTest(){
        String accessToken = jwtTokenUtil.generateAccessJwt(1);
        jwtTokenUtil.validateJwtToken(accessToken);
    }

}
