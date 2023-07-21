package com.quokka.classmusic.api.response;

public class ContactsSelectAllVo {
    private String name;
    private String memo;
    private String userProfileImage;
    private int order;

    public ContactsSelectAllVo(String name, String memo, String userProfileImage , int order) {
        this.name = name;
        this.memo = memo;
        this.order = order;
    }
}
