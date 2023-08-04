import axios from "@/api/axios";
import router from "@/router/index";

function apiGetLectureList(context, data) {
  axios
    .get("/contacts", {
      params: data,
    })
    .then(({ data }) => {
      context.commit("GET_LECTURE_LIST", data);
    })
    .catch((error) => {
      console.log("GET 요청 에러 : ", error);
    });
}
function apiGetRefusedLectureList(context, data) {
  axios
    .get("/contacts", {
      params: data,
    })
    .then(({ data }) => {
      context.commit("GET_REFUSED_LECTURE_LIST", data);
    })
    .catch((error) => {
      console.log("GET 요청 에러 : ", error);
    });
}

function apiDeleteContact(context, contactId) {
  axios
    .delete(`/contacts/${contactId}`)
    .then(() => {
      router.go(0);
    })
    .catch((error) => {
      console.log("DELETE 요청 에러 : ", error);
    });
}

function apiPutUpdateMemo(context, data) {
  const contactId = data.contactId;
  const memo = data.memo;

  axios
    .put(`/contacts/${contactId}/memo`, {
      type: 0,
      memo: memo,
    })
    .then(() => {
      router.go(0);
    })
    .catch((error) => {
      console.log("GET 요청 에러 : ", error);
    });
}

export {
  apiGetLectureList,
  apiGetRefusedLectureList,
  apiPutUpdateMemo,
  apiDeleteContact,
};
