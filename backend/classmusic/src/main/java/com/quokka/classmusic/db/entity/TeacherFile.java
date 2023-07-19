package com.quokka.classmusic.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "teacher_file")
public class TeacherFile {
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

