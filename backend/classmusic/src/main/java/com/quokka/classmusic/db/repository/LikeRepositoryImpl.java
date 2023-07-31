package com.quokka.classmusic.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.db.entity.Like;
import com.quokka.classmusic.db.entity.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.quokka.classmusic.db.entity.QLike.like;
import static com.quokka.classmusic.db.entity.QTeacher.teacher;
import static com.quokka.classmusic.db.entity.QUser.user;

@Slf4j
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
                .from(like)
                .join(like.teacher, teacher)
                .join(teacher.user, user)
                .where(like.student.userId.eq(userId))
                .fetch();
    }

    @Override
    public Like find(int likeId) {
        log.debug("################in repo################ : {}",likeId);
        return em.find(Like.class, likeId);
    }

    @Override
    public boolean duplicationCheck(Like checkLike) {
        Like searchByStudentAndTeacherId = query
                .select(like)
                .from(like)
                .where(like.student.userId.eq(checkLike.getStudent().getUserId()).and(like.teacher.teacherId.eq(checkLike.getTeacher().getTeacherId())))
                .fetchOne();
        if(searchByStudentAndTeacherId==null){
            return false;
        }else{
            return true;
        }
    }
}
