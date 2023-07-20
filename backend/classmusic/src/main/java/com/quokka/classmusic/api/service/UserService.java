package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.UserRegistDto;

public interface UserService {
    public int regist(UserRegistDto userRegistDto) throws Exception;
}
