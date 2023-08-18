package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "notice_file")
public class NoticeFile {
    @Builder
    public NoticeFile(int noticeFileId, Notice notice, String fileUrl) {
        this.noticeFileId = noticeFileId;
        this.notice = notice;
        this.fileUrl = fileUrl;
    }

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