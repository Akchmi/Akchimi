package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ContactsRepositoryCustomImpl implements ContactsRepositoryCustom{

    @PersistenceContext
    EntityManager em;

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
}
