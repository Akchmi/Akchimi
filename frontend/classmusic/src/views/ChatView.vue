<template>
  <div>
    <NavBar></NavBar>
    <ChatRoom />
  </div>
</template>

<script>
import NavBar from "@/components/common/NavBar.vue";
import ChatRoom from "@/components/Chat/ChatRoom";

export default {
  data() {
    return {};
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
  },
  methods: {},
  components: {
    NavBar,
    ChatRoom,
  },
};
</script>

<style lang="scss" scoped></style>
