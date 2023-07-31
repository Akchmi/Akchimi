// import http from "@/common/axios";
// const baseURL = "http://localhost:8080";

export default {
  //공지게시글 리스트 조회
  getNoticelist(context) {
    // http
    //   .get("/notices", {
    //     params: {
    //       pageNo: 0,
    //       keyword: "",
    //       searchType: "",
    //     },
    //   })
    //   .then((data) => {
    //     context.commit("SET_NOTICE_LIST", data);
    //     console.log(data);
    //   })
    //   .catch((error) => {
    //     console.error("GET 요청 에러 : ", error);
    //   });
    context.commit("SET_NOTICE_LIST", [
      {
        id: 1,
        title: "공지사항 제목 1",
        content: "공지사항 내용 1",
        createdAt: "2023-02-01T16:54:19.8261845",
      },
      {
        id: 2,
        title: "공지사항 제목 2",
        content: "공지사항 내용 2",
        createdAt: "2023-02-02T16:54:19.8261845",
      },
      {
        id: 3,
        title: "공지사항 제목 3",
        content: "공지사항 내용 3",
        createdAt: "2023-02-03T16:54:19.8261845",
      },
    ]);
  },

  //공지게시글 작성

  //공지게시글 상세
  //공지게시글 수정
  //공지게시글 삭제
  //첨부파일?
};
