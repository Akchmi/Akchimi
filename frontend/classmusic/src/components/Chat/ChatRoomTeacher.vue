<template>
  <div>chatview</div>
  <div id="app">
    내용: <input v-model="content" type="text" @keyup="sendMessage" />
    <div v-for="(item, idx) in recvList" :key="idx">
      <h3>보낸 시간: {{ item.createdTime }}</h3>
      <h3>샌더: {{ item.sender }}</h3>
      <h3>내용: {{ item.content }}</h3>
    </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { apiGetChatLog } from "@/api/chats.js";
import { useRoute } from "vue-router";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      roomId: 0,
      userName: JSON.parse(localStorage.getItem("vuex")).common.id,
      content: "",
      now: "00:00:00",
      recvList: [],
      sender: 1,
      other: "상대",
    };
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    const route = useRoute();
    this.roomId = route.params.id;
    // console.log(this.roomId + "!!!!!!!!!!!!!");
    this.getChatLog();
    this.connect();
  },
  methods: {
    ...mapActions(["postChatCreate"]),
    async getChatLog() {
      try {
        // console.log(this.roomId + "!!!!!!!!!!!!!");
        // const roomid = this.roomId;

        const data = await apiGetChatLog(this.roomId);
        console.log("getChatLog : ", data);

        if (data) {
          this.recvList = data;
        }
      } catch (error) {
        console.log(error);
      }
    },
    save() {
      // 채팅 메세지 DB에 추가
      this.postChatCreate({
        content: this.content,
        sender: this.sender,
        roomId: this.roomId,
      });
    },
    sendMessage(e) {
      if (e.keyCode === 13 && this.userName !== "" && this.message !== "") {
        this.send();
        this.save();
        this.content = "";
      }
    },
    send() {
      console.log("Send message:" + this.content);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          sender: this.sender,
          roomId: this.roomId,
          content: this.content,
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },

    connect() {
      const serverURL = "http://localhost:8080/websocket";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.

          this.stompClient.subscribe("/send", (res) => {
            console.log("구독으로 받은 메시지 입니다.", res.body);
            var livemessage = JSON.parse(res.body);
            if (livemessage.roomId == this.roomId) {
              var date = new Date();
              livemessage.createdTime =
                date.getHours() +
                ":" +
                date.getMinutes() +
                ":" +
                date.getSeconds();

              this.recvList.push(livemessage);
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
@import "@/assets/scss/templates/common.scss";
</style>
