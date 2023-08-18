package com.quokka.classmusic.api.response;

import com.quokka.classmusic.db.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private int commentId;
    private int articleId;
    private int userId;
    private String name;
    private String userProfileImage;
    private String content;
    private int createdAt;

    public CommentVo(Comment comment){
        this.commentId = comment.getCommentId();
        this.articleId = comment.getArticle().getArticleId();
        this.userId = comment.getUser().getUserId();
        this.name = comment.getUser().getName();
        this.userProfileImage = comment.getUser().getUserProfileImage();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
