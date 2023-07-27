export default {
  getTotalPages(state) {
    return state.articles.totalPages;
  },
  getPageSize(state) {
    return state.articles.pageSize;
  },
  getPageNumber(state) {
    return state.articles.pageNumber;
  },
  getNoticeListTotalCount(state) {
    return state.articles.noticeListTotalCount;
  },
  getNoticeList(state) {
    return state.articles.noticeList;
  },
  getNoticeDetail(state) {
    return state.articles.noticeDetail;
  },
};
