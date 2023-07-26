package com.quokka.classmusic.db.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.request.TeacherSelectDto;
import com.quokka.classmusic.api.response.ReviewVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.db.entity.Teacher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

import static com.quokka.classmusic.db.entity.QContact.contact;
import static com.quokka.classmusic.db.entity.QInstrument.instrument;
import static com.quokka.classmusic.db.entity.QReview.review;
import static com.quokka.classmusic.db.entity.QTeacher.teacher;
import static com.quokka.classmusic.db.entity.QTreat.treat;
import static com.quokka.classmusic.db.entity.QUser.user;

@Repository
public class TeacherRepositoryImpl implements TeacherRepository{
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory query;

    public TeacherRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<TeacherVo> findAll(Map<String, Object> params) {
        StringBuilder keyword = new StringBuilder();
        keyword.append("%").append(params.get("keyword")).append("%");
        return query.select(Projections.constructor(TeacherVo.class ,
                        user.name,
                        user.userProfileImage,
                        teacher.teacherId,
                        teacher.career,
                        teacher.introduce,
                        teacher.avgRating,
                        teacher.contactCnt
                ))
                .from(teacher)
                .where(selectTeacherFilter(params).and(selectTeacherIntroduceFilter(String.valueOf(params.get("keyword")))))
                .join(teacher.user , user)
                .where(selectGenderFilter(params))
                .offset(Integer.parseInt(String.valueOf(params.get("page"))))
                .limit(20)
                .fetch();

//        keyword;
//        classDay;
//        orderBy;
//        page;
    }

    @Override
    public Teacher findById(int teacherId) {
        return em.find(Teacher.class , teacherId);
    }

    @Override
    public void save(Teacher teacher) {
        em.persist(teacher);
    }

    @Override
    public void delete(Teacher teacher) {
        em.remove(teacher);
    }

    private BooleanExpression startCareerGt(Integer startCareer){
        if(startCareer == null){
            return null;
        }
        return teacher.career.gt(startCareer);
    }

    private BooleanExpression endCareerLt(Integer endCareer){
        if(endCareer == null){
            return null;
        }
        return teacher.career.lt(endCareer);
    }

    private BooleanExpression genderEq(Integer gender){
        if(gender == null){
            return null;
        }
        return user.gender.eq(gender);
    }

    private BooleanExpression startTimeGt(Integer startTime){
        if(startTime == null){
            return null;
        }
        return teacher.endTime.gt(startTime);
    }

    private BooleanExpression endTimeLt(Integer endTime){
        if(endTime == null){
            return null;
        }
        return teacher.startTime.lt(endTime);
    }

    private BooleanExpression startCostGoe(Integer startCost){
        if(startCost == null){
            return null;
        }
        return teacher.cost.goe(startCost);
    }

    private BooleanExpression endCostLoe(Integer endCost){
        if(endCost == null){
            return null;
        }
        return teacher.cost.loe(endCost);
    }

    private BooleanExpression instrumentEq(String ins){
        if(ins == null){
            return null;
        }
        return instrument.instrumentName.eq(ins);
    }

    BooleanExpression selectTeacherIntroduceFilter(String keyword){
        if(!keyword.equals("")){
            return null;
        }
        return teacher.introduce.like(new StringBuilder().append("%").append(keyword).append("%").toString());
    }

    private BooleanBuilder selectTeacherFilter(Map<String, Object> params){
        BooleanBuilder builder = new BooleanBuilder();
        if(!params.get("start_career").equals("")){
            builder.and(startCareerGt(Integer.parseInt(String.valueOf(params.get("start_career")))));
        }
        if(!params.get("end_career").equals("")){
            builder.and(endCareerLt(Integer.parseInt(String.valueOf(params.get("end_career")))));
        }
        if(!params.get("start_time").equals("")){
            builder.and(startCareerGt(Integer.parseInt(String.valueOf(params.get("start_time")))));
        }
        if(!params.get("end_time").equals("")){
            builder.and(endCareerLt(Integer.parseInt(String.valueOf(params.get("end_time")))));
        }
        return builder;
    }

    private BooleanBuilder selectGenderFilter(Map<String, Object> params){
        BooleanBuilder builder = new BooleanBuilder();
        if(!params.get("gender").equals("")){
            builder.and(genderEq(Integer.parseInt(String.valueOf(params.get("gender")))));
        }
        return builder;
    }


}
