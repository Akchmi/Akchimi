<template>
  <div :class="chat.sender == sender ? 'right' : 'left'">
    <img :src="profile.profileImage" />
    <div>
      <span>보낸이: {{ profile.name }}</span>
      <div></div>
      <span>내용: {{ chat.content }}</span>
      <div></div>
      <span>보낸 시간: {{ toLocalTimeStamp(chat.createdTime) }}</span>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import utils from "@/common/utils";

export default {
  data() {
    return {};
  },
  props: {
    chat: Object,
    profile: Object,
    sender: String,
  },
  created() {},

  methods: {
    ...mapActions(["postChatCreate"]),

    toLocalTimeStamp(unixTimeStamp) {
      return utils.unixTimeStampToLocalTimeStamp(unixTimeStamp);
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
@import "@/assets/scss/chat.scss";
img {
  width: 40px;
  border-radius: 50%;
}

.left {
  display: flex;
  flex-direction: row;
  justify-content: left;
  align-items: center;
}

.right {
  display: flex;
  flex-direction: row-reverse;
  justify-content: right;
  align-items: center;
}
</style>
