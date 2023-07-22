package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ContactsInsertDto;
import com.quokka.classmusic.api.request.ContactsSelectAllDto;
import com.quokka.classmusic.api.request.ContactsUpdateMemoDto;
import com.quokka.classmusic.api.request.ContactsUpdateStateDto;
import com.quokka.classmusic.api.response.ContactsListVo;
import com.quokka.classmusic.api.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<ContactsListVo>> selectAllContacts(@RequestParam Map<String, Integer> params){
        try {
            return ResponseEntity.status(200).body(contactsService.selectAllContacts(params));
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
//  매칭 상태 수정
    @PutMapping("/{contactId}/state")
    public ResponseEntity<Integer> updateContactsState(@PathVariable("contactId") int contactId ,@RequestBody ContactsUpdateStateDto contactsUpdateStateDto){
        try {
            return ResponseEntity.status(200).body((contactsService.updateContactsState(contactId ,contactsUpdateStateDto)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    매칭 메모 수정
    @PutMapping("/{contactId}/memo")
    public ResponseEntity<Void> updateContactsMemo(@PathVariable("contactId") int contactId , @RequestBody ContactsUpdateMemoDto contactsUpdateMemoDto){
        try {
            contactsService.updateContactsMemo(contactId ,contactsUpdateMemoDto);
            return ResponseEntity.status(200).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

// 강의실 입장url 가져오기
    @GetMapping("/{contactId}/meeting-room")
    public ResponseEntity<String> selectContactsRoom(@PathVariable("contactId") int contactId){
        try {
            return ResponseEntity.status(200).body(contactsService.selectContactsRoom(contactId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
