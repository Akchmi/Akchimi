package com.quokka.classmusic.api.response;


import com.quokka.classmusic.db.entity.Article;
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

    public ArticleVo(Article article){
        this.articleId = article.getArticleId();
        this.name = article.getUser().getName();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
        this.hit = article.getHit();
    }
}
