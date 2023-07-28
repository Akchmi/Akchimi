package com.quokka.classmusic.api.request;

import com.quokka.classmusic.db.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class NoticeDto {
    private String title;
    private String content;

    public Notice toEntity(){
        return Notice.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
