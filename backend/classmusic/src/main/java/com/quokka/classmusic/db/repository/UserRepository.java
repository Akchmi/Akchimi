package com.quokka.classmusic.db.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.quokka.classmusic.api.request.FindIdDto;
import com.quokka.classmusic.db.entity.User;

import java.util.Optional;

public interface UserRepository{
    Optional<User> findUserById(String id);
    Optional<User> findById(int userId);
    User findId(FindIdDto findIdDto);
    void save(User user);
    void delete(User user);
}
