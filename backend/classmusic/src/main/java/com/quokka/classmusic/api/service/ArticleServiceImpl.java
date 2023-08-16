package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.request.ImageDto;
import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.api.response.FileVo;
import com.quokka.classmusic.common.exception.ErrorCode;
import com.quokka.classmusic.common.exception.RestApiException;
import com.quokka.classmusic.common.util.AmazonS3ResourceStorage;
import com.quokka.classmusic.db.entity.*;
import com.quokka.classmusic.db.repository.ArticleRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, AmazonS3ResourceStorage amazonS3ResourceStorage) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.amazonS3ResourceStorage = amazonS3ResourceStorage;
    }

    @Override
    public int insertArticle(int userId, ArticleDto articleDto) {
        User user = userRepository.findById(userId);
        Article article = Article.builder()
                .title(articleDto.getTitle())
                .user(user)
                .content(articleDto.getContent())
                .build();
        articleRepository.save(article);
        return article.getArticleId();
    }

    @Override
    public ArticleVo select(int articleId) {
        ArticleVo articleVo = new ArticleVo(articleRepository.selectOneById(articleId));

        // 없는 글 조회시
        if(articleVo == null){
            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        return articleVo;
    }

    @Override
    public List<ArticleVo> selectAll(Map<String, String> params) {
        List<ArticleVo> articleList = new ArrayList<>();
        for(Article article : articleRepository.findAll(params)){
            log.debug("{} / {} / {} / {} / {} / {}"
                    ,article.getArticleId()
                    ,article.getUser().getName()
                    ,article.getUser().getUserProfileImage()
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
        }else{
            throw new RestApiException(ErrorCode.NOT_AUTHOR);
        }
    }

    @Override
    public void deleteArticle(int articleId, int userId) {
        Article article = articleRepository.findById(articleId);
        if(article.getUser().getUserId() == userId){
//            articleRepository.delete(article);
            // 삭제 시 현재 시간을 deletedAt에 저장
            article.setDeletedAt((int) Instant.now().getEpochSecond());
            articleRepository.save(article);
        }else {
            throw new RestApiException(ErrorCode.NOT_AUTHOR);
        }
    }

    @Override
    public void insertImage(int articleId, List<MultipartFile> multipartFiles) {
        Article article = articleRepository.findById(articleId);

        for (MultipartFile multipartFile : multipartFiles) {
            FileVo fileVo = FileVo.multipartOf(multipartFile);
            amazonS3ResourceStorage.store(fileVo.getPath() , multipartFile);
            articleRepository
                    .saveImage(ArticleFile.builder()
                            .article(article)
                            .fileUrl("https://music-class-bucket.s3.ap-northeast-2.amazonaws.com/" + fileVo.getPath())
                            .build());
        }
    }

    @Override
    public void deleteImage(int articleId, ImageDto imageDto) {
        for (String file : imageDto.getImages()) {
            amazonS3ResourceStorage.deleteFile(file);
            articleRepository.deleteImage(articleId , file);
        }
    }

    @Override
    public int endPageNo(Map<String, String> params) {
        return articleRepository.getEndPage(params);
    }
}
