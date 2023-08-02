package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.LoginDto;
import com.quokka.classmusic.api.request.SignupDto;
import com.quokka.classmusic.api.response.LoginSuccessVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.common.exception.UserIdDuplicatedExeception;
import com.quokka.classmusic.common.util.JwtTokenUtil;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService{

    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthServiceImpl(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    @Transactional
    public int signup(SignupDto signupDto) {
        log.debug("Auth Service signup 호출");

        // TODO : 유저 프로필 이미지 기능 추가
        // TODO : 유저 Type 기본값 0으로 설정? -> 백엔드에서 넣어주냐 vs DB에서 Default로 설정하냐
        // TODO : 아이디 중복 체크 -> User Table Id Column에 unique 속성 추가합시다

        if(userRepository.findUserById(signupDto.getId())!=null){
            throw new UserIdDuplicatedExeception("중복아이디입니다.");
        }

        User user = User.builder()
                .id(signupDto.getId())
                .name(signupDto.getName())
                .email(signupDto.getEmail())
                .gender(signupDto.getGender())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .build();

//        user = userRepository.save(user);
        userRepository.save(user);
        log.debug("회원가입된 사용자 정보 : {}", user);

        return user.getUserId();
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
