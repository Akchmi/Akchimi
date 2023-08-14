package com.quokka.classmusic.db.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.common.exception.ErrorCode;
import com.quokka.classmusic.common.exception.RestApiException;
import com.quokka.classmusic.db.entity.Article;
import com.quokka.classmusic.db.entity.ArticleFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.quokka.classmusic.db.entity.QArticle.article;
import static com.quokka.classmusic.db.entity.QArticleFile.articleFile;
import static com.quokka.classmusic.db.entity.QTeacher.teacher;
import static com.quokka.classmusic.db.entity.QTeacherFile.teacherFile;
import static com.quokka.classmusic.db.entity.QUser.user;

@Slf4j
@Repository
public class ArticleRepositoryImpl implements  ArticleRepository{
    private final EntityManager em;
    private final JPAQueryFactory query;

    public ArticleRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public void save(Article article) {
        em.persist(article);
    }

    @Override
    public List<Article> findAll(Map<String, String> params) {
        String searchType=params.get("searchType");
        String keyWord=params.get("keyword");
        Integer pageNo=Integer.parseInt(String.valueOf(params.get("pageNo")));
        String sortType=params.get("sortType");
        return query
                .select(Projections.constructor(Article.class,
                        article.articleId,
                        user,
                        article.title,
                        article.content,
                        article.createdAt,
                        article.hit
                ))
                .from(article)
                .join(article.user , user)
                .where(searchKeyWord(searchType, keyWord))
                .limit(20).offset((pageNo-1)*20)
                .orderBy(sortKeyWord(sortType))
                .fetch();
    }

    private OrderSpecifier<?> sortKeyWord(String sortType) {
        if("최신순".equals(sortType)){
            return article.articleId.desc();
        }else if("조회순".equals(sortType)){
            return article.hit.desc();
        }
        return null;
    }

    private BooleanExpression searchKeyWord(String searchType, String keyWord) {
        if(searchType.equals("전체")){
            return article.title.contains(keyWord)
                    .or(article.content.contains(keyWord))
                    .or(article.user.name.contains(keyWord));
        }else if(searchType.equals("제목")){
            return article.title.contains(keyWord);
        }else if(searchType.equals("내용")){
            return article.content.contains(keyWord);
        }else if(searchType.equals("작성자")){
            return  article.user.name.contains(keyWord);
        }
        return null;
    }

    @Override
    public Article selectOneById(int articleId) {
        Article viewArticle = em.find(Article.class, articleId);
        if(viewArticle == null)
            throw new RestApiException(ErrorCode.NOT_FOUND);
        viewArticle.setHit(viewArticle.getHit()+1);
        return query
                .select(Projections.constructor(Article.class,
                        article.articleId,
                        user,
                        article.title,
                        article.content,
                        article.createdAt,
                        article.hit
                ))
                .from(article)
                .join(article.user , user)
                .where(article.articleId.eq(articleId))
                .fetchOne();
    }

    @Override
    public Article findById(int articleId){
        return em.find(Article.class, articleId);
    }

    @Override
    public void delete(Article article) {
        em.remove(article);
    }

    @Override
    public int getEndPage(Map<String, String> params) {
        String searchType = params.get("searchType");
        String keyword = params.get("keyword");
        long totalArticleNum = query
                .selectFrom(article)
                .where(searchKeyWord(searchType, keyword))
                .fetchCount();
        return (int)(totalArticleNum-1)/20+1;
    }

    @Override
    public void deleteImage(int articleId, String file) {
        em.remove(query.selectFrom(articleFile)
                .join(articleFile.article , article)
                .where(article.articleId.eq(articleId).and(articleFile.fileUrl.eq(file)))
                .fetch());
    }

    @Override
    public void saveImage(ArticleFile articleFile) {
        em.persist(articleFile);
    }
}
