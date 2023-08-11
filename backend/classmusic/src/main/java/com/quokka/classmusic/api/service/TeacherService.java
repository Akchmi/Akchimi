package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.TeacherDto;
import com.quokka.classmusic.api.request.TeacherSelectDto;
import com.quokka.classmusic.api.response.TeacherDetailVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.api.response.UserDetailsVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    List<TeacherVo> selectAllTeacher(Map<String, String> params);
    TeacherDetailVo selectDetailTeacher(int teacherId , UserDetailsVo userDetailsVo);
    int insertTeacher(TeacherDto teacherDto);
    void updateTeacher(int teacherId , TeacherDto teacherDto);
    void deleteTeacher(int teacherId);
    void insertImage(int teacherId , List<MultipartFile> multipartFiles);
    void deleteImage(int teacherId , List<String> files);
}
