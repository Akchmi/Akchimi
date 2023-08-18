package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.db.entity.Article;
import com.quokka.classmusic.db.entity.ArticleFile;
import com.quokka.classmusic.db.entity.TeacherFile;

import java.util.List;
import java.util.Map;


public interface ArticleRepository{
    void save(Article article);
    List<Article> findAll(Map<String, String> params);
    Article findById(int articleId);
    void delete(Article article);
    int getEndPage(Map<String, String> params);
    void deleteImage(int articleId , String file);
    void saveImage(ArticleFile articleFile);
}
