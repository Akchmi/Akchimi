package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.FindIdDto;
import com.quokka.classmusic.api.request.ModifyUserDto;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.db.entity.User;

public interface UserService {
    UserVo findUserById(String id);
    UserVo findUserByUserId(int userId);
    UserVo findId(FindIdDto findIdDto);
    UserVo modifyUser(String id, ModifyUserDto modifyUserDto);
}
