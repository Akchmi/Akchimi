// import http from "@/common/axios";
// import utils from "@/common/utils";

export default {
  SET_USER(state, data) {
    state.id = data.id;
    state.name = data.name;
    state.email = data.email;
    state.userProfileImage = data.userProfileImage;
    state.type = data.type;
    state.createdAt = data.createdAt;
    state.gender = data.gender;
  },
  SET_FAVORITE_TEACHERS(state, data) {
    state.favoriteTeachers = data;
  },
  SET_TEACHER_PROFILE(state, data) {
    state.teacherId = data.teacherId

  }
};
