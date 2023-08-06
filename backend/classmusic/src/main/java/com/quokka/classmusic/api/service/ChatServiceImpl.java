package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ChatInsertDto;
import com.quokka.classmusic.api.response.ChatUserVo;
import com.quokka.classmusic.api.response.ChatsVo;
import com.quokka.classmusic.db.entity.Chat;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.ChatRepository;
import com.quokka.classmusic.db.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService{
    ChatRepository chatRepository;
    ContactsRepository contactsRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, ContactsRepository contactsRepository) {
        this.chatRepository = chatRepository;
        this.contactsRepository = contactsRepository;
    }

    @Override
    public List<ChatsVo> selectAll(int contactId) {
        return chatRepository.findbycontentId(contactId);
    }

    @Override
    public void insertChat(int contactId , ChatInsertDto chatInsertDto) {
        Chat chat = Chat.builder()
                .contact(contactsRepository.findById(contactId))
                .content(chatInsertDto.getContent())
                .sender(chatInsertDto.getSender())
                .build();
        chatRepository.save(chat);
    }

    @Override
    public ChatUserVo selectChatUser(int contactId) {
        Contact contact = contactsRepository.findById(contactId);
        User student = contact.getStudent();
        User teacher = contact.getTeacher().getUser();

        return new ChatUserVo(
            student.getName(),
            student.getUserProfileImage(),
            teacher.getName(),
            teacher.getUserProfileImage()
        );
    }
}
