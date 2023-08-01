package com.quokka.classmusic.db.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.response.TeacherDetailVo;
import com.quokka.classmusic.db.entity.Teacher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

import static com.quokka.classmusic.db.entity.QInstrument.instrument;
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
    public TeacherDetailVo findDetailById(int teacherId) {
        return query.select(Projections.constructor(TeacherDetailVo.class ,
                user.name,
                user.gender,
                user.userProfileImage,
                teacher.career,
                teacher.cost,
                teacher.introduce,
                teacher.startTime,
                teacher.endTime,
                teacher.avgRating,
                teacher.contactCnt))
                .from(teacher)
                .where(teacher.teacherId.eq(teacherId))
                .join(teacher.user , user)
                .fetchOne();
    }

    @Override
    public List<Teacher> findAll(Map<String, String> params) {
        System.out.println(params.get("keyword"));
        System.out.println(params.get("start_career"));
        System.out.println(params.get("end_career"));
        System.out.println(params.get("gender"));
        System.out.println(params.get("class_day"));
        System.out.println(params.get("start_time"));
        System.out.println(params.get("end_time"));
        System.out.println(params.get("start_cost"));
        System.out.println(params.get("instrument"));
        System.out.println(params.get("order_by"));
        System.out.println(params.get("page"));
        StringBuilder keyword = new StringBuilder();
        keyword.append("%").append(params.get("keyword")).append("%");

        return query.select(teacher)
                .from(teacher)
                .where(selectTeacherFilter(params).and(selectTeacherIntroduceFilter(params.get("keyword"))))
                .join(teacher.user , user)
                .where(selectGenderFilter(params))
                .join(teacher.treats , treat)
                .join(treat.instrument , instrument)
                .where(selectInstrumentFilter(params.get("instrument")))
                .offset(Integer.parseInt(String.valueOf(params.get("page"))))
                .limit(20)
                .orderBy(orderType(String.valueOf(params.get("order_by"))))
                .fetch();
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


    private BooleanExpression selectInstrumentFilter(String instrumentName) {
        if(instrumentName.equals("")){
            return null;
        }
        return instrument.instrumentName.eq(instrumentName);
    }

    private BooleanExpression startCareerGoe(Integer startCareer){
        if(startCareer == null){
            return null;
        }
        return teacher.career.goe(startCareer);
    }

    private BooleanExpression endCareerLoe(Integer endCareer){
        if(endCareer == null){
            return null;
        }
        return teacher.career.loe(endCareer);
    }

    private BooleanExpression genderEq(Integer gender){
        if(gender == null){
            return null;
        }
        return user.gender.eq(gender);
    }

    private BooleanExpression startTimeGoe(Integer startTime){
        if(startTime == null){
            return null;
        }
        return teacher.endTime.goe(startTime);
    }

    private BooleanExpression endTimeLoe(Integer endTime){
        if(endTime == null){
            return null;
        }
        return teacher.startTime.loe(endTime);
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
        if(keyword.equals("")){
            return null;
        }
        return teacher.introduce.like(new StringBuilder().append('%').append(keyword).append('%').toString());
    }

    private BooleanBuilder selectTeacherFilter(Map<String, String> params){
        BooleanBuilder builder = new BooleanBuilder();
        if(!params.get("start_career").equals("")){
            builder.and(startCareerGoe(Integer.parseInt(String.valueOf(params.get("start_career")))));
        }
        if(!params.get("end_career").equals("")){
            builder.and(endCareerLoe(Integer.parseInt(String.valueOf(params.get("end_career")))));
        }
        if(!params.get("start_time").equals("")){
            builder.and(startTimeGoe(Integer.parseInt(String.valueOf(params.get("start_time")))));
        }
        if(!params.get("end_time").equals("")){
            builder.and(endTimeLoe(Integer.parseInt(String.valueOf(params.get("end_time")))));
        }
        if(!params.get("start_cost").equals("")){
            builder.and(startCostGoe(Integer.parseInt(String.valueOf(params.get("start_cost")))));
        }
        if(!params.get("end_cost").equals("")){
            builder.and(endCostLoe(Integer.parseInt(String.valueOf(params.get("end_cost")))));
        }



        return builder;
    }

    private BooleanBuilder selectGenderFilter(Map<String, String> params){
        BooleanBuilder builder = new BooleanBuilder();
        if(!params.get("gender").equals("")){
            builder.and(genderEq(Integer.parseInt(String.valueOf(params.get("gender")))));
        }
        return builder;
    }


    private OrderSpecifier orderType(String orderBy){
        if(orderBy.equals("별점수")){
            return new OrderSpecifier(Order.DESC , teacher.avgRating);
        } else if(orderBy.equals("매칭순")){
            return new OrderSpecifier(Order.DESC , teacher.contactCnt);
        }
        return new OrderSpecifier(Order.DESC , teacher.teacherId);
    }
}
