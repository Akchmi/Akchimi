package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.TeacherDto;
import com.quokka.classmusic.api.request.TeacherSelectDto;
import com.quokka.classmusic.api.response.TeacherDetailVo;
import com.quokka.classmusic.api.response.TeacherVo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface TeacherService {
    List<TeacherVo> selectAllTeacher(Map<String, Objects> params);
    TeacherDetailVo selectDetailTeacher(int teacherId);
    int insertTeacher(TeacherDto teacherDto);
    void updateTeacher(int teacherId , TeacherDto teacherDto);
    void deleteTeacher(int teacherId);
}
