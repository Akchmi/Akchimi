package com.quokka.classmusic.api.response;

import com.quokka.classmusic.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private int userId;
    private String id;
    private String password;
    private String name;
    private String email;
    private String userProfileImage;
    private Integer type;
    private Integer createdAt;
    private Integer gender;

    public UserVo(User user) {
        this.userId = user.getUserId();
        this.id = user.getId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userProfileImage = user.getUserProfileImage();
        this.type = user.getType();
        this.createdAt = user.getCreatedAt();
        this.gender = user.getGender();
    }
}
