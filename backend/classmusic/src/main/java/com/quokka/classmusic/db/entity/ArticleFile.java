package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "article_file")
public class ArticleFile {
    @Builder
    public ArticleFile(int articleFileId, Article article, String fileUrl) {
        this.articleFileId = articleFileId;
        this.article = article;
        this.fileUrl = fileUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_file_id")
    private int articleFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private Article article;

    @Column(name = "file_url", length = 255)
    private String fileUrl;

}

