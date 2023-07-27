package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.response.ChatsVo;
import com.quokka.classmusic.db.entity.Chat;

import java.util.List;

public interface ChatRepository {
    void save(Chat chat);
    Chat findByid(int chatId);
    void delete(Chat chat);

    List<ChatsVo> findbycontentId(int contentId);
}
