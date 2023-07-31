export default {
  totalPages: 0, // 모든 페이지 개수
  pageSize: 5, // 보여주는 개수
  pageNumber: 0, // 현재페이지

  articleListTotalCount: 0,
  articleList: [
    {
      id: 1,
      user: null,
      title: "게시글 제목1",
      content: "내용1",
      createdAt: "2023-02-01T16:54:19.8261845",
      articleImageList: [],
      articleCommentList: [],
    },
  ],
  articleDetail: {
    id: 1,
    user: null,
    title: "게시글 제목1",
    content: "내용1",
    createdAt: "2023-02-01T16:54:19.8261845",
    articleImageList: [],
    articleCommentList: [],
  },

  // comment
  commentListTotalCount: 0,
  commentList: [],
  commentDetail: {},
};
