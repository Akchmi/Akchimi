package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.CommentDto;
import com.quokka.classmusic.api.response.CommentVo;
import com.quokka.classmusic.db.entity.Article;
import com.quokka.classmusic.db.entity.Comment;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.ArticleRepository;
import com.quokka.classmusic.db.repository.CommentRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<CommentVo> selectAll(int articleId) throws Exception {
        for(CommentVo cv:commentRepository.findAll(articleId)){
            log.debug("commentId : {}, content : {}", cv.getCommentId(), cv.getContent());
        }
        return commentRepository.findAll(articleId);
    }

    @Override
    public int insertComment(int articleId, int userId, CommentDto commentDto) throws Exception {
        User user=userRepository.findById(userId);
        Comment comment=Comment.builder()
                .article(articleRepository.findById(articleId))
                .user(user)
                .content(commentDto.getContent())
                .build();
        Article article=articleRepository.findById(articleId);
        log.debug("articleT : {}, comment : {}",article.getTitle(), comment.getContent());
        commentRepository.save(comment);
        return comment.getCommentId();
    }

    @Override
    public void deleteComment(int commentId) throws Exception {
        Comment comment = commentRepository.findById(commentId);
        commentRepository.delete(comment);
        log.debug("deleted comment - commentId{} content: {}",commentId,comment.getContent());
    }

    @Override
    public void modifyComment(int commentId, CommentDto commentDto) throws Exception {
        Comment comment = commentRepository.findById(commentId);
        comment.setContent(commentDto.getContent());
        commentRepository.save(comment);
        log.debug("modified comment - commentId{} content: {}",commentId, comment.getContent());
    }

    @Override
    public Comment select(int commentId) throws Exception {
        return commentRepository.findById(commentId);
    }
}
