import {
  apiGetLectureList,
  apiGetRefusedLectureList,
  apiPutUpdateMemo,
  apiDeleteContact,
} from "@/api/lectures";

export default {
  // 필요 기능
  //매칭리스트 조회-학생(진행중)
  getLectureList(context, data) {
    apiGetLectureList(context, data);
  },
  //매칭리스트 조회-학생(대기중)
  getRefusedLectureList(context, data) {
    apiGetRefusedLectureList(context, data);
  },
  //매칭 메모 수정-학생
  putUpdateMemo(context, data) {
    apiPutUpdateMemo(context, data);
  },
  //매칭 삭제
  deleteContact(context, contactId) {
    apiDeleteContact(context, contactId);
  },
  //매칭 상태 수정

  //매칭 순서 수정
  //매칭 생성
  //강의실 입장
};
