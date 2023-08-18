package com.quokka.classmusic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventVo {
    private int eventId;
    private int type;
    private String message;
    private int time;
}
