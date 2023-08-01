export default {
  SET_ARTICLE_LIST(state, data) {
    console.log(data);
    state.articles.articleList = data;
  },
  GET_PAGENO(state, data) {
    console.log(data);
    state.articles.endPageno = data;
  },
};
