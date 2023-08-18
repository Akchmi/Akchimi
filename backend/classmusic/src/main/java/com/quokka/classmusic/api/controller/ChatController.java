package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ChatInsertDto;
import com.quokka.classmusic.api.response.ChatUserVo;
import com.quokka.classmusic.api.response.ChatsVo;
import com.quokka.classmusic.api.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<List<ChatsVo>> selectAllChat(@PathVariable int contactId){
        return new ResponseEntity<>(chatService.selectAll(contactId), HttpStatus.OK);
    }

    @PostMapping("/{contactId}")
    public ResponseEntity<Integer> insertChat(@PathVariable int contactId , @RequestBody ChatInsertDto chatInsertDto){
        log.debug("contactId : {}, dto : {}", contactId, chatInsertDto);
        chatService.insertChat(contactId, chatInsertDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{contactId}/profile")
    public ResponseEntity<ChatUserVo> selectChatProfile(@PathVariable int contactId){
        return new ResponseEntity<>(chatService.selectChatUser(contactId), HttpStatus.OK);
    }
}
