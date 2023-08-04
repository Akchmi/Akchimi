package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ReviewInsertDto;
import com.quokka.classmusic.api.request.ReviewUpdateDto;
import com.quokka.classmusic.api.response.ReviewVo;
import com.quokka.classmusic.api.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin("*")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewVo>> selectAllReviews(@RequestParam int teacherId){
        log.debug("teacherId = {} 리뷰 목록 조회" ,teacherId);

        return new ResponseEntity<>(reviewService.selectAllReview(teacherId), HttpStatus.OK);
    }

    @GetMapping("/myreview")
    public ResponseEntity<ReviewVo> selectReviews(@RequestParam int contactId){
        ReviewVo reviewVo = reviewService.selectReview(contactId);

        log.debug("{}",reviewVo.getContent());
        return new ResponseEntity<>(reviewVo, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> insertReview(@RequestBody ReviewInsertDto reviewInsertDto){
        log.debug("리뷰쓰기");

        return new ResponseEntity<>(reviewService.insertReview(reviewInsertDto), HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable int reviewId ,@RequestBody ReviewUpdateDto reviewUpdateDto){
        log.debug("리뷰수정");

        reviewService.updateReview(reviewId , reviewUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable int reviewId){
        log.debug("리뷰삭제");

        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
