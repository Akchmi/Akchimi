export default {
  getNoticeList(state) {
    return state.notices.noticeList;
  },
  getEndPageNo(state) {
    return state.notices.endPageno;
  },
  getNoticeDetail(state) {
    return state.notices.noticeDetail;
  },
  getNowPage(state) {
    return state.notices.nowPage;
  },
};
