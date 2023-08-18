package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.CommentDto;
import com.quokka.classmusic.api.response.CommentVo;

import java.util.List;

public interface CommentService {
    public List<CommentVo> selectAll(int articleId);
    public int insertComment(int articleId, int userId, CommentDto commentDto);
    public void deleteComment(int commentId, int userId);
    public void modifyComment(int commentId, CommentDto commentDto, int userId);
    public CommentVo select(int commentId);
}
