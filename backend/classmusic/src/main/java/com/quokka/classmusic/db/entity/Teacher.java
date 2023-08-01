package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "teacher")
public class Teacher {
    @Builder
    public Teacher(int teacherId, User user, Integer career, Integer cost, String introduce, Integer startTime, Integer endTime, Integer classDay, Float avgRating, Integer contactCnt) {
        this.teacherId = teacherId;
        this.user = user;
        this.career = career;
        this.cost = cost;
        this.introduce = introduce;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classDay = classDay;
        this.avgRating = avgRating;
        this.contactCnt = contactCnt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private int teacherId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "treatment_id")
    private List<Treat> treats = new ArrayList<>();

    @Column(name = "career")
    private Integer career;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "introduce", length = 4000)
    private String introduce;

    @Column(name = "start_time")
    private Integer startTime;

    @Column(name = "end_time")
    private Integer endTime;

    @Column(name = "class_day")
    private Integer classDay;

    @Column(name = "avg_rating")
    private Float avgRating;

    @Column(name = "contact_cnt")
    private Integer contactCnt;

    @PrePersist
    private void prePersist(){
        this.avgRating = this.avgRating == null ? 0.0f : this.avgRating;
        this.contactCnt = this.contactCnt == null ? 0 : this.contactCnt;
    }



    // Constructors, getters, and setters
}

