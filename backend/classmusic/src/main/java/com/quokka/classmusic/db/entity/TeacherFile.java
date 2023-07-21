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
@Table(name = "teacher_file")
public class TeacherFile {

    @Builder
    public TeacherFile(int teacherFileId, Teacher teacher, String fileUrl) {
        this.teacherFileId = teacherFileId;
        this.teacher = teacher;
        this.fileUrl = fileUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_file_id")
    private int teacherFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private Teacher teacher;

    @Column(name = "file_url", length = 255)
    private String fileUrl;

    // Constructors, getters, and setters
}

