package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.*;
import com.quokka.classmusic.api.response.FileVo;
import com.quokka.classmusic.api.response.LikeVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.common.exception.ErrorCode;
import com.quokka.classmusic.common.exception.RestApiException;
import com.quokka.classmusic.common.util.AmazonS3ResourceStorage;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;
    private final LikeRepository likeRepository;
    private final TreatRepository treatRepository;
    private final JavaMailSender mailSender;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TeacherRepository teacherRepository, LikeRepository likeRepository, TreatRepository treatRepository, JavaMailSender mailSender, AmazonS3ResourceStorage amazonS3ResourceStorage) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
        this.likeRepository = likeRepository;
        this.treatRepository = treatRepository;
        this.mailSender = mailSender;
        this.amazonS3ResourceStorage = amazonS3ResourceStorage;
    }

    @Override
    public UserVo findUserById(String id) {
        User user = userRepository.findUserById(id);
        if(user == null){
            throw new RestApiException(ErrorCode.ID_NOT_FOUND);
        }
        return new UserVo(user);
    }

    @Override
    public UserVo findUserByUserId(int userId) {
         User user = userRepository.findById(userId);
        if(user == null){
            throw new RestApiException(ErrorCode.ID_NOT_FOUND);
        }
         return new UserVo(user);
    }

    @Override
    public UserVo findId(FindIdDto findIdDto) {
        User user = userRepository.findId(findIdDto);
        if(user == null){
            throw new RestApiException(ErrorCode.ID_NOT_FOUND);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("악취미 : 아이디 찾기");
        message.setText("악취미 아이디 찾기 서비스입니다.\n아이디 : "+user.getId());
        mailSender.send(message);

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

        if(isDuplicated) {
            throw new RestApiException(ErrorCode.LIKE_DUPLICATED);
        }

        likeRepository.save(like);
        return new LikeVo(like);
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
            throw new RestApiException(ErrorCode.ID_NOT_FOUND);
        }else{
            if(userIdFindById.getUserId()!=userIdFindByEmail.getUserId()){
                log.debug("아이디 이메일이 일치하지 않습니다.");
                throw new RestApiException(ErrorCode.ID_EMAIL_MISMATCH);
            }
        }
        String tmpPassword = getRandomPassword(10);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getEmail());
        message.setSubject("악취미 : 임시 비밀번호 발급");
        message.setText("임시 비밀번호 발급드립니다 : \n" + tmpPassword);
        log.debug("{} / {} / {}",message.getTo(),message.getSubject(),message.getText());
        mailSender.send(message);

        User user = userRepository.findByEmail(mailDto.getEmail());

        user.setPassword(passwordEncoder.encode(tmpPassword));
        userRepository.save(user);
    }

//   유저 프로필 이미지 추가
    @Override
    public void insertProfileImage(String id, MultipartFile multipartFile) {
        User user = userRepository.findUserById(id);
        if(user.getUserProfileImage() != null && user.getUserProfileImage().length() > 10){
            amazonS3ResourceStorage.deleteFile(user.getUserProfileImage());
            user.setUserProfileImage(null);
        }
        FileVo fileVo = FileVo.multipartOf(multipartFile);
        amazonS3ResourceStorage.store(fileVo.getPath() , multipartFile);
        user.setUserProfileImage("https://music-class-bucket.s3.ap-northeast-2.amazonaws.com/" + fileVo.getPath());
    }

    public String getRandomPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuilder sb = new StringBuilder();
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
