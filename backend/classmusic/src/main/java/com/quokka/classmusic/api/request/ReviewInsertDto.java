package com.quokka.classmusic.api.request;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ReviewInsertDto {
    private int contactId;
    private float rating;
    private String content;
}
