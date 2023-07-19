package com.quokka.classmusic.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private int teacherId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

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

    // Constructors, getters, and setters
}

