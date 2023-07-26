package com.quokka.classmusic.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeacherSelectDto {
    private String keyword;
    private Integer startCareer;
    private Integer endCareer;
    private Integer gender;
    private String classDay;
    private Integer startTime;
    private Integer endTime;
    private Integer startCost;
    private Integer endCost;
    private String orderBy;
    private String instrument;
    private Integer page;

    @Override
    public String toString() {
        return "TeacherSelectDto{" +
                "keyword='" + keyword + '\'' +
                ", startCareer=" + startCareer +
                ", endCareer=" + endCareer +
                ", gender=" + gender +
                ", classDay='" + classDay + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", startCost=" + startCost +
                ", endCost=" + endCost +
                ", orderBy=" + orderBy +
                ", instrument='" + instrument + '\'' +
                ", page=" + page +
                '}';
    }
}
