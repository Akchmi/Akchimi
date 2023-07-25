package com.quokka.classmusic.api.request;

import lombok.Getter;

@Getter
public class TeacherSelectDto {
    private String keyword;
    private int startCareer;
    private int endCareer;
    private int gender;
    private String classDay;
    private int startTime;
    private int endTime;
    private int startCost;
    private int endCost;
    private int orderBy;
    private String instrument;
    private int page;
}
