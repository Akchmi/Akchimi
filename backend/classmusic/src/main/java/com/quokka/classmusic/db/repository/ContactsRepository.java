package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.response.ContactsVo;
import com.quokka.classmusic.db.entity.Contact;

import java.util.List;
import java.util.Map;

public interface ContactsRepository  {
    List<ContactsVo> findAll(Map<String , Integer> params);
    void save(Contact contact);
    Contact findById(int contactId);
    void delete(Contact contact);
    int maxOrder(Integer id ,Integer state , Integer type);
    Contact findByPair(int studentId , int teacherId);
}
