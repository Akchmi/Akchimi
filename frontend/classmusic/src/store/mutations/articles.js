import router from "@/router/index";

export default {
  SET_ARTICLE_LIST(state, data) {
    state.articles.articleList = data;
  },
  GET_PAGENO(state, data) {
    state.articles.endPageno = data;
  },
  SET_ARTICLE_DETAIL(state, data) {
    state.articles.articleDetail = data;
  },
  SET_CREATE_ID(state, data) {
    state.articles.articleCreateId = data;
    router.push(`/article/${data}`);
  },
};
