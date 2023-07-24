package com.quokka.classmusic.api.request;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ReviewUpdateDto {
    private float rating;
    private String content;
}
