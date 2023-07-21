package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.request.ContactsInsertDto;

public interface ContactsRepositoryCustom {
    int insertContacts(ContactsInsertDto contactsInsertDto);
}
