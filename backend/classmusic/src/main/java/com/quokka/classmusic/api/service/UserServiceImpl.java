package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.*;
import com.quokka.classmusic.api.response.*;
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
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

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
    public UserVo findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
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
        String content="";
        if(user == null){
            throw new RestApiException(ErrorCode.ID_NOT_FOUND);
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom("quokkain");
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject("[악취미] 아이디 찾기 안내");
            content+="<html><body><div style=\"text-align: center;\">";
            content+="<div><img src=\"https://music-class-bucket.s3.ap-northeast-2.amazonaws.com/images/akchimilogo.png\"></div>";
            content+="<div><br/>";
            content+="<h3>안녕하세요 "+user.getName()+"님!</h3>";
            content+="악취미 아이디 찾기 서비스입니다. 요청하신 가입아이디 전송드립니다.<br/><br/>";
            content+="<span style=\"padding: 10px 100px; justify-content: center; font-weight: bolder; text-align: center; background-color: rgb(231, 231, 231) ;\">아이디 : "+user.getId()+"</span><br/><br/>";
            content+="악취미를 이용해주셔서 감사합니다.<br/>더욱 편리한 서비스를 제공하기 위해 항상 최선을 다하겠습니다.<br/><br/>";
            content+="<a href=\"http://i9a210.p.ssafy.io\" style=\"font-weight: bolder;\">악취미 바로가기</a>";
            content+="<br/><br/><br/></body></html>";
            mimeMessageHelper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RestApiException(ErrorCode.MAILSERVICERROR);
        }

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
        if(passwordEncoder.matches(changePasswordDto.getOldPassword(),user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
        }else{
            throw new RestApiException(ErrorCode.PASSWORD_MISMATCH);
        }
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
    public List<TeacherLikeVo> findAllLike(String id) {
        int userId = userRepository.findUserById(id).getUserId();
        List<TeacherLikeVo> teacherVoList = likeRepository.findAll(userId);
        for (TeacherLikeVo teacherVo:teacherVoList){
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
        String content="";
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
        User user = userRepository.findByEmail(mailDto.getEmail());
        user.setPassword(passwordEncoder.encode(tmpPassword));
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom("quokkain");
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject("[악취미] 임시 비밀번호 발급");
            content+="<html><body><div style=\"text-align: center;\">";
            content+="<div><img src=\"https://music-class-bucket.s3.ap-northeast-2.amazonaws.com/images/akchimilogo.png\"></div>";
            content+="<div><br/>";
            content+="<h3>안녕하세요 "+user.getName()+"님!</h3>";
            content+="악취미 임시 비밀번호 발급 서비스입니다. 발급 요청하신 임시 비밀번호 전송드립니다.<br/><br/>";
            content+="<span style=\"padding: 10px 100px; justify-content: center; font-weight: bolder; text-align: center; background-color: rgb(231, 231, 231) ;\">임시비밀번호 : "+tmpPassword+"</span><br/><br/>";
            content+="악취미를 이용해주셔서 감사합니다.<br/>더욱 편리한 서비스를 제공하기 위해 항상 최선을 다하겠습니다.<br/><br/>";
            content+="<a href=\"http://i9a210.p.ssafy.io\" style=\"font-weight: bolder;\">악취미 바로가기</a>";
            content+="<br/><br/><br/></body></html>";
            mimeMessageHelper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RestApiException(ErrorCode.MAILSERVICERROR);
        }
        userRepository.save(user);
    }

//   유저 프로필 이미지 추가
    @Override
    public void insertProfileImage(String id, MultipartFile multipartFile) {
        User user = userRepository.findUserById(id);
        if(!user.getUserProfileImage().equals("https://music-class-bucket.s3.ap-northeast-2.amazonaws.com/images/20b17a31-a4a1-4dbb-8556-93bdf1c58329.webp")){
            amazonS3ResourceStorage.deleteFile(user.getUserProfileImage());
            user.setUserProfileImage(null);
        }
        FileVo fileVo = FileVo.multipartOf(multipartFile);
        amazonS3ResourceStorage.store(fileVo.getPath() , multipartFile);
        user.setUserProfileImage("https://music-class-bucket.s3.ap-northeast-2.amazonaws.com/" + fileVo.getPath());
    }

    @Override
    public void deleteProfileImage(String id) {
        User user = userRepository.findUserById(id);
        if(!user.getUserProfileImage().equals("https://music-class-bucket.s3.ap-northeast-2.amazonaws.com/images/20b17a31-a4a1-4dbb-8556-93bdf1c58329.webp")){
            amazonS3ResourceStorage.deleteFile(user.getUserProfileImage());
            user.setUserProfileImage("https://music-class-bucket.s3.ap-northeast-2.amazonaws.com/images/20b17a31-a4a1-4dbb-8556-93bdf1c58329.webp");
        }

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
