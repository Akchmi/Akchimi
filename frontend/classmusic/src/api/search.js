import axios from "@/api/axios";

async function apiSearchTeachers(params, success, fail) {
  console.log("apiSearchTeachers params : ", params);
  try {
    const response = await axios.get(`/teachers`, { params: params });
    success(response);
  } catch (error) {
    fail(error);
  }
}

export { apiSearchTeachers };
