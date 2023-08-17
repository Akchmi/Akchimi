export default {
  GET_LECTURE_LIST(state, data) {
    state.lectures.lectureList = data;
  },

  GET_REFUSED_LECTURE_LIST(state, data) {
    state.lectures.refusedLectureList = data;
  },

  GET_REVIEW(state, data) {
    state.lectures.review = data;
  },

  UPDATEMEMO(state, data) {
    for (let i = 0; i < state.lectures.lectureList.length; i++) {
      if (state.lectures.lectureList[i].contactId == data.contactId) {
        state.lectures.lectureList[i].memo = data.memo;
      }
    }
  },
  UPDATEREVIEW(state, data) {
    state.lectures.review.content = data.content;
    state.lectures.review.rating = data.rating;
  },
};
