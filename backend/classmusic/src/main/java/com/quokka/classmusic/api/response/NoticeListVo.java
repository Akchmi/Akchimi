package com.quokka.classmusic.api.response;

import com.quokka.classmusic.db.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeListVo {
    private int noticeId;
    private String title;
    private int hit;
    private Integer createAt;

    public NoticeListVo(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.hit = notice.getHit();
        this.createAt = notice.getCreatedAt();
    }
}
