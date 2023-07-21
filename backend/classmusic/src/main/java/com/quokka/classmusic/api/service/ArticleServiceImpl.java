package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.db.entity.Article;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.ArticleRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{

    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository){
        this.articleRepository=articleRepository;
        this.userRepository=userRepository;
    }
    @Override
    public int insertArticle(ArticleDto articleDto) throws Exception {
        Article article = new Article();
        User user=userRepository.getById(1);
        articleDto.setUserId(1);
        log.debug("user :::::::::: {} {}",user.getUserId(),user);
        article.setUser(user);
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setHit(0);
        articleRepository.save(article);
        return article.getArticleId();
    }

    @Override
    public Article select(int articleId) throws Exception {
        Article article= articleRepository.findById(articleId).get();
        article.setHit(article.getHit()+1);
        return article;
    }

    @Override
    public List<Article> selectAll() throws Exception {
        return articleRepository.findAll();
    }

    @Override
    public void modifyArticle(int articleId, ArticleDto articleDto) throws Exception {
//        Article article = em.find(Article.class, articleId);
        Article article=articleRepository.findById(articleId).get();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        articleRepository.save(article);
    }

    @Override
    public void deleteArticle(int articleId) throws Exception {
        articleRepository.deleteById(articleId);
    }
}
