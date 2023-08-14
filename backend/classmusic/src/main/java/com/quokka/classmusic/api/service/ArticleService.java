package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.request.ImageDto;
import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.db.entity.Article;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public int insertArticle (int userId, ArticleDto articleDto);
    public ArticleVo select(int articleId);
    public List<ArticleVo> selectAll(Map<String, String> params);
    public void modifyArticle(int articleId, ArticleDto articleDto, int userId);
    public void deleteArticle(int articleId, int userId);
    void insertImage(int articleId , List<MultipartFile> multipartFiles);
    void deleteImage(int articleId , ImageDto imageDto);
    public int endPageNo(Map<String, String> params);
}
