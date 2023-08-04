package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.*;
import com.quokka.classmusic.api.response.ContactsVo;
import com.quokka.classmusic.api.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
@CrossOrigin("*")
public class ContactsController {
    private ContactsService contactsService;

    @Autowired
    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

//    내 매칭 보기
    @GetMapping("")
    public ResponseEntity<List<ContactsVo>> selectAllContacts(@RequestParam Map<String, Integer> params){
        return new ResponseEntity<>(contactsService.selectAllContacts(params), HttpStatus.OK);
    }

//    매칭 삭제하기
    @DeleteMapping
    public ResponseEntity<Void> deleteContacts(@RequestParam int contactId){
        contactsService.deleteContacts(contactId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    매칭 생성하기
    @PostMapping
    public ResponseEntity<Integer> insertContacts(@RequestBody ContactsInsertDto contactsInsertDto){
        return new ResponseEntity<>(contactsService.insertContacts(contactsInsertDto), HttpStatus.CREATED);
    }
//  매칭 상태 수정
    @PutMapping("/{contactId}/state")
    public ResponseEntity<Void> updateContactsState(@PathVariable("contactId") int contactId ,@RequestBody ContactsUpdateStateDto contactsUpdateStateDto){
        contactsService.updateContactsState(contactId ,contactsUpdateStateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
//    매칭 순서 바꾸기
    @PutMapping
    public ResponseEntity<Void> updateContactsOrder(@RequestBody ContactsUpdateOrderListDto contactsUpdateOrderListDto){
        contactsService.updateContactsOrder(contactsUpdateOrderListDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    매칭 메모 수정
    @PutMapping("/{contactId}/memo")
    public ResponseEntity<Void> updateContactsMemo(@PathVariable("contactId") int contactId , @RequestBody ContactsUpdateMemoDto contactsUpdateMemoDto){
        contactsService.updateContactsMemo(contactId ,contactsUpdateMemoDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

// 강의실 입장url 가져오기
    @GetMapping("/{contactId}/meeting-room")
    public ResponseEntity<String> selectContactsRoom(@PathVariable("contactId") int contactId){
        return new ResponseEntity<>(contactsService.selectContactsRoom(contactId), HttpStatus.OK);
    }
}
