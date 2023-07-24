package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.api.response.ReviewVo;
import com.quokka.classmusic.db.entity.Review;

import java.util.List;

public interface ReviewRepository {
    List<ReviewVo> findAll(int teacherId);
    void save(Review review);
    Review findById(int reviewId);
    void delete(Review review);
}
