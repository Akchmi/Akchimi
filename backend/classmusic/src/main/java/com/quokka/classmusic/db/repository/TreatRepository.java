package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.db.entity.Instrument;
import com.quokka.classmusic.db.entity.Treat;

public interface TreatRepository{
    void save(Treat treat);
    Instrument findInstrument(String instrumentName);
}
