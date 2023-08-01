package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.db.entity.Article;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.ArticleRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public int insertArticle(int userId, ArticleDto articleDto) {
        User user = userRepository.findById(userId);
        Article article = Article.builder()
                .title(articleDto.getTitle())
                .user(user)
                .content(articleDto.getContent())
//                .hit(0)
                .build();
        articleRepository.save(article);
        return article.getArticleId();
    }

    @Override
    public ArticleVo select(int articleId) {
        ArticleVo articleVo = new ArticleVo(articleRepository.selectOneById(articleId));
        return articleVo;
    }

    @Override
    public List<ArticleVo> selectAll(Map<String, String> params) {
        List<ArticleVo> articleList = new ArrayList<>();
        for(Article article : articleRepository.findAll(params)){
            log.debug("{} / {} / {} / {} / {} / {}"
                    ,article.getArticleId()
                    ,article.getUser().getName()
                    ,article.getTitle()
                    ,article.getContent(),
                    article.getCreatedAt(),
                    article.getHit());
            articleList.add(new ArticleVo(article));
        }

        return articleList;
    }

    @Override
    public void modifyArticle(int articleId, ArticleDto articleDto, int userId) {
        Article article=articleRepository.findById(articleId);
        if(article.getUser().getUserId() == userId){
            article.setTitle(articleDto.getTitle());
            article.setContent(articleDto.getContent());
            articleRepository.save(article);
        }else{
            log.debug("작성자가 아닙니다.");
        }
    }

    @Override
    public void deleteArticle(int articleId, int userId) {
        Article article = articleRepository.findById(articleId);
        if(article.getUser().getUserId() == userId){
            articleRepository.delete(article);
        }else {
            log.debug("작성자가 아닙니다.");
        }
    }

    @Override
    public int endPageNo(Map<String, String> params) {
        return articleRepository.getEndPage(params);
    }
}
