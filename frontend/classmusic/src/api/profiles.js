import axios from "@/api/axios";

async function apiGetUserInfo(id) {
  try {
    const response = await axios.get(`/users/${id}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
}

async function apiChangePw(id, newPassword) {
  try {
    const response = await axios.put(`/users/${id}/password`, {
      password: newPassword,
    });
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
}

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
  console.log(333, data);
  axios
    .post(`/users/${id}/like`, data)
    .then(({ data }) => {
      console.log(11, data);
      return data
    })
    .catch((error) => {
      if ((error.response.data.message) == "이미 존재하는 즐겨찾기입니다."){
        alert(error.response.data.message);
        return 
      }
     console.log(error)
    });
}

async function apiDeleteLIkeTeacher(likeId) {
  try {
    const response = await axios.delete(`users/like/${likeId}`);
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

function apiTeacherProfileCreate(context, data) {
  console.log(data);
  axios
    .post(`/teachers/`, data)
    .then(({ data }) => {
      console.log(data);
    })
    .catch((error) => {
      console.error("POST 요청 에러 : ", error);
    });
}

function apiTeacherProfileUpdate(context, data) {
  const teacherId = data.teacherId;
  console.log("수정은 ", data, teacherId);
  axios
    .put(`/teachers/${teacherId}`, data)
    .then(({ data }) => {
      console.log("풋전송", data);
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
  console.log("리뷰api", teacherId)
  try {
    const response = await axios.get(`/reviews?teacherId=${teacherId}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
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
};
