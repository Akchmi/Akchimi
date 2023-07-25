package com.quokka.classmusic.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.api.response.CommentVo;
import com.quokka.classmusic.db.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.quokka.classmusic.db.entity.QArticle.article;
import static com.quokka.classmusic.db.entity.QComment.comment;
import static com.quokka.classmusic.db.entity.QUser.user;

@Slf4j
@Repository
public class CommentRepositoryImpl implements CommentRepository{
    private EntityManager em;
    private JPAQueryFactory query;

    public CommentRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public void save(Comment comment) {
        em.persist(comment);
    }

    @Override
    public List<CommentVo> findAll(int articleId) {
        log.debug("articleId : {}",articleId);
        return query
                .select(Projections.constructor(CommentVo.class,
                    comment.commentId,
                    article.articleId,
                    user.name,
                    comment.content,
                    comment.createdAt
                ))
                .from(comment)
                .join(comment.user, user)
                .join(comment.article, article)
                .where(article.articleId.eq(articleId))
                .fetch();
    }

    @Override
    public Comment findById(int commentId) {
        return em.find(Comment.class, commentId);
    }

    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }
}
