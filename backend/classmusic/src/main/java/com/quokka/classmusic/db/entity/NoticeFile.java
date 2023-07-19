package com.quokka.classmusic.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "notice_file")
public class NoticeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_file_id")
    private int noticeFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", referencedColumnName = "notice_id")
    private Notice notice;

    @Column(name = "file_url", length = 255)
    private String fileUrl;

    // Constructors, getters, and setters
}