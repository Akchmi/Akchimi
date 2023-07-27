package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.request.CommentDto;
import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.api.response.CommentVo;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.api.service.ArticleService;
import com.quokka.classmusic.api.service.CommentService;
import com.quokka.classmusic.api.service.UserService;
import com.quokka.classmusic.db.entity.Comment;
import com.quokka.classmusic.db.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles")
@CrossOrigin("*")
@Slf4j
public class ArticleController {
    private ArticleService articleService;
    private CommentService commentService;
    private UserService userService;
    @Autowired
    public ArticleController(ArticleService articleService, CommentService commentService, UserService userService){
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
    }

    // 자유게시판 글쓰기
    @PostMapping
    public ResponseEntity<Integer> insertArticle(@RequestBody ArticleDto articleDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        int userId = userDetailsVo.getUserVo().getUserId();
        articleDto.setName(userService.findUserByUserId(userId).getName());
        try {
            int articleId = articleService.insertArticle(articleDto);
            log.debug("insertArticle : id {} article insertion", articleId);
            return new ResponseEntity<>(articleId,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 자유게시판 전체글 가져오기
    @GetMapping
    public ResponseEntity<List<ArticleVo>> findAllArticle(@RequestParam Map<String, String> params){
        try {
            List<ArticleVo> articlesList = articleService.selectAll(params);
            log.debug("findAllArticles : {} articles loading",articlesList.size());
            return new ResponseEntity<>(articlesList, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 자유게시판 글 상세보기
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleVo> findArticle(@PathVariable int articleId){
        try {
            ArticleVo article = articleService.select(articleId);
            log.debug("get {} article info : {} {} {}",article.getArticleId(), article.getName(), article.getTitle(), article.getContent());
            return new ResponseEntity<>(article, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 자유게시판 글 수정
    @PutMapping("/{articleId}")
    public ResponseEntity<Integer> modifyArticle(@PathVariable int articleId, @RequestBody ArticleDto articleDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        int userId = userDetailsVo.getUserVo().getUserId();
        try {
            if(userId == userService.findUserById(articleService.select(articleId).getName()).getUserId()) {
                articleService.modifyArticle(articleId, articleDto);
                return new ResponseEntity<>(articleId, HttpStatus.ACCEPTED);
            }else{
                log.debug("작성자가 아닙니다.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 자유게시판 글 삭제
    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteArticle(@PathVariable int articleId, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        int userId = userDetailsVo.getUserVo().getUserId();
        try {
            if(userId == userService.findUserById(articleService.select(articleId).getName()).getUserId()) {
                articleService.deleteArticle(articleId);
                return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
            }
            else{
                log.debug("작성자가 아닙니다.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 자유게시판 게시글에서 댓글 가져오기
    @GetMapping("/{articleId}/comments")
    public ResponseEntity<List<CommentVo>> findAllComments(@PathVariable int articleId){
        try {
            List<CommentVo> commentVoList = commentService.selectAll(articleId);
            return new ResponseEntity<>(commentVoList, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 댓글 등록
    @PostMapping("/{articleId}/comments")
    public ResponseEntity<Integer> insertComment(@PathVariable int articleId, @RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        int userId = userDetailsVo.getUserVo().getUserId();
        try {
            int commentId = commentService.insertComment(articleId, userId, commentDto);
            return new ResponseEntity(commentId, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 댓글 수정
    @PutMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<Integer> modifyComment(@PathVariable int articleId, @PathVariable int commentId, @RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        int userId = userDetailsVo.getUserVo().getUserId();
        try {
            Comment comment = commentService.select(commentId);
            if(userId == commentService.select(commentId).getUser().getUserId()){
                commentService.modifyComment(commentId, commentDto);
                return new ResponseEntity<>(commentId, HttpStatus.ACCEPTED);
            }else{
                log.debug("작성자가 아닙니다.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable int articleId, @PathVariable int commentId, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        int userId = userDetailsVo.getUserVo().getUserId();
        try {
            if(userId == commentService.select(commentId).getUser().getUserId()){
                commentService.deleteComment(commentId);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }else{
                log.debug("작성자가 아닙니다.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
