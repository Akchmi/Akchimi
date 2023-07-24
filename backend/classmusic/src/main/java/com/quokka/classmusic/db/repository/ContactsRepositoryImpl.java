package com.quokka.classmusic.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.response.ContactsVo;
import com.quokka.classmusic.api.response.ReviewVo;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
                        .where(idEq(id))
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
                        .where(teacheridEq(id))
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
    public int insertContacts(ContactsInsertDto contactsInsertDto) {
        User user = em.find(User.class, contactsInsertDto.getStudentId());
        Teacher teacher = em.find(Teacher.class, contactsInsertDto.getTeacherId());
        Contact contact = Contact.builder()
                .student(user)
                .teacher(teacher)
                .state(1)
                .build();
        em.persist(contact);
        return 1;
    }

    private BooleanExpression idEq(Integer id){
        if(id == null){
            return null;
        }
        return user.userId.eq(id);
    }

    private BooleanExpression teacheridEq(Integer id){
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
