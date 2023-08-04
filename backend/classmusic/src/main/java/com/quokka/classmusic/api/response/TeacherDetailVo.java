package com.quokka.classmusic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDetailVo {
    private int userId;
    private String name;
    private Integer gender;
    private String userProfileImage;
    private Integer career;
    private Integer cost;
    private String introduce;
    private Integer startTime;
    private Integer endTime;
    private String classDay;
    private float avgRating;
    private Integer contactCnt;
    private List<String> instruments;

    public void setClassDay(String classDay) {
        this.classDay = classDay;
    }

    public void setInstruments(List<String> instruments) {
        this.instruments = instruments;
    }
}
