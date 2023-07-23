package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.LoginDto;
import com.quokka.classmusic.api.response.LoginSuccessVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.common.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService{

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    public LoginSuccessVo login(LoginDto loginDto) {
        log.debug("Auth Service login 호출 id : {} password : {}", loginDto.getId(), loginDto.getPassword());
        // 1. 사용자가 입력한 아이디로 DB에서 회원 정보 가져온다.
        // 2. 존재하지 않는 회원인 경우 로그인 실패 -> 존재하지 않는 아이디입니다.
        UserVo userVo = userService.findUserById(loginDto.getId());

        // 3. 가져왔는데 비밀번호가 틀린 경우 로그인 실패 -> 비밀번호가 틀렸습니다.
        if(!passwordEncoder.matches(loginDto.getPassword(), userVo.getPassword())){
            throw new BadCredentialsException("로그인 실패");
        }
        // 4. 일치하는 경우 JWT Access, Refresh 토큰 생성하여 반환
        String accessToken = jwtTokenUtil.generateAccessJwt(userVo.getUserId());
        String refreshToken = jwtTokenUtil.generateRefreshJwt(userVo.getUserId());

        return new LoginSuccessVo(userVo.getUserId(), accessToken, refreshToken);
    }
}
