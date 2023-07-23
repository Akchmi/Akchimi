package com.quokka.classmusic;

import static org.junit.jupiter.api.Assertions.*;

import com.quokka.classmusic.api.request.LoginDto;
import com.quokka.classmusic.api.request.SignupDto;
import com.quokka.classmusic.api.response.LoginSuccessVo;
import com.quokka.classmusic.api.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class AuthTests {

    private PasswordEncoder passwordEncoder;
    private AuthService authService;

    @Autowired
    public AuthTests(PasswordEncoder passwordEncoder, AuthService authService) {
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @Test
    public void beanTest(){
        assertNotNull(passwordEncoder);
        assertNotNull(authService);
    }

    // 로그인 Test
    @Test
    public void loginTest(){
        String id = "ssafy";
        String password = "ssafy";

        LoginSuccessVo loginSuccessVo = authService.login(new LoginDto(id, password));

        assertEquals(1, loginSuccessVo.getUserId());
    }

    // 회원가입 Test
    @Test
    @Transactional
    public void signupTest(){
        SignupDto signupDto = new SignupDto("nope", "nope", "NOPE", "nope@email.com", 1);

        int result = authService.signup(signupDto);

        assertEquals(5, result);
    }

}
