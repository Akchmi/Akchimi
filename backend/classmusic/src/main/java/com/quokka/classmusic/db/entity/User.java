package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@ToString
@Table(name="user")
public class User {

    @Builder
    public User(int userId, String id, String password, String name, String email, String userProfileImage, Integer type, Integer createdAt, Integer gender) {
        this.userId = userId;
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.userProfileImage = userProfileImage;
        this.type = type;
        this.createdAt = createdAt;
        this.gender = gender;
    }

    @PrePersist
    public void setDefaultValueBeforePersist(){
        if(this.type == null){
            this.type = 0;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "id", length = 20, unique = true)
    private String id;

    @Column(name = "password", length = 255)
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
    private Integer createdAt;

    @Column(name = "gender")
    private Integer gender;

    @OneToOne(mappedBy = "user")
    private Teacher teacher;
}
