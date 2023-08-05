package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ChatInsertDto;
import com.quokka.classmusic.api.response.ChatsVo;
import com.quokka.classmusic.api.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        try {
            return ResponseEntity.status(200).body(chatService.selectAll(contactId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{contactId}")
    public ResponseEntity<Integer> insertChat(@PathVariable int contactId , @RequestBody ChatInsertDto chatInsertDto){
        log.debug("contactId : {}, dto : {}", contactId, chatInsertDto);
        try {
            chatService.insertChat(contactId, chatInsertDto);
            return ResponseEntity.status(200).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
