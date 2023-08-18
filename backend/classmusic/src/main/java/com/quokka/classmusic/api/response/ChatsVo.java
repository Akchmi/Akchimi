package com.quokka.classmusic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ChatsVo {
    private String content;
    private int sender;
    private int createdTime;
}
