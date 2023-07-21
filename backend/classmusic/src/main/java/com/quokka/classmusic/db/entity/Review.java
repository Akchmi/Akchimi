package com.quokka.classmusic.db.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "review")
public class Review {
    @Builder
    public Review(int reviewId, Contact contact, Float rating, String content, Integer createdAt) {
        this.reviewId = reviewId;
        this.contact = contact;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id")
    private Contact contact;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "content", length = 200)
    private String content;

    @Column(name = "created_at")
    private Integer createdAt;

    // Constructors, getters, and setters
}