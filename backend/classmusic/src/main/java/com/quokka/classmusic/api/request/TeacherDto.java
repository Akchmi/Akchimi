package com.quokka.classmusic.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class TeacherDto {
    private int userId;
    private int career;
    private int cost;
    private String introduce;
    private int startTime;
    private int endTime;
    private String classDay;
    private List<String> instruments;
}
