package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.db.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentRepository {
    void save(Comment comment);
    List<Comment> findAll(int articleId);
    Comment findById(int commentId);
    void delete(Comment comment);
}
