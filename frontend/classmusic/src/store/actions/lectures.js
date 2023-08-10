import {
  apiGetLectureList,
  apiGetRefusedLectureList,
  apiPutUpdateMemo,
  apiDeleteContact,
  apiGetReview,
  apiPostReview,
  apiPutReviewUpdate,
  apiDeleteReview,
  apiChangeLectureState,
} from "@/api/lectures";

export default {
  // 필요 기능
  //매칭리스트 조회-학생(진행중)
  getLectureList(context, data) {
    apiGetLectureList(context, data);
  },
  //매칭리스트 조회-학생(거절)
  getRefusedLectureList(context, data) {
    apiGetRefusedLectureList(context, data);
  },
  //매칭 메모 수정
  putUpdateMemo(context, data) {
    apiPutUpdateMemo(context, data);
  },
  //매칭 삭제
  deleteContact(context, contactId) {
    apiDeleteContact(context, contactId);
  },
  //매칭 상태 수정
  changeLectureState(context, data) {
    apiChangeLectureState(context, data);
  },
  //매칭 순서 수정
  //매칭 생성
  //강의실 입장

  //리뷰 조회
  getReview(context, params) {
    apiGetReview(context, params);
  },

  //리뷰 작성
  postReview(context, params) {
    apiPostReview(context, params);
  },

  //리뷰 수정
  putReviewUpdate(context, params) {
    apiPutReviewUpdate(context, params);
  },

  //리뷰 삭제
  deleteReview(context, reviewId) {
    apiDeleteReview(context, reviewId);
  },
};
