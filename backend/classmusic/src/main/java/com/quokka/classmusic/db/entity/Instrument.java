package com.quokka.classmusic.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "instrument")
public class Instrument {
    @Id
    @Column(name = "instrument_id")
    private int instrumentId;

    @Column(name = "instrument_name", length = 20)
    private String instrumentName;

    // Constructors, getters, and setters
}
