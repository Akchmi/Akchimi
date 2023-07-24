package com.quokka.classmusic.db.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.request.ArticleDto;
import com.quokka.classmusic.api.response.ArticleVo;
import com.quokka.classmusic.db.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static com.quokka.classmusic.db.entity.QArticle.article;
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
    public List<ArticleVo> findAll(Map<String, String> params) {
        String searchType=params.get("searchType");
        String keyWord=params.get("keyWord");
        Integer pageNo=Integer.parseInt(String.valueOf(params.get("pageNo")));
        String sortType=params.get("sortType");
        log.debug("{} {} {} {}",searchType,keyWord,pageNo,sortType);
        return query
                .select(Projections.constructor(ArticleVo.class,
                        article.articleId,
                        user.name,
                        article.title,
                        article.content,
                        article.createdAt,
                        article.hit
                ))
                .from(article)
                .join(article.user , user)
                .where(searchKeyWord(searchType, keyWord))
                .orderBy(sortKeyWord(sortType))
                .fetch();
    }

    private OrderSpecifier<?> sortKeyWord(String sortType) {
        if(sortType.equals("default")){
            return article.articleId.desc();
        }else if(sortType.equals("hits")){
            return article.hit.desc();
        }
        return null;
    }

    private BooleanExpression searchKeyWord(String searchType, String keyWord) {
        if(searchType.equals("default")){
            return article.title.contains(keyWord)
                    .or(article.content.contains(keyWord))
                    .or(article.user.name.contains(keyWord));
        }else if(searchType.equals("title")){
            return article.title.contains(keyWord);
        }else if(searchType.equals("content")){
            return article.content.contains(keyWord);
        }else if(searchType.equals("name")){
            return  article.user.name.contains(keyWord);
        }
        return null;
    }

    @Override
    public ArticleVo findById(int articleId) {
        return query
                .select(Projections.constructor(ArticleVo.class,
                        article.articleId,
                        user.name,
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
    public void delete(Article article) {
        em.remove(article);
    }
}
