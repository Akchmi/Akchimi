package com.quokka.classmusic.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikeInsertDto {
    private int studentId;
    private int teacherId;
}
