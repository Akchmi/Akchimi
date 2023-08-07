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
  const type = data.type;

  axios
    .put(`/contacts/${contactId}/memo`, {
      type: type,
      memo: memo,
    })
    .then(() => {
      router.go(0);
    })
    .catch((error) => {
      console.log("GET 요청 에러 : ", error);
    });
}

function apiGetReview(context, params) {
  axios
    .get("/reviews/myreview", {
      params: params,
    })
    .then(({ data }) => {
      context.commit("GET_REVIEW", data);
    })
    .catch((error) => {
      console.log(error);
    });
}

function apiPostReview(context, params) {
  axios
    .post("/reviews", params)
    .then(() => {
      router.go(0);
    })
    .catch((error) => {
      console.log(error);
    });
}

function apiPutReviewUpdate(context, params) {
  axios
    .put(`/reviews/${params.reviewId}`, params)
    .then(() => {
      router.go(0);
    })
    .catch((error) => {
      console.log(error);
    });
}

function apiDeleteReview(context, reviewId) {
  console.log(reviewId);
  axios
    .delete(`/reviews/${reviewId}`)
    .then(() => {
      router.go(0);
    })
    .catch((error) => {
      console.log(error);
    });
}

export {
  apiGetLectureList,
  apiGetRefusedLectureList,
  apiPutUpdateMemo,
  apiDeleteContact,
  apiGetReview,
  apiPostReview,
  apiPutReviewUpdate,
  apiDeleteReview,
};
