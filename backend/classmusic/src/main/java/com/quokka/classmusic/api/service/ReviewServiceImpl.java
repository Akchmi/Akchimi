package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ReviewInsertDto;
import com.quokka.classmusic.api.request.ReviewUpdateDto;
import com.quokka.classmusic.api.response.ReviewVo;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.Review;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.repository.ContactsRepository;
import com.quokka.classmusic.db.repository.ReviewRepository;
import com.quokka.classmusic.db.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{
    private ReviewRepository reviewRepository;
    private ContactsRepository contactsRepository;
    private TeacherRepository teacherRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ContactsRepository contactsRepository, TeacherRepository teacherRepository) {
        this.reviewRepository = reviewRepository;
        this.contactsRepository = contactsRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<ReviewVo> selectAllReview(int teacherId) {
        return reviewRepository.findAll(teacherId);
    }

    @Override
    public int insertReview(ReviewInsertDto reviewInsertDto) {
         Contact contact = contactsRepository.findById(reviewInsertDto.getContactId());
         Review review = Review.builder()
                 .contact(contact)
                 .rating(reviewInsertDto.getRating())
                 .content(reviewInsertDto.getContent())
                 .build();
         reviewRepository.save(review);
         updateRating(review.getContact().getTeacher());

         return review.getReviewId();
    }

    @Override
    public void updateReview(int reviewId , ReviewUpdateDto reviewUpdateDto) {
        Review review = reviewRepository.findById(reviewId);
        review.setContent(reviewUpdateDto.getContent());
        review.setRating(reviewUpdateDto.getRating());
        reviewRepository.save(review);
        updateRating(reviewRepository.findById(reviewId).getContact().getTeacher());
    }
    @Override
    public void deleteReview(int reviewId) {
        Teacher teacher = reviewRepository.findById(reviewId).getContact().getTeacher();
        reviewRepository.delete(reviewRepository.findById(reviewId));
        updateRating(teacher);
    }

    public void updateRating(Teacher teacher) {
        System.out.println(teacher);
        float sum = teacherRepository.findReviewSum(teacher.getTeacherId());
        long cnt = teacherRepository.findReviewCount(teacher.getTeacherId());
        if(cnt != 0){
            teacher.setAvgRating(Math.round(sum / cnt * 10) / 10f);
        } else{
            teacher.setAvgRating(0f);
        }
    }
}
