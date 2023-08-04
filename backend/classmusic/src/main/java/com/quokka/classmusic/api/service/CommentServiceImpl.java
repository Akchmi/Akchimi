package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.CommentDto;
import com.quokka.classmusic.api.response.CommentVo;
import com.quokka.classmusic.common.exception.ErrorCode;
import com.quokka.classmusic.common.exception.RestApiException;
import com.quokka.classmusic.db.entity.Comment;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.ArticleRepository;
import com.quokka.classmusic.db.repository.CommentRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private ArticleRepository articleRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<CommentVo> selectAll(int articleId) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for(Comment comment:commentRepository.findAll(articleId)){
            commentVoList.add(new CommentVo(comment));
        }
        return commentVoList;
    }

    @Override
    public int insertComment(int articleId, int userId, CommentDto commentDto){
        User user=userRepository.findById(userId);
        Comment comment=Comment.builder()
                .article(articleRepository.findById(articleId))
                .user(user)
                .content(commentDto.getContent())
                .build();
        commentRepository.save(comment);
        return comment.getCommentId();
    }

    @Override
    public void deleteComment(int commentId, int userId) {
        Comment comment = commentRepository.findById(commentId);
        if(comment.getUser().getUserId() == userId){
            commentRepository.delete(comment);
        }else{
            throw new RestApiException(ErrorCode.NOT_AUTHOR);
        }
    }

    @Override
    public void modifyComment(int commentId, CommentDto commentDto, int userId) {
        Comment comment = commentRepository.findById(commentId);
        if(comment.getUser().getUserId() == userId){
            comment.setContent(commentDto.getContent());
            commentRepository.save(comment);
        }else{
            throw new RestApiException(ErrorCode.NOT_AUTHOR);
        }
    }

    @Override
    public CommentVo select(int commentId) {
        CommentVo commentVo = new CommentVo(commentRepository.findById(commentId));
        return commentVo;
    }
}
