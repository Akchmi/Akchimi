package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ChangePasswordDto;
import com.quokka.classmusic.api.request.FindIdDto;
import com.quokka.classmusic.api.request.LikeInsertDto;
import com.quokka.classmusic.api.request.ModifyUserDto;
import com.quokka.classmusic.api.response.LikeVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.db.entity.Like;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.LikeRepository;
import com.quokka.classmusic.db.repository.TeacherRepository;
import com.quokka.classmusic.db.repository.TreatRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TeacherRepository teacherRepository;
    private LikeRepository likeRepository;
    private TreatRepository treatRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TeacherRepository teacherRepository, LikeRepository likeRepository, TreatRepository treatRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
        this.likeRepository = likeRepository;
        this.treatRepository = treatRepository;
    }

    @Override
    public UserVo findUserById(String id) {
        User user = userRepository.findUserById(id);
//                .orElseThrow(() -> new NoSuchElementException("id와 일치하는 회원이 없습니다."));
        return new UserVo(user);
    }

    @Override
    public UserVo findUserByUserId(int userId) {
         User user = userRepository.findById(userId);
//                 .orElseThrow(() -> new NoSuchElementException("userId와 일치하는 회원이 없습니다."));
         return new UserVo(user);
    }

    @Override
    public UserVo findId(FindIdDto findIdDto) {
        User user = userRepository.findId(findIdDto)
                .orElseThrow(() -> new NoSuchElementException("일치하는 아이디가 없습니다."));
        return new UserVo(user);
    }

    @Override
    public UserVo modifyUser(String id, ModifyUserDto modifyUserDto) {
        User user = userRepository.findUserById(id);
        user.setName(modifyUserDto.getName());
        user.setUserProfileImage(modifyUserDto.getUserProfileImage());
        userRepository.save(user);
        return new UserVo(user);
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId);
        user.setType(3);
        userRepository.save(user);
    }

    @Override
    public void changePassword(String id, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findUserById(id);
        passwordEncoder.matches(user.getPassword(),changePasswordDto.getOldPassword());
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public LikeVo addLike(LikeInsertDto likeInsertDto) {
        Like like = Like.builder()
                .teacher(teacherRepository.findById(likeInsertDto.getTeacherId()))
                .student(userRepository.findById(likeInsertDto.getStudentId()))
                .build();
        boolean isDuplicated = likeRepository.duplicationCheck(like);
        if(!isDuplicated) {
            likeRepository.save(like);
            return new LikeVo(like);
        }else{
            return null;
        }
    }

    @Override
    public List<TeacherVo> findAllLike(String id) {
        int userId = userRepository.findUserById(id).getUserId();
        List<TeacherVo> teacherVoList = likeRepository.findAll(userId);
        for (TeacherVo teacherVo:teacherVoList){
            teacherVo.setInstruments(treatRepository.findInstrumentNameByTeacherId(teacherVo.getTeacherId()));
        }
        return teacherVoList;
    }

    @Override
    public void deleteLike(int likeId) {
        likeRepository.delete(likeRepository.find(likeId));
    }
}
