package com.quokka.classmusic.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="user") @DynamicInsert
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "name", length = 10)
    private String name;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "user_profile_image", length = 255)
    private String userProfileImage;

    @Column(name = "type")
    private Integer type;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "gender")
    private Integer gender;
}
