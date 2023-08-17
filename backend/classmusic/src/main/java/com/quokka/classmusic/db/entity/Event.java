package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "event")
public class Event {
    @Builder
    public Event(User user , int type , String message) {
        this.user = user;
        this.type = type;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "message", length = 255)
    private String message;

    @Column(name = "time")
    private Integer time;

    @Column(name = "type")
    private int type;

    @PrePersist
    private void prePersist(){
        this.time = this.time == null ? (int) Instant.now().getEpochSecond() : 0;
    }
}
