package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "contact")
public class Contact {

    @Builder
    public Contact(int contactId, User student, Teacher teacher, Integer state, String studentMemo, String teacherMemo, Integer studentOrder, Integer teacherOrder, Integer startTime, Integer endTime, String roomKey) {
        this.contactId = contactId;
        this.student = student;
        this.teacher = teacher;
        this.state = state;
        this.studentMemo = studentMemo;
        this.teacherMemo = teacherMemo;
        this.studentOrder = studentOrder;
        this.teacherOrder = teacherOrder;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomKey = roomKey;
    }

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

    @OneToOne(mappedBy = "contact")
    private Review review;

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
}