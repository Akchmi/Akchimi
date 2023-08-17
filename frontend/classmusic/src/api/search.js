import axios from "@/api/axios";
import router from "@/router/index";

async function apiSearchTeachers(params, success, fail) {
  // console.log("apiSearchTeachers params : ", params);
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
      alert("강의 신청이 완료되었습니다.");
      router.push("/lecture/studentwaiting");
    })
    .catch(() => {
      alert("이미 신청한 강사입니다.");
    });
}

export { apiSearchTeachers, apiPostMatchingCreate };
