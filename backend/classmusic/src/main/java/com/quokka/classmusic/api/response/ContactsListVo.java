package com.quokka.classmusic.api.response;

public class ContactsListVo {
    private String name;
    private String memo;
    private String userProfileImage;
    private int order;
    public ContactsListVo(String name, String memo, String userProfileImage, int order) {
        this.name = name;
        this.memo = memo;
        this.userProfileImage = userProfileImage;
        this.order = order;
    }
}
