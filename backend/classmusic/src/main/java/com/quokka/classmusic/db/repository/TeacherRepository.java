package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.TeacherFile;

import java.util.List;
import java.util.Map;

public interface TeacherRepository {
    List<Teacher> findAll(Map<String, String> params);
    Teacher findById(int teacherId);
    void save(Teacher teacher);
    void delete(Teacher teacher);
    long findReviewCount(int teacherId);
    float findReviewSum(int teacherId);
    void deleteImage(int teacherId);
    void saveImage(TeacherFile teacherFile);
}
