package com.quokka.classmusic.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.request.FindIdDto;
import com.quokka.classmusic.db.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.quokka.classmusic.db.entity.QUser.user;

@Repository
public class UserRepositoryImpl implements UserRepository{
    private final EntityManager em;
    private final JPAQueryFactory query;

    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public User findUserById(String id) {
        return query.select(user)
                .from(user)
                .where(user.id.eq(id))
                .fetchOne();
    }

    @Override
    public User findById(int userId) {
        return em.find(User.class, userId);
    }

    @Override
    public User findId(FindIdDto findIdDto) {
        return query
                .select(user)
                .from(user)
                .where(user.email.eq(findIdDto.getEmail()).and(user.name.eq(findIdDto.getName())))
                .fetchOne();
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public void delete(User user) {
        em.remove(user);
    }
}
