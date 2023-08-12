<template>
	<div :class="chat.sender == sender ? 'right' : 'left'">
		<!-- <div class="chat-name">보낸이: {{ profile.name }}</div> -->
		<img class="chat-img" :src="profile.profileImage" />
		<span class="chat-content"> {{ chat.content }}</span>
		<span class="chat-sendtime">{{
			toLocalTimeStamp(chat.createdTime)
		}}</span>
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
@import "@/assets/scss/chat.scss";
img {
	width: 50px;
	border-radius: 70%;
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
