import axios from "@/api/axios";

function apiGetNoticelist(context, params) {
  axios
    .get("/notices", {
      params: params,
    })
    .then(({ data }) => {
      context.commit("SET_NOTICE_LIST", data);
      console.log(data);
    })
    .catch((error) => {
      console.error("GET 요청 에러 : ", error);
    });
}

function apiGetNoticedetail(context, noticeId) {
  axios
    .get(`/notices/${noticeId}`)
    .then(({ data }) => {
      context.commit("SET_NOTICE_DETAIL", data);
      console.log(data);
    })
    .catch((error) => {
      console.error("GET 요청 에러 : ", error);
    });
}

export { apiGetNoticelist, apiGetNoticedetail };
