package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.UserRegistDto;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int regist(UserRegistDto userRegistDto) throws Exception {
        User user = User.builder()
                .id(userRegistDto.getId())
                .password(userRegistDto.getPassword())
                .name(userRegistDto.getName())
                .email(userRegistDto.getEmail())
                .gender(userRegistDto.getGender())
                .userProfileImage(userRegistDto.getUserProfileImage())
                .type(userRegistDto.getType())
                .build();
        userRepository.save(user);
        return 1;
    }
}
