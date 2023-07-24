package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ReviewInsertDto;
import com.quokka.classmusic.api.request.ReviewUpdateDto;
import com.quokka.classmusic.api.response.ReviewVo;
import com.quokka.classmusic.api.service.ReviewServiceImpl;
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
    private ReviewServiceImpl reviewService;
    @Autowired
    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewVo>> selectAllReviews(@RequestParam int teacherId){
        System.out.println(teacherId);
        log.debug("teacherId = {} 리뷰 목록 조회" ,teacherId);
        try {
            return ResponseEntity.status(200).body(reviewService.selectAllReview(teacherId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<Integer> insertReview(@RequestBody ReviewInsertDto reviewInsertDto){
        log.debug("리뷰쓰기");
        try {
            return ResponseEntity.status(200).body(reviewService.insertReview(reviewInsertDto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable int reviewId ,@RequestBody ReviewUpdateDto reviewUpdateDto){
        log.debug("리뷰수정");
        try {
            reviewService.updateReview(reviewId , reviewUpdateDto);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
