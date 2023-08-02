// import http from "@/common/axios";
// import utils from "@/common/utils";

export default {
  SET_USER(state, data) {    
    state.profiles.userInfo = data
  },
  SET_FAVORITE_TEACHERS(state, data) {
    state.favoriteTeachers = data;
  },
  SET_TEACHER_PROFILE(state, data) {
    state.teacherId = data.teacherId

  }
};
