package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.*;
import com.quokka.classmusic.api.response.ContactsVo;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.repository.ContactsRepository;
import com.quokka.classmusic.db.repository.TeacherRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ContactsServiceImpl implements ContactsService{
    private ContactsRepository contactsRepository;
    private UserRepository userRepository;
    private TeacherRepository teacherRepository;

    @Autowired
    public ContactsServiceImpl(ContactsRepository contactsRepository, UserRepository userRepository, TeacherRepository teacherRepository) {
        this.contactsRepository = contactsRepository;
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
    }
//    내 매칭 보기
    @Override
    public List<ContactsVo> selectAllContacts(Map<String,Integer> params) throws Exception {
        return contactsRepository.findAll(params);
    }
//    매칭 삭제
    @Override
    public void deleteContacts(int contactId) throws Exception {
        Contact contact = contactsRepository.findById(contactId);
        contactsRepository.delete(contact);
    }
//    매칭 상태 바꾸기
    @Override
    public void updateContactsState(int contactId , ContactsUpdateStateDto contactsUpdateStateDto) throws Exception {
        Contact contact = contactsRepository.findById(contactId);
        int state = contactsUpdateStateDto.getState();
//        수락 시 매칭 수 증가 > 시간 넣어줘야됨
        if(state == 1){
            Teacher teacher = contact.getTeacher();
            teacher.setContactCnt(teacher.getContactCnt() + 1);
            contact.setStartTime((int) (System.currentTimeMillis() / 1000));
        } else if(state == 2){
            contact.setEndTime((int) (System.currentTimeMillis() / 1000));
        }
        else if(state == 4){
            contact.setEndTime((int) (System.currentTimeMillis() / 1000));
        }
        contact.setState(state);
        contactsRepository.save(contact);
    }
//    매칭 메모 바꾸기
    @Override
    public void updateContactsMemo(int contactId , ContactsUpdateMemoDto contactsUpdateMemoDto) throws Exception {
        Contact contact = contactsRepository.findById(contactId);
        if(contactsUpdateMemoDto.getType() == 0){
            contact.setStudentMemo(contactsUpdateMemoDto.getMemo());
        } else {
            contact.setTeacherMemo(contactsUpdateMemoDto.getMemo());
        }
        contactsRepository.save(contact);
    }
//    매칭 순서 바꾸기
    @Override
    public void updateContactsOrder(ContactsUpdateOrderListDto contactsUpdateOrderListDto) throws Exception {
        int type = contactsUpdateOrderListDto.getType();
        if(contactsUpdateOrderListDto.getType() == 0){
            for (ContactsUpdateOrderDto contactsUpdateOrderDto : contactsUpdateOrderListDto.getContacts()) {
                Contact contact = contactsRepository.findById(contactsUpdateOrderDto.getContactId());
                contact.setStudentOrder(contactsUpdateOrderDto.getOrder());
                contactsRepository.save(contact);
            }
        } else if(contactsUpdateOrderListDto.getType() == 1){
            for (ContactsUpdateOrderDto contactsUpdateOrderDto : contactsUpdateOrderListDto.getContacts()) {
                Contact contact = contactsRepository.findById(contactsUpdateOrderDto.getContactId());
                contact.setTeacherOrder(contactsUpdateOrderDto.getOrder());
                contactsRepository.save(contact);
            }
        } else{
            return;
        }
    }
//    매칭 생성하기
    @Override
    public int insertContacts(ContactsInsertDto contactsInsertDto) throws Exception {
        Contact contact = Contact.builder()
                .student(userRepository.findById(contactsInsertDto.getStudentId()))
                .teacher(teacherRepository.findById(contactsInsertDto.getTeacherId()))
                .state(0)
                .studentOrder(contactsRepository.maxOrder(contactsInsertDto.getStudentId() ,0,0) + 1)
                .teacherOrder(contactsRepository.maxOrder(contactsInsertDto.getTeacherId() ,0,1) + 1)
                .build();
        contactsRepository.save(contact);
        return contact.getContactId();
    }
//    강의실 입장
    @Override
    public String selectContactsRoom(int contactId) throws Exception {
        return contactsRepository.findById(contactId).getRoomKey();
    }
}
