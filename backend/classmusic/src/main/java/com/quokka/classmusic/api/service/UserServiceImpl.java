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
        User user = new User();
        user.setId(userRegistDto.getId());
        user.setPassword(userRegistDto.getPassword());
        user.setName(userRegistDto.getName());
        user.setEmail(userRegistDto.getEmail());
        user.setGender(userRegistDto.getGender());
        user.setUserProfileImage(userRegistDto.getUserProfileImage());
        user.setType(userRegistDto.getType());
        userRepository.save(user);
        return 1;
    }
}
