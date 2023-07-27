package com.quokka.classmusic.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.api.response.ChatsVo;
import com.quokka.classmusic.db.entity.Chat;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.quokka.classmusic.db.entity.QChat.chat;

@Repository
public class ChatRepositoryImpl implements ChatRepository{
    private final EntityManager em;
    private final JPAQueryFactory query;

    public ChatRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public void save(Chat chat) {
        em.persist(chat);
    }

    @Override
    public Chat findByid(int chatId) {
        return em.find(Chat.class , chatId);
    }

    @Override
    public void delete(Chat chat) {
        em.remove(chat);
    }

    @Override
    public List<ChatsVo> findbycontentId(int contentId) {
        return query
                .select(Projections.constructor(ChatsVo.class,
                                chat.content,
                                chat.sender,
                                chat.createdAt))
                .from(chat)
                .where(chat.contact.contactId.eq(contentId))
                .orderBy(chat.createdAt.desc())
                .fetch();
    }
}
