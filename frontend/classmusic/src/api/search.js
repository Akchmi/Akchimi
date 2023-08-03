import axios from "@/api/axios";

async function apiSearchTeachers(params) {
  console.log("apiSearchTeachers params : ", params);
  try {
    const response = await axios.get(`/teachers`, { params: params });

    return response.data;
  } catch (error) {
    console.log("api search teachers error : " + error);
    return error.response;
  }
}

export { apiSearchTeachers };
