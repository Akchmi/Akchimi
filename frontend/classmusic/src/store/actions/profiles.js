import {
  apiTeacherProfileCreate,
  apiTeacherProfileUpdate,
  apiLikeTeacherUpdate,
  apiChangePw,
  apiDeleteAttachedImage,
  apiDeleteMyprofileImage,
  apiDeleteTeacher,
} from "@/api/profiles.js";

export default {
  async postTeacherProfileCreate(context, data) {
    const response = await apiTeacherProfileCreate(context, data);
    if (response) {
      context.commit("SAVE_TEACHERID", response);
      context.commit("SET_USER_TYPE", 1);
    }
    return response;
  },
  async updateUserType({ commit }, userType) {
    commit("SET_USER_TYPE", userType);
  },

  putTeacherProfileUpdate(context, data) {
    // console.log("액션", context, data);
    return apiTeacherProfileUpdate(context, data);
  },

  postLikeTeacherUpdate(context, data) {
    apiLikeTeacherUpdate(context, data);
  },
  putChangePw(context, data) {
    // console.log("비번변경", context, data);
    apiChangePw(context, data);
  },
  deleteAttachedImage(context, data) {
    // console.log("첨사삭제", context, data);
    apiDeleteAttachedImage(context, data);
  },
  deleteMyprofileImage(context, data) {
    apiDeleteMyprofileImage(data);
  },
  deleteTeacher(context, teacherId) {
    apiDeleteTeacher(context, teacherId);
  },
};
