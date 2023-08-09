export default {
  getlectureList(state) {
    return state.lectures.lectureList;
  },
  getRefusedLectureList(state) {
    return state.lectures.refusedLectureList;
  },
  getReview(state) {
    return state.lectures.review;
  },
};
