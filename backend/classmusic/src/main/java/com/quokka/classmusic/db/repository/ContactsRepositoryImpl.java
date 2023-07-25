package com.quokka.classmusic.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.response.ContactsVo;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

import static com.quokka.classmusic.db.entity.QContact.contact;
import static com.quokka.classmusic.db.entity.QTeacher.teacher;
import static com.quokka.classmusic.db.entity.QUser.user;

@Repository
public class ContactsRepositoryImpl implements ContactsRepository {
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory query;

    public ContactsRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<ContactsVo> findAll(Map<String ,Integer> params) {
        Integer id = Integer.parseInt(String.valueOf(params.get("id")));
        Integer state = Integer.parseInt(String.valueOf(params.get("state")));
        Integer type = Integer.parseInt(String.valueOf(params.get("type")));
        if(type != null){
            if(type == 0){
                return query.select(Projections.constructor(ContactsVo.class ,
                                teacher.user.name,
                                teacher.user.userProfileImage,
                                contact.studentMemo,
                                contact.studentOrder))
                        .from(contact)
                        .where(stateEq(state))
                        .join(contact.student , user)
                        .where(userIdEq(id))
                        .join(contact.teacher , teacher)
                        .orderBy(contact.studentOrder.desc())
                        .fetch();
            } else if(type == 1){
                return query.select(Projections.constructor(ContactsVo.class ,
                                user.name,
                                user.userProfileImage,
                                contact.teacherMemo,
                                contact.teacherOrder))
                        .from(contact)
                        .where(stateEq(state))
                        .join(contact.teacher , teacher)
                        .where(teacherIdEq(id))
                        .join(contact.student , user)
                        .orderBy(contact.teacherOrder.desc())
                        .fetch();
            }
        }
        return null;
    }

    @Override
    public void save(Contact contact) {
        em.persist(contact);
    }

    @Override
    public Contact findById(int contactId) {
        return em.find(Contact.class , contactId);
    }

    @Override
    public void delete(Contact contact) {
        em.remove(contact);
    }

    @Override
    public int maxOrder(Integer id , Integer state, Integer type) {
        Integer order = 0;
        if(type == 0){
            order = query.select(contact.studentOrder.max())
                    .from(contact)
                    .where(contact.student.eq(em.find(User.class , id)))
                    .fetchOne();
        } else if(type == 1){
            order = query.select(contact.teacherOrder.max())
                    .from(contact)
                    .where(contact.teacher.eq(em.find(Teacher.class , id)))
                    .fetchOne();
        }
        return order == null ? 0 : order;
    }


    private BooleanExpression userIdEq(Integer id){
        if(id == null){
            return null;
        }
        return user.userId.eq(id);
    }

    private BooleanExpression teacherIdEq(Integer id){
        if(id == null){
            return null;
        }
        return teacher.teacherId.eq(id);
    }

    private BooleanExpression stateEq(Integer state){
        if(state == null){
            return null;
        }
        return contact.state.eq(state);
    }
}
