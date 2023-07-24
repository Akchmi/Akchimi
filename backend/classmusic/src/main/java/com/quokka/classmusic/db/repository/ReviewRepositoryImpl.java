package com.quokka.classmusic.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.response.ReviewVo;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.quokka.classmusic.db.entity.QUser.user;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository{
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory query;

    public ReviewRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<ReviewVo> findAll(int teacherId) {
//        query.select(new ReviewVo(
//                user.name
//        ));
        return null;
    }

    @Override
    public void save(Review review) {
        em.persist(review);
    }

    @Override
    public Review findById(int reviewId) {
        return em.find(Review.class , reviewId);
    }

    @Override
    public void delete(Review review) {
        em.remove(review);
    }

    @Override
    public Contact findContactById(int contactId) {
        return em.find(Contact.class , contactId);
    }
}
