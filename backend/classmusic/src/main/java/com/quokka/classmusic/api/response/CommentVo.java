package com.quokka.classmusic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private int commentId;
    private int articleId;
    private String name;
    private String content;
    private int createdAt;
}
