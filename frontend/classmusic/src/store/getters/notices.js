export default {
  getTotalPages(state) {
    return state.notices.totalPages;
  },
  getPageSize(state) {
    return state.notices.pageSize;
  },
  getPageNumber(state) {
    return state.notices.pageNumber;
  },
  getNoticeListTotalCount(state) {
    return state.notices.noticeListTotalCount;
  },
  getNoticeList(state) {
    return state.notices.noticeList;
  },
  getNoticeDetail(state) {
    return state.notices.noticeDetail;
  },
};
