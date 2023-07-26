package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.db.entity.Instrument;
import com.quokka.classmusic.db.entity.Treat;

import java.util.List;

public interface TreatRepository{
    void save(Treat treat);
    Instrument findInstrument(String instrumentName);

    List<Treat> findByTeacherId(int teacherId);
    void delete(Treat treat);
}
