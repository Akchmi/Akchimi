package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.request.ContactsSelectAllDto;
import com.quokka.classmusic.api.response.ContactsSelectAllVo;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService{
    @PersistenceUnit
    private EntityManagerFactory emf;
    @PersistenceContext
    private EntityManager em;
    private ContactsRepository contactsRepository;

    @Autowired
    public ContactsServiceImpl(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }
//    내 매칭 보기
    @Override
    public List<ContactsSelectAllVo> selectAllContacts(ContactsSelectAllDto contactsSelectAllDto) throws Exception {
        List<ContactsSelectAllVo> list = null;
        contactsRepository.findAll();
        return list;
    }
//    내 매칭 삭제
    @Override
    public void deleteContacts(int contactId) throws Exception {
        contactsRepository.deleteById(contactId);
    }
//    매칭 상태 바꾸기
    @Override
    public void updateContactsState() throws Exception {

    }
//    매칭 메모 바꾸기
    @Override
    public void updateContactsMemo() throws Exception {

    }
//    매칭 순서 바꾸기
    @Override
    public void updateContactsOrder() throws Exception {

    }
//    매칭 생성하기
    @Override
    public int insertContacts(ContactsInsertDto contactsInsertDto) throws Exception {
        User user = em.find(User.class,contactsInsertDto.getStudentId());
        Teacher teacher = em.find(Teacher.class , contactsInsertDto.getTeacherId());
        Contact contact = new Contact();
        contact.setStudent(user);
        contact.setTeacher(teacher);
        contactsRepository.save(contact);
        return 1;
    }
//    강의실 입장
    @Override
    public void selectContacts() throws Exception {

    }
}
