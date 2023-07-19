package com.quokka.classmusic.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private int noticeId;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content", length = 4000)
    private String content;

    @Column(name = "hit")
    private Integer hit;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}