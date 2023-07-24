package com.quokka.classmusic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVo {
    private String name;
    private String userProfileImage;
    private int startTime;
    private int endTime;
    private float rating;
    private String content;
    private int createAt;
}
