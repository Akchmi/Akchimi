package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "comment")
public class Comment {
    @Builder
    public Comment(int commentId, Article article, User user, String content, Integer createdAt) {
        this.commentId = commentId;
        this.article = article;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "created_at")
    private Integer createdAt;

    @PrePersist
    private void prePersist(){
        this.createdAt = this.createdAt == null ? (int) Instant.now().getEpochSecond() : 0;
    }

}