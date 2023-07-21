package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.request.ContactsSelectAllDto;
import com.quokka.classmusic.api.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
    private ContactsService contactsService;

    @Autowired
    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }


//    내 매칭 보기
    @GetMapping("")
    public ResponseEntity<Void> selectAllContacts(@RequestBody ContactsSelectAllDto contactsSelectAllDto){
        try {
            contactsService.selectAllContacts(contactsSelectAllDto);
            return ResponseEntity.status(200).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    매칭 삭제하기
    @DeleteMapping
    public ResponseEntity<Void> deleteContacts(@RequestParam int contactId){
        try {
            contactsService.deleteContacts(contactId);
            return ResponseEntity.status(200).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    매칭 생성하기
    @PostMapping
    public ResponseEntity<Integer> insertContacts(@RequestBody ContactsInsertDto contactsInsertDto){
        try {
            return ResponseEntity.status(200).body(contactsService.insertContacts(contactsInsertDto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
