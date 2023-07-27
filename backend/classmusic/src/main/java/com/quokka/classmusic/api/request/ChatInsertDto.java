package com.quokka.classmusic.api.request;

import lombok.Getter;

@Getter
public class ChatInsertDto {
    private String content;
    private int sender;
}
