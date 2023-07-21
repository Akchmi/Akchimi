package com.quokka.classmusic.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter @Setter
@DynamicInsert
public class Article {
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

}
