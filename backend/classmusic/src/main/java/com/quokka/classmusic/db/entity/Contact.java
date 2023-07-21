package com.quokka.classmusic.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private int contactId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private Teacher teacher;

    @Column(name = "state")
    private Integer state;

    @Column(name = "student_memo", length = 255)
    private String studentMemo;

    @Column(name = "teacher_memo", length = 255)
    private String teacherMemo;

    @Column(name = "student_order")
    private Integer studentOrder;

    @Column(name = "teacher_order")
    private Integer teacherOrder;

    @Column(name = "start_time")
    private Integer startTime;

    @Column(name = "end_time")
    private Integer endTime;

    @Column(name = "room_key", length = 255)
    private String roomKey;

    // Constructors, getters, and setters
}