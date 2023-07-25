package com.quokka.classmusic.api.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ContactsUpdateOrderListDto {
    private int type;
    List<ContactsUpdateOrderDto> contacts;
}
