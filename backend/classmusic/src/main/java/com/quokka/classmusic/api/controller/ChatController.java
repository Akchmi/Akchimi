package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ChatInsertDto;
import com.quokka.classmusic.api.request.ContactsInsertDto;
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
@CrossOrigin("*")
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<List<ChatsVo>> selectAllChat(@PathVariable int contentId){
        try {
            return ResponseEntity.status(200).body(chatService.selectAll(contentId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{contentId}")
    public ResponseEntity<Integer> insertChat(@PathVariable int contentId , @RequestBody ChatInsertDto chatInsertDto){
        try {
            chatService.insertChat(contentId, chatInsertDto);
            return ResponseEntity.status(200).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
