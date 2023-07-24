package com.quokka.classmusic.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupDto {
    private String id;
    private String password;
    private String name;
    private String email;
    private int gender;
}
