package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.*;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TeacherRepository teacherRepository;
    private LikeRepository likeRepository;
    private TreatRepository treatRepository;
    private JavaMailSender mailSender;
    @Autowired
    public UserServiceImpl(UserRepository userRepository
            , PasswordEncoder passwordEncoder
            , TeacherRepository teacherRepository
            , LikeRepository likeRepository
            , TreatRepository treatRepository
            , JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
        this.likeRepository = likeRepository;
        this.treatRepository = treatRepository;
        this.mailSender = mailSender;
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
        User user = userRepository.findId(findIdDto);
//                .orElseThrow(() -> new NoSuchElementException("일치하는 아이디가 없습니다."));
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

    @Override
    public void sendTemporaryPassword(MailDto mailDto) {
        User userIdFindById = userRepository.findUserById(mailDto.getId());
        User userIdFindByEmail = userRepository.findByEmail(mailDto.getEmail());
        if(userIdFindById==null || userIdFindByEmail==null){
            log.debug("그런 사용자 없습니다.");
            throw new BadCredentialsException("그런 사용자 없습니다.");
        }else{
            if(userIdFindById.getUserId()!=userIdFindByEmail.getUserId()){
                log.debug("아이디 이메일이 일치하지 않습니다.");
                throw new BadCredentialsException("아이디 이메일이 일치하지 않습니다.");
            }
        }
        String tmpPassword = getRamdomPassword(10);
        userRepository.findByEmail(mailDto.getEmail()).setPassword(tmpPassword);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getEmail());
        message.setSubject("테스트 메일 제목");
        message.setText("테스트 메일 내용" + tmpPassword);
        log.debug("{} / {} / {}",message.getTo(),message.getSubject(),message.getText());
        mailSender.send(message);
    }

    public String getRamdomPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }

}
