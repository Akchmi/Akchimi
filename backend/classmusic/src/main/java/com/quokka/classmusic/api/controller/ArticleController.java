package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.request.CommentDto;
import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.api.service.ArticleService;
import com.quokka.classmusic.api.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    public ArticleController(ArticleService articleService, CommentService commentService){
        this.articleService=articleService;
        this.commentService=commentService;
    }
    @PostMapping
    public ResponseEntity insertArticle(@RequestBody ArticleDto articleDto){
        try {
            int articleId=articleService.insertArticle(articleDto);
            log.debug("insertArticle : id {} article insertion",articleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping
    public ResponseEntity findAllArticle(@RequestParam Map<String, String> params){
        try {
            List<ArticleVo> articlesList=articleService.selectAll(params);
            log.debug("findAllArticles : {} articles loading",articlesList.size());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{articleId}")
    public ResponseEntity findArticle(@PathVariable int articleId){
        try {
            ArticleVo article=articleService.select(articleId);
            log.debug("get {} article info : {} {} {}",article.getArticleId(), article.getName(), article.getTitle(), article.getContent());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{articleId}")
    public ResponseEntity modifyArticle(@PathVariable int articleId, @RequestBody ArticleDto articleDto){
        try {
            articleService.modifyArticle(articleId, articleDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteArticle(@PathVariable int articleId){
        try {
            articleService.deleteArticle(articleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/{articleId}/comments")
    public ResponseEntity findAllComments(@PathVariable int articleId){
        try {
            commentService.selectAll(articleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{articleId}/comments")
    public ResponseEntity insertComment(@PathVariable int articleId, @RequestBody CommentDto commentDto){
        try {
            commentService.insertComment(articleId, commentDto);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity modifyComment(@PathVariable int articleId, @PathVariable int commentId, @RequestBody CommentDto commentDto){
        try {
            commentService.modifyComment(commentId, commentDto);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable int articleId, @PathVariable int commentId){
        try {
            commentService.deleteComment(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
