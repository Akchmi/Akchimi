package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ImageDto;
import com.quokka.classmusic.api.request.TeacherDto;
import com.quokka.classmusic.api.response.FileVo;
import com.quokka.classmusic.api.response.TeacherDetailVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.common.exception.ErrorCode;
import com.quokka.classmusic.common.exception.RestApiException;
import com.quokka.classmusic.common.util.AmazonS3ResourceStorage;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.TeacherFile;
import com.quokka.classmusic.db.entity.Treat;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.TeacherRepository;
import com.quokka.classmusic.db.repository.TreatRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final TreatRepository treatRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, UserRepository userRepository, TreatRepository treatRepository, AmazonS3ResourceStorage amazonS3ResourceStorage) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.treatRepository = treatRepository;
        this.amazonS3ResourceStorage = amazonS3ResourceStorage;
    }

    @Override
    public List<TeacherVo> selectAllTeacher(Map<String, String> params) {
        List<Teacher> teacherList = teacherRepository.findAll(params);
        List<TeacherVo> teacherVoList = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            teacherVoList.add(new TeacherVo(teacher.getUser().getName() ,
                    teacher.getUser().getUserProfileImage(),
                    teacher.getTeacherId(),
                    teacher.getCareer(),
                    teacher.getIntroduce(),
                    teacher.getAvgRating(),
                    teacher.getContactCnt(),
                    treatRepository.findInstrumentNameByTeacherId(teacher.getTeacherId())
                    ));
        }

        return teacherVoList;
    }

    @Override
    public TeacherDetailVo selectDetailTeacher(int teacherId , UserDetailsVo userDetailsVo) {
        Teacher teacher = teacherRepository.findById(teacherId);
        User user = teacher.getUser();
        Integer likeId = null;
        if(userDetailsVo != null){
            System.out.println(likeId);
            likeId = teacherRepository.findLikeById(userDetailsVo.getUserVo().getUserId() , teacherId);
        }
        TeacherDetailVo teacherDetailVo = new TeacherDetailVo(
                user.getUserId(),
                user.getName(),
                user.getGender(),
                user.getUserProfileImage(),
                teacher.getCareer(),
                teacher.getCost(),
                teacher.getIntroduce(),
                teacher.getStartTime(),
                teacher.getEndTime(),
                IntToday(teacher.getClassDay()),
                teacher.getAvgRating(),
                teacher.getContactCnt(),
                treatRepository.findInstrumentNameByTeacherId(teacherId),
                teacherRepository.findImageByTeacherId(teacherId),
                likeId,
                teacher.getVisible()
        );
        return teacherDetailVo;
    }

    @Override
    public int insertTeacher(TeacherDto teacherDto) {
        //있는지화인해 유저가 선생테이블에 있는지 확인하고
        if(userRepository.findById(teacherDto.getUserId()).getType() == 1){
            throw new RestApiException(ErrorCode.BAD_REQUEST);
        }
//        유저 타입 1로 바꿔줌
        User user = userRepository.findById(teacherDto.getUserId());
        user.setType(1);
        userRepository.save(user);

//        날짜 int로 변환해서 DB넣기
        int day = dayToInt(teacherDto.getClassDay());

//        프로필 저장
        Teacher teacher = Teacher.builder()
                .user(user)
                .career(teacherDto.getCareer())
                .cost(teacherDto.getCost())
                .introduce(teacherDto.getIntroduce())
                .startTime(teacherDto.getStartTime())
                .endTime(teacherDto.getEndTime())
                .classDay(day)
                .visible(1)
                .build();
        teacherRepository.save(teacher);

//        악기들 저장
        saveInstruments(teacher , teacherDto.getInstruments());
        return teacher.getTeacherId();
    }

    @Override
    public void updateTeacher(int teacherId , TeacherDto teacherDto) {
        int day = dayToInt(teacherDto.getClassDay());
        Teacher teacher = teacherRepository.findById(teacherId);
        teacher.setCareer(teacherDto.getCareer());
        teacher.setCost(teacherDto.getCost());
        teacher.setIntroduce(teacherDto.getIntroduce());
        teacher.setStartTime(teacherDto.getStartTime());
        teacher.setEndTime(teacherDto.getEndTime());
        teacher.setClassDay(day);
        teacherRepository.save(teacher);

//        내가 가르치는 악기들 삭제하고 다시 insert
        deleteMyTreat(treatRepository.findByTeacherId(teacherId));
        saveInstruments(teacher , teacherDto.getInstruments());
    }
    @Override
    public void setVisibleTeacher(int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId);
        teacher.setVisible(teacher.getVisible() ^ 1);
//        teacherRepository.delete(teacher);
    }

    @Override
    public void insertImage(int teacherId , List<MultipartFile> multipartFiles) {
        Teacher teacher = teacherRepository.findById(teacherId);

        for (MultipartFile multipartFile : multipartFiles) {
            FileVo fileVo = FileVo.multipartOf(multipartFile);
            amazonS3ResourceStorage.store(fileVo.getPath() , multipartFile);
            teacherRepository
                    .saveImage(TeacherFile.builder()
                    .teacher(teacher)
                    .fileUrl("https://music-class-bucket.s3.ap-northeast-2.amazonaws.com/" + fileVo.getPath())
                    .build());
        }
    }

    @Override
    public void deleteImage(int teacherId, ImageDto imageDto) {
        for (String fileUrl : imageDto.getImages()) {
            System.out.println(fileUrl);
            amazonS3ResourceStorage.deleteFile(fileUrl);
            teacherRepository.deleteImage(teacherId , fileUrl);
        }
    }

    public int dayToInt(String classDay){
        return Integer.parseInt(classDay,2);
    }

    public String IntToday(int days){
        return Integer.toBinaryString(days);
    }

    public void saveInstruments(Teacher teacher , List<String> list){
        for (String instrumentName: list) {
            Treat treat = Treat.builder()
                    .teacher(teacher)
                    .instrument(treatRepository.findInstrument(instrumentName))
                    .build();
            treatRepository.save(treat);
        }
    }

    public void deleteMyTreat(List<Treat> treats){
        for (Treat treat: treats ) {
            treatRepository.delete(treat);
        }
    }
}
