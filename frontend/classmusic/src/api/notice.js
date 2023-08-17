import axios from "@/api/axios";
import router from "@/router/index";

function apiGetNoticelist(context, params) {
  axios
    .get("/notices", {
      params: params,
    })
    .then(({ data }) => {
      context.commit("SET_NOTICE_LIST", data);
    })
    .catch((error) => {
      console.error("GET 요청 에러 : ", error);
    });
}

function apiGetNoticePageno(context, searchType, keyword) {
  axios
    .get("/notices/endPageNo", {
      params: {
        searchType: searchType,
        keyword: keyword,
      },
    })
    .then(({ data }) => {
      context.commit("GET_NOTICE_PAGENO", data);
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
    })
    .catch((error) => {
      console.error("GET 요청 에러 : ", error);
    });
}

function apiNoticecreate(context, data) {
  axios
    .post(`/notices`, data)
    .then(({ data }) => {
      context.commit("SET_CREATE_NOTICE_ID", data);
    })
    .catch((error) => {
      console.error("POST 요청 에러 : ", error);
    });
}

function apiNoticeupdate(context, data) {
  const noticeId = data.noticeId;
  axios
    .put(
      `/notices/${noticeId}`,
      JSON.stringify({
        title: data.title,
        content: data.content,
        file: data.file,
      })
    )
    .then(() => {
      router.push(`/notice/${noticeId}`);
    })
    .catch((error) => {
      console.error("PUT 요청 에러 : ", error);
    });
}

function apiNoticedelete(context, noticeId) {
  axios
    .delete(`/notices/${noticeId}`)
    .then(() => {
      router.push(`/notice/list`);
    })
    .catch((error) => {
      console.error("POST 요청 에러 : ", error);
    });
}

export {
  apiGetNoticelist,
  apiGetNoticedetail,
  apiNoticecreate,
  apiNoticedelete,
  apiNoticeupdate,
  apiGetNoticePageno,
};
