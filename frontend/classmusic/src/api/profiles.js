import axios from "@/api/axios";


async function apiGetUserInfo(id) {
  try {
    const response = await axios.get(`/users/${id}`);     
    return response.data
  } catch (error) {
    console.log(error);
    return error
  }
}

async function apiChangePw(id, newPassword) {
  try {
    const response = await axios.put(`/users/${id}/password`,  { password: newPassword })
    return response.data
  } catch (error) {
    console.log(error);
    return error
  }
}

async function apiDeleteUser(id) {
  try {
    const response = await axios.delete(`/users/${id}`)
    return response.data
  } catch (error) {
    console.log(error)
    return error
  }
}

async function apiLikeTeacher(id) {
  try{
    const response = await axios.get(`/users/${id}/like`);
    return response.data
  } catch (error) {
    console.log(error);
    return error
  }
}

async function apiDeleteLIkeTeacher(likeId) {
  try{
    const response = await axios.delete(`users/like/${likeId}`)
    return response.data
  } catch(error) {
    console.log(error)
    return error
  }
}

async function apiDetailTeacherInfo(userId) {
  try{
    const response = await axios.get(`teachers/${userId}`);
    return response.data
  } catch(error) {
    console.log(error)
    return error
  }
}

async function apiTeacherProfileCreate(data) {
  try {
    const response = await axios.post('/teachers', data)
    return response.data
  } catch (error) {
    console.log(error)
    return error
  }
}

async function apiTeacherProfileUpdate(userId, updateData) {
  try {
    const response = await axios.put(`/teachers/${userId}`, updateData)
    return response.data
  } catch (error) {
    console.log(error)
    return error
  }
}

async function apiGetReview(teacherId) {
  try {
    const response = await axios.get(`/reveiws?teacher=${teacherId}`)
    return response.data
  } catch (error) {
    console.log(error)
    return error
  }
}

async function apiReviewCreate(data) {
  try {
    const response = await axios.post(`/reviews`, data)
    return response.data
  } catch (error) {
    console.log(error)
    return error
  }
}

async function apiGetMyReview(matchingId) {
  try {
    const response = await axios.get(`/reviews/myreview?contactId=${matchingId}`)
    return response.data
  } catch (error) {
    console.log(error)
    return error
  }
}



export {apiGetMyReview,  apiReviewCreate, apiGetReview, apiTeacherProfileUpdate, apiTeacherProfileCreate, apiGetUserInfo, apiLikeTeacher , apiDeleteLIkeTeacher, apiChangePw, apiDeleteUser, apiDetailTeacherInfo}