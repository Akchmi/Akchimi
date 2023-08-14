import { apiChatcreate } from "@/api/chats";

export default {
  // 필요 기능(회원, 강사 프로필)
  //채팅 메시지 목록 조회
  //채팅 메시지 추가
  //채팅방 참가자 정보 조회
  postChatCreate(context, data) {
    const { content, sender, roomId } = data;
    apiChatcreate(context, { content, sender }, roomId);
  },
};
