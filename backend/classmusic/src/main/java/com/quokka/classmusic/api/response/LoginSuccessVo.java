package com.quokka.classmusic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessVo {
    private int userId;
    private String name;
    private String id;
    private int type;
    private String accessToken;
    private String refreshToken;
}
