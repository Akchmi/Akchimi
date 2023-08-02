package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.FindIdDto;
import com.quokka.classmusic.api.request.LoginDto;
import com.quokka.classmusic.api.request.MailDto;
import com.quokka.classmusic.api.request.SignupDto;
import com.quokka.classmusic.api.response.LoginSuccessVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.api.service.AuthService;
import com.quokka.classmusic.api.service.UserService;
import com.quokka.classmusic.common.exception.UserIdDuplicatedExeception;
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

        try {
            int userId = authService.signup(signupDto);
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        }catch(UserIdDuplicatedExeception e){
            return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
        }
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
    // 이름과 이메일로 아이디 찾기
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody FindIdDto findIdDto){
        try{
            UserVo userVo = userService.findId(findIdDto);
            log.debug("find-id : {}",userVo.getId());
            return new ResponseEntity<>(userVo.getId(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            log.debug("해당하는 아이디를 찾지 못했습니다.");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 임시 비밀번호 발급
    @PutMapping("/temporary-password")
    public ResponseEntity sendEmail(@RequestBody MailDto mailDto){
        userService.sendTemporaryPassword(mailDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
