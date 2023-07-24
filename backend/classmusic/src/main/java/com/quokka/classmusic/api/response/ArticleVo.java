package com.quokka.classmusic.api.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {
    private int articleId;
    private String name;
    private String title;
    private String content;
    private int createdAt;
    private int hit;
}
