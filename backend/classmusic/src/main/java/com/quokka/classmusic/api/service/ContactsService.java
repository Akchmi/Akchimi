package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.request.ContactsUpdateMemoDto;
import com.quokka.classmusic.api.request.ContactsUpdateOrderListDto;
import com.quokka.classmusic.api.request.ContactsUpdateStateDto;
import com.quokka.classmusic.api.response.ContactsVo;

import java.util.List;
import java.util.Map;

public interface ContactsService {
    public List<ContactsVo> selectAllContacts(Map<String, Integer> params);
    public void deleteContacts(int contactId);
    public void updateContactsState(int contactId , ContactsUpdateStateDto contactsUpdateStateDto);
    public void updateContactsMemo(int contactId , ContactsUpdateMemoDto contactsUpdateMemoDto);
    public void updateContactsOrder(ContactsUpdateOrderListDto contactsUpdateOrderListDto);
    public int insertContacts(ContactsInsertDto contactsInsertDto);
    public String selectContactsRoom(int contactId);
}
