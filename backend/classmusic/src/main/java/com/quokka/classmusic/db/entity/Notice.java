package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notice")
@ToString
@DynamicInsert
public class Notice {

    @Builder
    public Notice(int noticeId, String title, String content, Integer hit, Integer createdAt) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.createdAt = createdAt;
    }

    public void noticeUpdate(String title, String content){
        this.title = title;
        this.content = content;
    }

    @PrePersist
    public void setDefaultValueBeforePersist(){
        if(this.hit == null){
            this.hit = 0;
        }
    }

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
    @ColumnDefault("(UNIX_TIMESTAMP())")
    private Integer createdAt;
}