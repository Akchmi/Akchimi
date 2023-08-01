package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.*;
import com.quokka.classmusic.api.response.LikeVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.api.response.UserVo;

import java.util.List;

public interface UserService {
    UserVo findUserById(String id);
    UserVo findUserByUserId(int userId);
    UserVo findId(FindIdDto findIdDto);
    UserVo modifyUser(String id, ModifyUserDto modifyUserDto);
    void deleteUser(int userId);
    void changePassword(String id, ChangePasswordDto changePasswordDto);
    LikeVo addLike(LikeInsertDto likeInsertDto);
    List<TeacherVo> findAllLike(String id);
    void deleteLike(int likeId);
    void sendTemporaryPassword(MailDto mailDto);
}
