package com.quokka.classmusic.db.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.TeacherFile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

import static com.quokka.classmusic.db.entity.QContact.contact;
import static com.quokka.classmusic.db.entity.QInstrument.instrument;
import static com.quokka.classmusic.db.entity.QLike.like;
import static com.quokka.classmusic.db.entity.QReview.review;
import static com.quokka.classmusic.db.entity.QTeacher.teacher;
import static com.quokka.classmusic.db.entity.QTeacherFile.teacherFile;
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
    public List<Teacher> findAll(Map<String, String> params) {
        return query.select(teacher)
                .distinct()
                .from(treat)
                .join(treat.teacher , teacher)
                .join(treat.instrument , instrument)
                .join(teacher.user , user)
                .where(teacher.visible.eq(1))
                .where(selectTeacherFilter(params))
                .where(selectTeacherIntroduceFilter(params.get("keyword")))
                .where(selectGenderFilter(params))
                .where(instrumentEq(params.get("instrument")))
                .where(getBitAndTemplate(Integer.parseInt(params.get("classDay"))).gt(0))
                .offset((Long.parseLong(params.get("page")) - 1) * 20)
                .limit(20)
                .orderBy(orderType(String.valueOf(params.get("orderBy"))))
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

    @Override
    public long findReviewCount(int teacherId) {
        return query.select(review.count())
                .from(review)
                .join(review.contact , contact)
                .join(contact.teacher , teacher)
                .where(teacher.teacherId.eq(teacherId))
                .fetchOne();
    }

    @Override
    public float findReviewSum(int teacherId) {
        return query.select(review.rating.sum())
                .from(review)
                .join(review.contact , contact)
                .join(contact.teacher , teacher)
                .where(teacher.teacherId.eq(teacherId))
                .fetchOne();
    }

    @Override
    public void deleteImage(int teacherId , String file) {
        em.remove(query.selectFrom(teacherFile)
                .join(teacherFile.teacher , teacher)
                .where(teacher.teacherId.eq(teacherId).and(teacherFile.fileUrl.eq(file)))
                .fetchOne());
    }

    @Override
    public void saveImage(TeacherFile teacherFile) {
        em.persist(teacherFile);
    }

    @Override
    public List<String> findImageByTeacherId(int teacherId) {
        return query.select(teacherFile.fileUrl)
                .from(teacherFile)
                .join(teacherFile.teacher , teacher)
                .where(teacher.teacherId.eq(teacherId))
                .fetch();
    }

    @Override
    public Integer findLikeById(int userId , int teacherId) {
        return query.select(like.favoriteId)
                .from(like)
                .where(like.student.userId.eq(userId) , like.teacher.teacherId.eq(teacherId))
                .fetchOne();
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
        if(ins.equals("악기종류") || ins.equals("")){
            return null;
        }
        return instrument.instrumentName.eq(ins);
    }

    BooleanExpression selectTeacherIntroduceFilter(String keyword){
        if(keyword.equals("")){
            return null;
        }
        String str = new StringBuilder().append('%').append(keyword).append('%').toString();
        return teacher.introduce.like(str).or(user.name.like(str));
    }

    private BooleanBuilder selectTeacherFilter(Map<String, String> params){
        BooleanBuilder builder = new BooleanBuilder();
        if(!params.get("startCareer").equals("")){
            builder.and(startCareerGoe(Integer.parseInt(String.valueOf(params.get("startCareer")))));
        }
        if(!params.get("endCareer").equals("")){
            builder.and(endCareerLoe(Integer.parseInt(String.valueOf(params.get("endCareer")))));
        }
        if(!params.get("startTime").equals("")){
            builder.and(startTimeGoe(Integer.parseInt(String.valueOf(params.get("startTime")))));
        }
        if(!params.get("endTime").equals("")){
            builder.and(endTimeLoe(Integer.parseInt(String.valueOf(params.get("endTime")))));
        }
        if(!params.get("startCost").equals("")){
            builder.and(startCostGoe(Integer.parseInt(String.valueOf(params.get("startCost")))));
        }
        if(!params.get("endCost").equals("")){
            builder.and(endCostLoe(Integer.parseInt(String.valueOf(params.get("endCost")))));
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
        if(orderBy.equals("별점순")){
            return new OrderSpecifier(Order.DESC , teacher.avgRating);
        } else if(orderBy.equals("매칭순")){
            return new OrderSpecifier(Order.DESC , teacher.contactCnt);
        }
        return new OrderSpecifier(Order.DESC , teacher.teacherId);
    }

    private NumberTemplate<Integer> getBitAndTemplate(int day){
        return Expressions.numberTemplate(Integer.class , "function('bitand',{0} , {1})", teacher.classDay , day);
    }
}
