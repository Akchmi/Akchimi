import axios from "@/api/axios";

async function apiGetUserInfo(id) {
  try {
    const response = await axios.get(`/users/${id}`);
    console.log('겟유인',response)
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
    return { success: false, error: error.response ? error.response.data.message : 'Unknown Error' };
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
  console.log(333, data);
  axios
    .post(`/users/${id}/like`, data)
    .then(({ data }) => {

      alert("즐겨찾기에 성공하였습니다");
      this.$router.push(`/profile/myprofile`);
      return data;
    })
    .catch((error) => {
      if (error.response.data.message == "이미 존재하는 즐겨찾기입니다.") {
        alert(error.response.data.message);
        return;
      }
      console.log(error);
    });
}

async function apiDeleteLIkeTeacher(teacherId) {
  try {
    const response = await axios.delete(`users/like/${teacherId}`);
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
  console.log("apiTeacherProfileCreate data : ", data);
  try {
    const response = await axios.post(`/teachers`, data);
    console.log("post /teachers 결과 : ", response);
    context.commit('SAVE_TEACHERID', response.data)
    return response.data;
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
  console.log("리뷰api", teacherId);
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
