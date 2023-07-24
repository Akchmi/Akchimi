package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.db.entity.Article;

import java.util.List;
import java.util.Map;


public interface ArticleRepository{
    void save(Article article);
    List<ArticleVo> findAll(Map<String, String> params);
    ArticleVo selectOneById(int articleId);
    Article findById(int articleId);
    void delete(Article article);
}
