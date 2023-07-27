package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.db.entity.Article;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.ArticleRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    public int insertArticle(ArticleDto articleDto) {
        Article article = Article.builder()
                .title(articleDto.getTitle())
                .user(new User(1,"1","2","1","2","3",1,2,1))
                .content(articleDto.getContent())
                .build();
        articleRepository.save(article);
        return article.getArticleId();
    }

    @Override
    public ArticleVo select(int articleId) {
        return articleRepository.selectOneById(articleId);
    }

    @Override
    public List<ArticleVo> selectAll(Map<String, String> params) {
        return articleRepository.findAll(params);
    }

    @Override
    public void modifyArticle(int articleId, ArticleDto articleDto) {
        Article article=articleRepository.findById(articleId);
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        articleRepository.save(article);
    }

    @Override
    public void deleteArticle(int articleId) {
        Article article = articleRepository.findById(articleId);
        articleRepository.delete(article);
    }
}
