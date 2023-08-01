package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.LoginDto;
import com.quokka.classmusic.api.request.SignupDto;
import com.quokka.classmusic.api.response.LoginSuccessVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.api.service.AuthService;
import com.quokka.classmusic.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {

    private AuthService authService;
    private UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Integer> signup(@RequestBody SignupDto signupDto){
        log.info("POST /auth/sign-up 요청 SignUpDto : {}", signupDto);

        int userId = authService.signup(signupDto);

        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    // 200 성공 401 인증 실패 404 사용자 없음 500 서버 오류
    @PostMapping("/login")
    public ResponseEntity<LoginSuccessVo> login(@RequestBody LoginDto loginDto){
        log.info("POST /auth/login 요청 LoginDto : {}", loginDto);

        try {
            LoginSuccessVo loginSuccessVo = authService.login(loginDto);
            return new ResponseEntity<>(loginSuccessVo, HttpStatus.OK);
        }catch(NoSuchElementException e){
            log.debug("아이디가 존재하지 않습니다.");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch(BadCredentialsException e){
            log.debug("비밀번호가 일치하지 않습니다.");
            return  new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/check-id")
    public ResponseEntity<Boolean> checkIsDuplicateId(@RequestParam String id){
        log.info("GET /auth/check-id id : {}", id);

        boolean isDuplicated = false;

        UserVo userVo = null;
        try {
            userVo = userService.findUserById(id);
        }catch(NoSuchElementException e){
            log.debug("아이디 중복체크 {}와 일치하는 아이디가 없습니다. 결과 : false", id);
        }

        if(userVo != null){
            isDuplicated = true;
        }

        return new ResponseEntity<>(isDuplicated, HttpStatus.OK);
    }
}
