package com.quokka.classmusic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContactsVo {
    private int contactId;
    private String name;
    private int matchingUserId; // contact 상대 userId (강사이면 studentId, 학생이면 teacherId)
    private String userProfileImage;
    private String memo;
    private int order;
}
