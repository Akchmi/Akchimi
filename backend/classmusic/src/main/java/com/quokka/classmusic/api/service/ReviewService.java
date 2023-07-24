package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ReviewInsertDto;
import com.quokka.classmusic.api.request.ReviewUpdateDto;
import com.quokka.classmusic.api.response.ReviewVo;

import java.util.List;

public interface ReviewService {
    List<ReviewVo> selectAllReview(int teacherId);
    int insertReview(ReviewInsertDto reviewInsertDto);
    void updateReview(int reviewId , ReviewUpdateDto reviewUpdateDto);
    void deleteReview(int reviewId);
}
