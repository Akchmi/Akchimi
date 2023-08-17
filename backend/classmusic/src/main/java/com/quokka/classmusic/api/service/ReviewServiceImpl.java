package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.ReviewInsertDto;
import com.quokka.classmusic.api.request.ReviewUpdateDto;
import com.quokka.classmusic.api.response.ReviewVo;
import com.quokka.classmusic.common.exception.ErrorCode;
import com.quokka.classmusic.common.exception.RestApiException;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.Event;
import com.quokka.classmusic.db.entity.Review;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.repository.ContactsRepository;
import com.quokka.classmusic.db.repository.EventRepository;
import com.quokka.classmusic.db.repository.ReviewRepository;
import com.quokka.classmusic.db.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final ContactsRepository contactsRepository;
    private final TeacherRepository teacherRepository;
    private final EventRepository eventRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ContactsRepository contactsRepository, TeacherRepository teacherRepository, EventRepository eventRepository) {
        this.reviewRepository = reviewRepository;
        this.contactsRepository = contactsRepository;
        this.teacherRepository = teacherRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<ReviewVo> selectAllReview(int teacherId) {
        return reviewRepository.findAll(teacherId);
    }

    @Override
    @Transactional
    public int insertReview(ReviewInsertDto reviewInsertDto) {
         Contact contact = contactsRepository.findById(reviewInsertDto.getContactId());

         if(contact.getReview() != null){
             throw new RestApiException(ErrorCode.REVIEW_DUPLICATED);
         }
         Review review = Review.builder()
                 .contact(contact)
                 .rating(reviewInsertDto.getRating())
                 .content(reviewInsertDto.getContent())
                 .build();
         reviewRepository.save(review);
         updateRating(contact.getTeacher());
         eventRepository.save(new Event(contact.getTeacher().getUser() , 7,contact.getStudent().getName() + " 학생에게 리뷰가 달렸습니다."));
         return review.getReviewId();
    }

    @Override
    @Transactional
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

    @Override
    public ReviewVo selectReview(int contactId) {
        Review review = reviewRepository.findReviewByContactId(contactId);

        if(review == null){
            return new ReviewVo();
//            throw new RestApiException(ErrorCode.NOT_FOUND);
        }

        return new ReviewVo(review);
    }

    public void updateRating(Teacher teacher) {
        log.debug("teacher : {}", teacher);
        float sum = teacherRepository.findReviewSum(teacher.getTeacherId());
        long cnt = teacherRepository.findReviewCount(teacher.getTeacherId());
        if(cnt != 0){
            teacher.setAvgRating(Math.round(sum / cnt * 10) / 10f);
        } else{
            teacher.setAvgRating(0f);
        }
    }
}
