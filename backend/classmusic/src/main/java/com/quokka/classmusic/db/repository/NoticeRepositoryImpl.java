package com.quokka.classmusic.db.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.db.entity.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static com.quokka.classmusic.db.entity.QArticle.article;
import static com.quokka.classmusic.db.entity.QNotice.notice;

@Repository
@Slf4j
public class NoticeRepositoryImpl implements NoticeRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public NoticeRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Notice> findAll(Map<String, String> params) {
        String searchType = params.get("searchType");
        String keyword = params.get("keyword");
        Integer pageNo = Integer.parseInt(params.get("pageNo"));

        log.debug("NoticeRepository findAll params : {}", params);

        // TODO : 페이징 처리
        return queryFactory.select(Projections.constructor(Notice.class,
                notice.noticeId,
                notice.title,
                notice.content,
                notice.hit,
                notice.createdAt))
                .from(notice)
                .where(likeKeywordInSearchType(searchType, keyword))
                .orderBy(notice.noticeId.desc())
                .offset((pageNo-1)*20).limit(20)
                .fetch();
    }

    @Override
    public Notice findNoticeById(int noticeId) {
        return em.find(Notice.class, noticeId);
    }

    @Override
    public void save(Notice notice) {
        em.persist(notice);
    }

    @Override
    public void delete(Notice notice) {
        em.remove(notice);
    }

    @Override
    public int getEndPage(Map<String, String> params) {
        String searchType = params.get("searchType");
        String keyword = params.get("keyword");
        long totalNoticeNum = queryFactory
                .selectFrom(notice)
                .where(likeKeywordInSearchType(searchType, keyword))
                .fetchCount();
        return (int)(totalNoticeNum-1)/20+1;
    }

    private BooleanExpression likeKeywordInSearchType(String searchType, String keyword){
        if(searchType != null && keyword != null){
            if("제목".equals(searchType)){
                return notice.title.like(new StringBuilder().append("%").append(keyword).append("%").toString());
            }else if("내용".equals(searchType)){
                return notice.content.like(new StringBuilder().append("%").append(keyword).append("%").toString());
            }
        }
        return null;
    }

}
