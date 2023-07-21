package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.request.ContactsSelectAllDto;
import com.quokka.classmusic.api.response.ContactsSelectAllVo;
import com.quokka.classmusic.db.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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
        return contactsRepository.insertContacts(contactsInsertDto);
    }
//    강의실 입장
    @Override
    public void selectContacts() throws Exception {

    }
}
