package com.quokka.classmusic.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "article_file")
public class ArticleFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_file_id")
    private int articleFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private Article article;

    @Column(name = "file_url", length = 255)
    private String fileUrl;

    // Constructors, getters, and setters
}

