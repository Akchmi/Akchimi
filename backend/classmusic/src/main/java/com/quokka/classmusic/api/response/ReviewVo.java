package com.quokka.classmusic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewVo {
    private String name;
    private String userProfileImage;
    private int startTime;
    private int endTime;
    private int rating;
    private String content;
    private int createAt;
}
