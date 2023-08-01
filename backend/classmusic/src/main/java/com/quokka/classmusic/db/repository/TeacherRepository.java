package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.request.TeacherSelectDto;
import com.quokka.classmusic.api.response.TeacherDetailVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.db.entity.Teacher;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface TeacherRepository {
    TeacherDetailVo findDetailById(int teacherId);
    List<Teacher> findAll(Map<String, String> params);
    Teacher findById(int teacherId);
    void save(Teacher teacher);
    void delete(Teacher teacher);
    int findReviewCount(int teacherId);
    int findReviewSum(int teacherId);
}
