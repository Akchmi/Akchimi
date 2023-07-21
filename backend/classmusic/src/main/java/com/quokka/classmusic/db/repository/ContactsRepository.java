package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.db.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<Contact, Integer> {

}
