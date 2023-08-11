import axios from "@/api/axios";
import router from "@/router/index";

function apiGetTeacherList(context, params) {
  console.log("params", params);
  axios
    .get(`/teachers`, {
      params: params,
    })
    .then(({ data }) => {
      console.log("성공", data);
      context.commit("SET_LECTURE_LIST", data);
    })
    .catch((error) => {
      console.log(error);
    });
}

async function apiSearchTeachers(params, success, fail) {
  console.log("apiSearchTeachers params : ", params);
  try {
    const response = await axios.get(`/teachers`, { params: params });
    console.log("데이터는", response);
    success(response);
  } catch (error) {
    fail(error);
  }
}

function apiPostMatchingCreate(context, data) {
  console.log(data);
  axios
    .post("/contacts", data)
    .then(() => {
      router.push("/lecture/studentwaiting");
    })
    .catch((error) => {
      console.log(error);
    });
}

export { apiSearchTeachers, apiPostMatchingCreate, apiGetTeacherList };
