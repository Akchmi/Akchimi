package com.quokka.classmusic.db.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quokka.classmusic.db.entity.Instrument;
import com.quokka.classmusic.db.entity.Treat;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.quokka.classmusic.db.entity.QInstrument.instrument;
import static com.quokka.classmusic.db.entity.QTreat.treat;

@Repository
public class TreatRepositoryImpl implements TreatRepository{
    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory query;

    public TreatRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public void save(Treat treat) {
        em.persist(treat);
    }

    @Override
    public Instrument findInstrument(String instrumentName) {
        Instrument ins = query.selectFrom(instrument)
                .where(instrument.instrumentName.contains(instrumentName)).fetchOne();
        return ins;
    }

    @Override
    public List<Treat> findByTeacherId(int teacherId) {
        return query
                .selectFrom(treat)
                .where(treat.teacher.teacherId.eq(teacherId))
                .fetch();
    }

    @Override
    public void delete(Treat treat) {

    }


}
