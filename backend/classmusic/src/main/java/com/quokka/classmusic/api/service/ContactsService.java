package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.request.ContactsSelectAllDto;
import com.quokka.classmusic.api.request.ContactsUpdateStateDto;
import com.quokka.classmusic.api.response.ContactsSelectAllVo;

import java.util.List;

public interface ContactsService {
    public List<ContactsSelectAllVo> selectAllContacts(ContactsSelectAllDto contactsSelectAllDto) throws Exception;
    public void deleteContacts(int contactId) throws Exception;
    public int updateContactsState(int contactId , ContactsUpdateStateDto contactsUpdateStateDto) throws Exception;
    public void updateContactsMemo() throws Exception;
    public void updateContactsOrder() throws Exception;
    public int insertContacts(ContactsInsertDto contactsInsertDto) throws Exception;
    public void selectContacts() throws Exception;
}
