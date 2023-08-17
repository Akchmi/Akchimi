package com.quokka.classmusic.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.db.entity.Event;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.quokka.classmusic.db.entity.QEvent.event;
import static com.quokka.classmusic.db.entity.QUser.user;

@Repository
public class EventRepositoryImpl implements EventRepository{
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory query;

    public EventRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Event> findAll(int userId) {
        return query
                .selectFrom(event)
                .join(user)
                .where(user.userId.eq(userId))
                .fetch();
    }

    @Override
    public void save(Event event) {
        em.persist(event);
    }

    @Override
    public Event findById(int eventId) {
        return em.find(Event.class , eventId);
    }

    @Override
    public void delete(Event event) {
        em.remove(event);
    }
}
