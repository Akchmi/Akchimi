// import http from "@/common/axios";
// import utils from "@/common/utils";

export default {
  SET_USER(state, data) {
    state.profiles.userInfo = data;
  },
  SET_FAVORITE_TEACHERS(state, data) {
    state.profiles.favoriteTeachers = data;
  },
  SET_TEACHER_PROFILE(state, data) {
    state.profiles.teacherId = data.teacherId;
  },
  SAVE_TEACHERID(state, data) {
    state.common.teacherId = data; 
  },
};
