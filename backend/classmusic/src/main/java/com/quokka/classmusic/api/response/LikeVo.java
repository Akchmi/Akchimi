package com.quokka.classmusic.api.response;

import com.quokka.classmusic.db.entity.Like;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikeVo {
    private int likeId;
    private User student;
    private Teacher teacher;

    public LikeVo(Like like) {
        this.likeId = like.getFavoriteId();
        this.student = like.getStudent();
        this.teacher = like.getTeacher();
    }
}
