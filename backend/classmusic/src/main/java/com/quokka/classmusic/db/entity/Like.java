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
@Table(name = "favorite")
public class Like {
    @Builder
    public Like(int favoriteId, User student, Teacher teacher) {
        this.favoriteId = favoriteId;
        this.student = student;
        this.teacher = teacher;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private int favoriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private Teacher teacher;

    // Constructors, getters, and setters
}
