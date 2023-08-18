package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.LoginDto;
import com.quokka.classmusic.api.request.SignupDto;
import com.quokka.classmusic.api.response.LoginSuccessVo;

public interface AuthService {
    LoginSuccessVo login(LoginDto loginDto);
    int signup(SignupDto signupDto);
}
