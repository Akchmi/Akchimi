package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.FindIdDto;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserVo findUserById(String id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new NoSuchElementException("id와 일치하는 회원이 없습니다."));
        return new UserVo(user);
    }

    @Override
    public UserVo findUserByUserId(int userId) {
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new NoSuchElementException("userId와 일치하는 회원이 없습니다."));
         return new UserVo(user);
    }

//    @Override
//    public UserVo findId(FindIdDto findIdDto) {
//        User user = userRepository.findId(findIdDto);
//        return null;
//    }
}
