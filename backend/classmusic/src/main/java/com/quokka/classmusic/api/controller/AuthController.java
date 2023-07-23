package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.LoginDto;
import com.quokka.classmusic.api.response.LoginSuccessVo;
import com.quokka.classmusic.api.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    // 200 성공 401 인증 실패 404 사용자 없음 500 서버 오류
    @PostMapping("/login")
    public ResponseEntity<LoginSuccessVo> login(@RequestBody LoginDto loginDto){
        log.info("POST /auth/login 요청 LoginDto : {}", loginDto);

        try {
            LoginSuccessVo loginSuccessVo = authService.login(loginDto);
            return new ResponseEntity<>(loginSuccessVo, HttpStatus.ACCEPTED);
        }catch(NoSuchElementException e){
            log.debug("아이디가 존재하지 않습니다.");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch(BadCredentialsException e){
            log.debug("비밀번호가 일치하지 않습니다.");
            return  new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
