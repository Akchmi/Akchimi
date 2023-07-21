package com.quokka.classmusic.api.response;

import com.quokka.classmusic.db.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeDetailVo {
    private int noticeId;
    private String title;
    private String content;
    private int hit;
    private Integer createAt;

    public NoticeDetailVo(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.hit = notice.getHit();
        this.createAt = notice.getCreatedAt();
    }
}
