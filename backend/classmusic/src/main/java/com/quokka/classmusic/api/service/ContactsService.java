package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.request.ContactsUpdateMemoDto;
import com.quokka.classmusic.api.request.ContactsUpdateOrderListDto;
import com.quokka.classmusic.api.request.ContactsUpdateStateDto;
import com.quokka.classmusic.api.response.ContactsVo;

import java.util.List;
import java.util.Map;

public interface ContactsService {
    List<ContactsVo> selectAllContacts(Map<String, Integer> params);
    void deleteContacts(int contactId);
    void updateContactsState(int contactId , ContactsUpdateStateDto contactsUpdateStateDto);
    void updateContactsMemo(int contactId , ContactsUpdateMemoDto contactsUpdateMemoDto);
    void updateContactsOrder(ContactsUpdateOrderListDto contactsUpdateOrderListDto);
    int insertContacts(ContactsInsertDto contactsInsertDto);
    String selectContactsRoom(int contactId);
}
