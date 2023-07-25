package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.TeacherDto;
import com.quokka.classmusic.api.response.TeacherDetailVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.db.entity.Instrument;
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
//       날짜 int로 변환해서 DB넣기
        String days = teacherDto.getClassDay();
        int day = 0;
        for (int i = 0; i < days.length(); i++) {
            if (days.charAt(i) == '1') {
                day |= (1 << i);
            }
        }

//        유저 타입 1로 바꿔줌
        User user = userRepository.findById(teacherDto.getUserId()).get();
        user.setType(1);
        userRepository.save(user);

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
        List<String> list = teacherDto.getInstruments();
        for (String instrumentName: list) {
            Treat treat = Treat.builder()
                    .teacher(teacher)
                    .instrument(treatRepository.findInstrument(instrumentName))
                    .build();
            treatRepository.save(treat);
        }
        return teacher.getTeacherId();
    }

    @Override
    public void updateTeacher(TeacherDto teacherDto) {

    }
    @Override
    public void deleteTeacher(int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId);
        teacherRepository.delete(teacher);
    }
}
