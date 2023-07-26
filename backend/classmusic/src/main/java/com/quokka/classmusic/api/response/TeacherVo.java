package com.quokka.classmusic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherVo {
    private String name;
    private String userProfileImage;
    private int teacherId;
    private int career;
    private String introduce;
    private float avgRating;
    private int contactCnt;
    private List<String> instruments;

    public TeacherVo(String name, String userProfileImage, int teacherId, int career, String introduce, float avgRating, int contactCnt) {
        this.name = name;
        this.userProfileImage = userProfileImage;
        this.teacherId = teacherId;
        this.career = career;
        this.introduce = introduce;
        this.avgRating = avgRating;
        this.contactCnt = contactCnt;
    }

    public void setInstruments(List<String> instruments) {
        this.instruments = instruments;
    }
}
