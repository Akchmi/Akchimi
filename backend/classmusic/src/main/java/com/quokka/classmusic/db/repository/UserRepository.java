package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.request.FindIdDto;
import com.quokka.classmusic.db.entity.Like;
import com.quokka.classmusic.db.entity.User;

import java.util.Optional;

public interface UserRepository{
    User findUserById(String id);
    User findById(int userId);
    Optional<User> findId(FindIdDto findIdDto);
    void save(User user);
    void delete(User user);
}
