package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.request.ContactsUpdateMemoDto;
import com.quokka.classmusic.api.request.ContactsUpdateStateDto;
import com.quokka.classmusic.api.response.ContactsListVo;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ContactsServiceImpl implements ContactsService{
    private ContactsRepository contactsRepository;

    @Autowired
    public ContactsServiceImpl(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    //    내 매칭 보기
    @Override
    public List<ContactsListVo> selectAllContacts(Map<String,Integer> params) throws Exception {
        List<ContactsListVo> Vo = new ArrayList<>();
        List<Contact> list = contactsRepository.findAll(params);
        System.out.println(list.size());
        return Vo;
    }
//    내 매칭 삭제
    @Override
    public void deleteContacts(int contactId) throws Exception {
        Contact contact = contactsRepository.findById(contactId);
        contactsRepository.delete(contact);
    }
//    매칭 상태 바꾸기
    @Override
    public int updateContactsState(int contactId , ContactsUpdateStateDto contactsUpdateStateDto) throws Exception {
        Contact contact = contactsRepository.findById(contactId);
        contact.setState(contactsUpdateStateDto.getState());
        contactsRepository.save(contact);
        return 1;
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
    public void updateContactsOrder() throws Exception {

    }
//    매칭 생성하기
    @Override
    public int insertContacts(ContactsInsertDto contactsInsertDto) throws Exception {
        return contactsRepository.insertContacts(contactsInsertDto);
    }
//    강의실 입장
    @Override
    public String selectContactsRoom(int contactId) throws Exception {
        return contactsRepository.findById(contactId).getRoomKey();
    }
}
