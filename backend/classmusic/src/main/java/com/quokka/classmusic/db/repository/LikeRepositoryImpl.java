package com.quokka.classmusic.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.db.entity.Like;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.quokka.classmusic.db.entity.QTeacher.teacher;
import static com.quokka.classmusic.db.entity.QUser.user;

@Repository
public class LikeRepositoryImpl implements LikeRepository{
    private EntityManager em;
    private JPAQueryFactory query;

    public LikeRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public void save(Like like) {
        em.persist(like);
    }

    @Override
    public void delete(Like like) {
        em.remove(like);
    }

    @Override
    public List<TeacherVo> findAll(int userId) {
        return query.select(Projections.constructor(TeacherVo.class,
                    user.name,
                    user.userProfileImage,
                    teacher.teacherId,
                    teacher.career,
                    teacher.introduce,
                    teacher.avgRating,
                    teacher.contactCnt))
                .from(teacher)
                .join(teacher.user, user)
                .fetch();
    }
}
