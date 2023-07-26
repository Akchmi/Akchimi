package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.db.entity.Like;

import java.util.List;

public interface LikeRepository {
    public void save(Like like);
    public void delete(Like like);
    public List<TeacherVo> findAll(int userId);

}
