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
@Table(name = "article")
public class Article {
    @Builder
    public Article(int articleId, User user, String title, String content, Integer createdAt, Integer hit) {
        this.articleId = articleId;
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.hit = hit;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private int articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content", length = 4000)
    private String content;

    @Column(name = "created_at")
    private Integer createdAt;

    @Column(name = "hit")
    private Integer hit;
    @PrePersist
    private void prePersist(){
        this.hit = this.hit == null ? 0 : this.hit;
        this.createdAt = this.createdAt == null ? (int) Instant.now().getEpochSecond() : 0;
    }

}
