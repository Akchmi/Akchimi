package com.quokka.classmusic.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class MailDto {
    private String id;
    private String email;
}
