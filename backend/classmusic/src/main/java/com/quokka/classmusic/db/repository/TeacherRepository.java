package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.db.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherRepository {
    List<Teacher> findAll(Map<String, String> params);
    Teacher findById(int teacherId);

    void save(Teacher teacher);
    void delete(Teacher teacher);
    long findReviewCount(int teacherId);
    float findReviewSum(int teacherId);
}
