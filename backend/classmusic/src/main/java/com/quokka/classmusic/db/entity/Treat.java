package com.quokka.classmusic.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "treat")
public class Treat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_id")
    private int treatmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrument_id", referencedColumnName = "instrument_id")
    private Instrument instrument;

    // Constructors, getters, and setters
}
