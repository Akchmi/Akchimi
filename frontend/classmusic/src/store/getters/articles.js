export default {
  getTotalPages(state) {
    return state.articles.totalPages;
  },
  getPageSize(state) {
    return state.articles.state;
  },
  getPageNumber(state) {
    return state.articles.pageNumber;
  },
  getArticleListTotalCount(state) {
    return state.articles.articleListTotalCount;
  },
  getArticleList(state) {
    return state.articles.articleList;
  },
  getArticleDetail(state) {
    return state.articles.articleDetail;
  },
  getCommentListTotalCount(state) {
    return state.articles.commentListTotalCount;
  },
  getCommentList(state) {
    return state.articles.commentList;
  },
  getCommentDetail(state) {
    return state.articles.commentDetail;
  },
};
