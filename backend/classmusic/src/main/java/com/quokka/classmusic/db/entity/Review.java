package com.quokka.classmusic.db.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {
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
    private LocalDateTime createdAt;

    // Constructors, getters, and setters
}