package com.quokka.classmusic.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.response.ReviewVo;
import com.quokka.classmusic.db.entity.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.quokka.classmusic.db.entity.QContact.contact;
import static com.quokka.classmusic.db.entity.QReview.review;
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
        return query.select(Projections.constructor(ReviewVo.class ,
                review.reviewId,
                user.name,
                user.userProfileImage,
                contact.startTime,
                contact.endTime,
                review.rating,
                review.content,
                review.createdAt
                ))
                .from(review)
                .join(review.contact , contact)
                .join(contact.student , user)
                .where(contact.teacher.teacherId.eq(teacherId))
                .fetch();
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
    public Review findReviewByContactId(int contactId) {
        return query.selectFrom(review)
                .join(review.contact , contact)
                .where(contact.contactId.eq(contactId))
                .fetchOne();
    }
}
