package com.quokka.classmusic.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRegistDto {
    private String id;
    private String password;
    private String name;
    private String email;
    private String userProfileImage;
    private int type;
    private int gender;
}
