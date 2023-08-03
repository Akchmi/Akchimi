package com.quokka.classmusic.api.response;

import com.quokka.classmusic.db.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVo {
    private String name;
    private String userProfileImage;
    private int startTime;
    private int endTime;
    private float rating;
    private String content;
    private int createAt;

    public ReviewVo(Review review){
        this.name = review.getContact().getStudent().getName();
        this.userProfileImage = review.getContact().getStudent().getUserProfileImage();
        this.startTime = review.getContact().getStartTime();
        this.endTime = review.getContact().getEndTime();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createAt = review.getCreatedAt();
    }
}
