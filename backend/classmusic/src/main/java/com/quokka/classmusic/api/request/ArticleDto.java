package com.quokka.classmusic.api.request;

import com.quokka.classmusic.db.entity.Article;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleDto {
    private String name;
    private String title;
    private String content;
    private int createdAt;
    private int hit;
}
