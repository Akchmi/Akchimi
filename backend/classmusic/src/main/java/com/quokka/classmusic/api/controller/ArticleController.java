package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.service.ArticleService;
import com.quokka.classmusic.db.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@CrossOrigin("*")
@Slf4j
public class ArticleController {
    private ArticleService articleService;
    @Autowired
    public ArticleController(ArticleService articleService){
        this.articleService=articleService;
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
    public ResponseEntity findAllArticle(Pageable pageable, String searchType, String keyWord, int pageNo, int sortType){
        try {
            List<Article> articlesList=articleService.selectAll();
            log.debug("findAllArticles : {} articles loading",articlesList.size());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{articleId}")
    public ResponseEntity findArticle(@PathVariable int articleId){
        try {
            Article article=articleService.select(articleId);
            log.debug("get {} article info : {} {} {}",article.getArticleId(), article.getUser(), article.getTitle(), article.getContent());
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
}
