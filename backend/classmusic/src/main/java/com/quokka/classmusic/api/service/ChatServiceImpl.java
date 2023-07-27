package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ChatInsertDto;
import com.quokka.classmusic.api.response.ChatUserVo;
import com.quokka.classmusic.api.response.ChatsVo;
import com.quokka.classmusic.db.entity.Chat;
import com.quokka.classmusic.db.repository.ChatRepository;
import com.quokka.classmusic.db.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{
    ChatRepository chatRepository;
    ContactsRepository contactsRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, ContactsRepository contactsRepository) {
        this.chatRepository = chatRepository;
        this.contactsRepository = contactsRepository;
    }

    @Override
    public List<ChatsVo> selectAll(int contentId) {
        return chatRepository.findbycontentId(contentId);
    }

    @Override
    @Transactional
    public void insertChat(int contentId , ChatInsertDto chatInsertDto) {
        Chat chat = Chat.builder()
                .contact(contactsRepository.findById(contentId))
                .content(chatInsertDto.getContent())
                .sender(chatInsertDto.getSender())
                .build();
        chatRepository.save(chat);
    }

    @Override
    public ChatUserVo selectChatUser(int chatId) {
        return null;
    }
}
