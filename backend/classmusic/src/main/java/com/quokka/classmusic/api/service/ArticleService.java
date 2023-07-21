package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.request.UserLoginDto;
import com.quokka.classmusic.db.entity.Article;

import java.util.List;

public interface ArticleService {
    public int insertArticle (ArticleDto articleDto) throws Exception;
    public Article select(int articleId) throws Exception;
    public List<Article> selectAll() throws Exception;
    public void modifyArticle(int articleId, ArticleDto articleDto) throws Exception;
    public void deleteArticle(int articleId) throws Exception;
}
