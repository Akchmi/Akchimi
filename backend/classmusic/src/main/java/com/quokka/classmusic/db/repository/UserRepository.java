package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.request.FindIdDto;
import com.quokka.classmusic.db.entity.User;

public interface UserRepository{
    User findUserById(String id);
    User findById(int userId);
    User findId(FindIdDto findIdDto);
    void save(User user);
    void delete(User user);
    User findByEmail(String email);
}
