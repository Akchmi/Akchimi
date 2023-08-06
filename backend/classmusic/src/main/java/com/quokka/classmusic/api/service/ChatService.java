package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ChatInsertDto;
import com.quokka.classmusic.api.response.ChatUserVo;
import com.quokka.classmusic.api.response.ChatsVo;
import com.quokka.classmusic.db.entity.Chat;

import java.util.List;

public interface ChatService {
    List<ChatsVo> selectAll(int contactId);
    void insertChat(int contactId , ChatInsertDto chatInsertDto);
    ChatUserVo selectChatUser(int contactId);
}
