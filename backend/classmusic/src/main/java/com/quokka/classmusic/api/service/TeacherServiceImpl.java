package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.TeacherDto;
import com.quokka.classmusic.api.response.TeacherDetailVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.Treat;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.TeacherRepository;
import com.quokka.classmusic.db.repository.TreatRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{
    private TeacherRepository teacherRepository;
    private UserRepository userRepository;
    private TreatRepository treatRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, UserRepository userRepository, TreatRepository treatRepository) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.treatRepository = treatRepository;
    }

    @Override
    public List<TeacherVo> selectAllTeacher(Map<String, Objects> params) {
        return null;
    }

    @Override
    public TeacherDetailVo selectDetailTeacher(int teacherId) {
        return null;
    }

    @Override
    public int insertTeacher(TeacherDto teacherDto) {
//        유저 타입 1로 바꿔줌
        User user = userRepository.findById(teacherDto.getUserId()).get();
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
    public void deleteTeacher(int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId);
        teacherRepository.delete(teacher);
    }

    public int dayToInt(String days){
        int day = 0;
        for (int i = 0; i < days.length(); i++) {
            if (days.charAt(i) == '1') {
                day |= (1 << i);
            }
        }
        return day;
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
