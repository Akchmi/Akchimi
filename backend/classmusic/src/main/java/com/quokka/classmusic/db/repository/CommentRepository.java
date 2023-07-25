package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.api.response.CommentVo;
import com.quokka.classmusic.db.entity.Article;
import com.quokka.classmusic.db.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentRepository {
    void save(Comment comment);
    List<CommentVo> findAll(int articleId);
    Comment findById(int commentId);
    void delete(Comment comment);
}
