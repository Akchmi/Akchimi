package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository
public class ContactsRepositoryImpl implements ContactsRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Contact> findAll(Map<String ,Integer> params) {
        return em.createQuery("select c.contactId from Contact c").getResultList();
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
}
