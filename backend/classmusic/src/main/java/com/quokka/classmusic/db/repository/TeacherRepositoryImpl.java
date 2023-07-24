package com.quokka.classmusic.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.db.entity.Teacher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public Teacher findById(int teacherId) {
        return em.find(Teacher.class , teacherId);
    }
}
