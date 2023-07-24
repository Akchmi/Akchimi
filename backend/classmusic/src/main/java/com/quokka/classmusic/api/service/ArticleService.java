package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.response.ArticleVo;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public int insertArticle (ArticleDto articleDto) throws Exception;
    public ArticleVo select(int articleId) throws Exception;
    public List<ArticleVo> selectAll(Map<String, String> params) throws Exception;
    public void modifyArticle(int articleId, ArticleDto articleDto) throws Exception;
    public void deleteArticle(int articleId) throws Exception;
}
