<template>
  <div>
    <div class="chat-profile" v-if="sender == 0">
      <img class="chat-img" :src="teacherProfile.profileImage" />
      <div class="chat-teachername">{{ teacherProfile.name }} 님</div>
    </div>
    <div class="chat-profile" v-if="sender == 1">
      <img class="chat-img" :src="studentProfile.profileImage" />
      <div class="chat-teachername">{{ studentProfile.name }} 님</div>
    </div>
    <div class="chat-room" ref="chatroom">
      <!-- 채팅 목록 -->
      <TheChat
        v-for="(chat, idx) in chats"
        :key="idx"
        :chat="chat"
        :profile="
          chat.sender == '0' ? this.studentProfile : this.teacherProfile
        "
        :sender="sender"
      />
    </div>
    <!-- 채팅 폼 -->
    <div class="chat-input">
      <input
        class="chat-content"
        v-model="content"
        type="text"
        @keyup.enter="sendMessage"
      />
      <button
        v-if="content.length == 0"
        class="chat-button"
        @click="sendMessage"
        disabled
      >
        전송
      </button>
      <button v-else class="chat-button" @click="sendMessage">전송</button>
    </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import TheChat from "@/components/Chat/TheChat";
import { apiGetChatLog, apiGetChatParticipant } from "@/api/chats.js";
import { useRoute } from "vue-router";
import { mapActions } from "vuex";
// import A210URL from "@/api/axios";

export default {
  data() {
    return {
      roomId: null,
      sender: null,
      content: "",

      studentProfile: {
        name: "",
        profileImage: "",
      },
      teacherProfile: {
        name: "",
        profileImage: "",
      },
      chats: [],
    };
  },
  components: {
    TheChat,
  },

  created() {
    const route = useRoute();
    this.roomId = route.params.lectureId;
    this.sender = route.query.type;

    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect();

    // 채팅방 초기 설정 (참여자 및 이전 채팅 불러오기)
    this.initializeChatRoom();
    this.scrollToBottom();
  },
  updated() {
    this.scrollToBottom();
  },

  methods: {
    ...mapActions(["postChatCreate"]),
    async getChatLog() {
      try {
        this.chats = await apiGetChatLog(this.roomId);

        // console.log("getChatLog : ", this.chats);
      } catch (error) {
        console.log(error);
      }
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const chatroom = this.$refs.chatroom;
        chatroom.scrollTop = chatroom.scrollHeight;
      });
    },

    async initializeChatRoom() {
      // 채팅 참여자 정보 조회
      await this.initializeChatParticipant();

      // 이전 채팅 정보 조회
      await this.getChatLog();
    },

    async initializeChatParticipant() {
      const participantInfo = await apiGetChatParticipant(this.roomId);
      this.teacherProfile = {
        name: participantInfo.teacherName,
        profileImage: participantInfo.teacherProfileImage,
      };
      this.studentProfile = {
        name: participantInfo.studentName,
        profileImage: participantInfo.studentProfileImage,
      };
      // console.log("아웃풋", participantInfo);
    },

    save(newChat) {
      // 채팅 메세지 DB에 추가
      this.postChatCreate(newChat);
    },

    sendMessage() {
      if (this.content.length == 0) {
        return;
      }

      if (this.userName !== "" && this.message !== "") {
        const newChat = {
          content: this.content,
          sender: this.sender,
          roomId: this.roomId,
        };
        this.send(newChat);
        this.save(newChat);
        this.content = "";
      }
    },

    send(newChat) {
      // console.log("Send message:" + this.content);
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/receive", JSON.stringify(newChat), {});
      }
    },

    connect() {
      const serverURL = "https://i9a210.p.ssafy.io:3000/api/websocket";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log(frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.

          this.stompClient.subscribe("/send", (res) => {
            // console.log("구독으로 받은 메시지 입니다.", res.body);
            const livemessage = JSON.parse(res.body);
            if (livemessage.roomId == this.roomId) {
              livemessage.createdTime = new Date() / 1000;

              this.chats.push(livemessage);
            }
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/chat.scss";
</style>
