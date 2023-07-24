package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.response.UserVo;

public interface UserService {
    UserVo findUserById(String id);
    UserVo findUserByUserId(int userId);
}
