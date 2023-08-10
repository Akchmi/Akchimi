import {
  apiTeacherProfileCreate,
  apiTeacherProfileUpdate,
  apiLikeTeacherUpdate,
  apiChangePw,
  apiDeleteAttachedImage,
  apiDeleteMyprofileImage
} from "@/api/profiles.js";

export default {
  async postTeacherProfileCreate(context, data) {
    const result = await apiTeacherProfileCreate(context, data);
    return result;
  },
  async updateUserType({ commit }, userType) {
    commit("SET_USER_TYPE", userType);
  },

  putTeacherProfileUpdate(context, data) {
    console.log("액션", context, data);
    apiTeacherProfileUpdate(context, data);
  },

  postLikeTeacherUpdate(context, data) {
    apiLikeTeacherUpdate(context, data);
  },
  putChangePw(context, data) {
    console.log("비번변경", context, data)
    apiChangePw(context, data)
  },
  deleteAttachedImage(context, data) {
    console.log("첨사삭제", context, data)
    apiDeleteAttachedImage(context,data)
  },
  deleteMyprofileImage(context, data) {
    apiDeleteMyprofileImage(data)
  }
};
