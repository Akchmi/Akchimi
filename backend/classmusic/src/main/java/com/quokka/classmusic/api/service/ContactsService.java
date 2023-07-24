package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.request.ContactsUpdateMemoDto;
import com.quokka.classmusic.api.request.ContactsUpdateStateDto;
import com.quokka.classmusic.api.response.ContactsVo;

import java.util.List;
import java.util.Map;

public interface ContactsService {
    public List<ContactsVo> selectAllContacts(Map<String, Integer> params) throws Exception;
    public void deleteContacts(int contactId) throws Exception;
    public int updateContactsState(int contactId , ContactsUpdateStateDto contactsUpdateStateDto) throws Exception;
    public void updateContactsMemo(int contactId , ContactsUpdateMemoDto contactsUpdateMemoDto) throws Exception;
    public void updateContactsOrder() throws Exception;
    public int insertContacts(ContactsInsertDto contactsInsertDto) throws Exception;
    public String selectContactsRoom(int contactId) throws Exception;
}
