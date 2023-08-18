package com.quokka.classmusic.api.request;

import com.quokka.classmusic.db.entity.Article;
import com.quokka.classmusic.db.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDto {
    private Article article;
    private User user;
    private String content;
    private Integer createdAt;
}
