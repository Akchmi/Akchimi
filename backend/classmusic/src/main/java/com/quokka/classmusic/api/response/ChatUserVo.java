package com.quokka.classmusic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatUserVo {
    private String studentName;
    private String studentProfileImage;
    private String teacherName;
    private String teacherProfileImage;
}
