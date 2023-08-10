import router from "@/router/index";

export default {
  SET_NOTICE_LIST(state, data) {
    if (data.length == 0) {
      alert("검색된 게시글이 없습니다.");
      return;
    }
    state.notices.noticeList = data;
  },
  GET_NOTICE_PAGENO(state, data) {
    state.notices.endPageno = data;
  },
  SET_NOTICE_DETAIL(state, data) {
    state.notices.noticeDetail = data;
  },
  SET_CREATE_NOTICE_ID(state, data) {
    router.push(`/notice/${data}`);
  },
};
