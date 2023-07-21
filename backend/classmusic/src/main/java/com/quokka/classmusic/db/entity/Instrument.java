package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "instrument")
public class Instrument {
    @Builder
    public Instrument(int instrumentId, String instrumentName) {
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
    }

    @Id
    @Column(name = "instrument_id")
    private int instrumentId;

    @Column(name = "instrument_name", length = 20)
    private String instrumentName;

    // Constructors, getters, and setters
}
