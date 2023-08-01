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
    private String userProfileImage;
    private String memo;
    private int order;
}
