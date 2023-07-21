package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "chat")
public class Chat {
    @Builder
    public Chat(int chatId, Contact contact, String content, Integer createdAt, Integer sender) {
        this.chatId = chatId;
        this.contact = contact;
        this.content = content;
        this.createdAt = createdAt;
        this.sender = sender;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private int chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id")
    private Contact contact;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "created_at")
    private Integer createdAt;

    @Column(name = "sender")
    private Integer sender;

    // Constructors, getters, and setters
}