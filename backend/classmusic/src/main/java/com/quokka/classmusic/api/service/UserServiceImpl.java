package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ChangePasswordDto;
import com.quokka.classmusic.api.request.FindIdDto;
import com.quokka.classmusic.api.request.ModifyUserDto;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public UserVo findId(FindIdDto findIdDto) {
        User user = userRepository.findId(findIdDto);
        return new UserVo(user);
    }

    @Override
    public UserVo modifyUser(String id, ModifyUserDto modifyUserDto) {
        User user = userRepository.findUserById(id).get();
        user.setName(modifyUserDto.getName());
        user.setUserProfileImage(modifyUserDto.getUserProfileImage());
        userRepository.save(user);
        return new UserVo(user);
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId).get();
        user.setType(3);
        userRepository.save(user);
    }

    @Override
    public void changePassword(String id, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findUserById(id).get();
        passwordEncoder.matches(user.getPassword(),changePasswordDto.getOldPassword());
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }
}
