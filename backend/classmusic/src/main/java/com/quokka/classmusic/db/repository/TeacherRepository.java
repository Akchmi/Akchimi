package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.db.entity.Teacher;

public interface TeacherRepository {
    Teacher findById(int teacherId);
    void save(Teacher teacher);
    void delete(Teacher teacher);
}
