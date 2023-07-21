package com.quokka.classmusic.api.request;

import com.quokka.classmusic.db.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeDto {
    private String title;
    private String content;
    private int hit;

    public Notice toEntity(){
        return Notice.builder()
                .title(this.title)
                .content(this.content)
                .hit(this.hit)
                .build();
    }
}
