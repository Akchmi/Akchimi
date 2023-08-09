export default {
  GET_LECTURE_LIST(state, data) {
    state.lectures.lectureList = data;
  },

  GET_REFUSED_LECTURE_LIST(state, data) {
    state.lectures.refusedLectureList = data;
  },

  GET_REVIEW(state, data) {
    console.log(data);
    state.lectures.review = data;
  },
};
