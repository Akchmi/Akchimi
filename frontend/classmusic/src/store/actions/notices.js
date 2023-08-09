import {
  apiGetNoticedetail,
  apiGetNoticelist,
  apiNoticecreate,
  apiNoticedelete,
  apiNoticeupdate,
  apiGetNoticePageno,
} from "@/api/notice";

export default {
  //공지게시글 리스트 조회 &검색
  getNoticelist(context, params) {
    apiGetNoticelist(context, params);
    apiGetNoticePageno(context, params.searcyType, params.keyword);
  },

  //공지게시글 상세
  getNoticedetail(context, noticeId) {
    apiGetNoticedetail(context, noticeId);
  },

  //공지게시글 작성
  postNoticeCreate(context, data) {
    apiNoticecreate(context, data);
  },

  //공지게시글 수정
  putNoticeupdate(context, data) {
    apiNoticeupdate(context, data);
  },
  //공지게시글 삭제
  deleteNoticeDelete(context, noticeId) {
    apiNoticedelete(context, noticeId);
  },

  //첨부파일?
};
