import axios from "@/api/axios";
import router from "@/router/index";
async function apiGetUserInfo(id) {
  try {
    const response = await axios.get(`/users/${id}`);
    console.log("겟유인", response);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
}

async function apiChangePw(data) {
  const id = data.id;
  try {
    const response = await axios.put(`/users/${id}/password`, data);
    return { success: true, data: response.data };
  } catch (error) {
    console.log("비번변경실패", error);
    return {
      success: false,
      error: error.response ? error.response.data.message : "Unknown Error",
    };
  }
}

// function apiChangePw(context, data) {
//   const id = data.id
//   axios
//     .put(`/users/${id}/password`, data)
//     .then(({data}) => {
//       console.log("비번변경성공", data);
//     })
//     .catch((error) => {
//       console.log("비번변경실패", error)
//     })

// }

async function apiDeleteUser(id) {
  try {
    const response = await axios.delete(`/users/${id}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
}

// function apiTeacherProfileCreate(context, data) {
//   console.log(data);
//   axios
//     .post(`/teachers/`, data)
//     .then(({ data }) => {
//       console.log(data);
//     })
//     .catch((error) => {
//       console.error("POST 요청 에러 : ", error);
//     });
// }

async function apiLikeTeacher(id) {
  try {
    const response = await axios.get(`/users/${id}/like`);
    console.log("444", response.data);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
}

function apiLikeTeacherUpdate(context, data) {
  const id = data.id;
  return axios
    .post(`/users/${id}/like`, data)
    .then(({ data }) => {
      return data;
    })
    .catch((error) => {
      throw error;
    });
}

async function apiDeleteLIkeTeacher(data) {
  try {
    const response = await axios.delete(`users/${data.id}/like/${data.likeId}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
}

async function apiDetailTeacherInfo(teacherId) {
  try {
    const response = await axios.get(`teachers/${teacherId}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
}

async function apiTeacherProfileCreate(context, data) {
  try {
    const response = await axios.post(`/teachers`, data);
    console.log("axios실행", response);
    context.commit("SAVE_TEACHERID", response.data);
    // router.push(`/profile/teacherProfile/${response.data}`);
  } catch (error) {
    console.log("apiTeacherProfileCreate 중 에러 발생!!!", error);
    return null;
  }
}

function apiTeacherProfileUpdate(context, data) {
  const teacherId = data.teacherId;
  axios
    .put(`/teachers/${teacherId}`, data)
    .then(({ data }) => {
      console.log("풋전송", data);
      return data;
    })
    .catch((error) => {
      console.error(teacherId, "PUt 요청 에러 :", error);
    });
}

// async function apiTeacherProfileUpdate(userId, updateData) {
//   try {
//     const response = await axios.put(`/teachers/${userId}`, updateData);
//     return response.data;
//   } catch (error) {
//     console.log(error);
//     return error;
//   }
// }

async function apiGetReview(teacherId) {
  try {
    const response = await axios.get(`/reviews?teacherId=${teacherId}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
}

async function apiDeleteAttachedImage(teacherId, data) {
  try {
    await axios.post(`/teachers/${teacherId}/images/delete`, data);
  } catch (error) {
    console.log("첨부파일삭제실패", error);
    return error;
  }
}

async function apiDeleteMyprofileImage(id) {
  console.log("사진지", id);
  try {
    await axios.delete(`/users/${id}/profileImage`);
    router.go(0);
  } catch (error) {
    console.log("내프사삭", error);
    return error;
  }
}

function apiDeleteTeacher(context, teacherId) {
  axios
    .delete(`/teachers/${teacherId}`)
    .then(() => {
      router.push(`/profile/myprofile`);
    })
    .catch((error) => {
      console.error("DELETE 요청 에러 : ", error);
    });
}

// 강사 활성화/비활성화 토글
async function apiToggleTeacherVisible(teacherId) {
  axios
    .put(`/teachers/${teacherId}/visible`)
    .then(() => {
      router.push(`/profile/myprofile`);
    })
    .catch((error) => {
      console.error("PUT 요청 에러 : ", error);
    });
}

export {
  apiGetReview,
  apiTeacherProfileUpdate,
  apiTeacherProfileCreate,
  apiGetUserInfo,
  apiLikeTeacher,
  apiDeleteLIkeTeacher,
  apiChangePw,
  apiDeleteUser,
  apiDetailTeacherInfo,
  apiLikeTeacherUpdate,
  apiDeleteAttachedImage,
  apiDeleteMyprofileImage,
  apiDeleteTeacher,
  apiToggleTeacherVisible,
};
