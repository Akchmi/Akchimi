<template>
	<div class="container">
		<div class="create__container">
			<div>
				<!-- 공지사항 글쓰기 최상단 -->
				<h1>공지사항 수정</h1>
				<hr />
			</div>

			<div>
				<!-- 글 제목, 내용 -->
				<h3>제목</h3>
				<input
					class="input__title"
					type="text"
					v-model="noticeDetail.title"
				/>
				<h3>내용</h3>
				<textarea
					class="input__content"
					type="text"
					v-model="noticeDetail.content"
				/>
			</div>

			<hr />
			<!-- 첨부파일 -->
			<!-- <div>
        <button>첨부파일 추가</button>
      </div> -->

			<div class="notice-update__buttons">
				<!-- 수정완료 버튼 -->
				<button
					class="notice-update__cancel"
					@click="this.$router.push('/notice/' + this.noticeId)"
				>
					취소
				</button>
				<button @click="noticeUpdate">수정완료</button>
			</div>
		</div>
	</div>
</template>

<script>
import { onMounted } from "vue";
import { useStore, mapGetters, mapActions } from "vuex";
import { useRoute } from "vue-router";

export default {
	computed: {
		...mapGetters({ noticeDetail: "getNoticeDetail" }),
	},
	methods: {
		...mapActions(["putNoticeupdate"]),

		noticeUpdate() {
			this.putNoticeupdate({
				title: this.noticeDetail.title,
				content: this.noticeDetail.content,
				file: "",
				noticeId: this.noticeId,
			});
		},
	},
	setup() {
		const store = useStore();
		const route = useRoute();
		const noticeId = route.params.id;

		onMounted(() => {
			store.dispatch("getNoticedetail", noticeId);
		});

		return { noticeId };
	},
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/notice.scss";

.notice-update__buttons {
	display: flex;
	align-items: center;
	justify-content: right;
}

.notice-update__cancel {
	margin-right: 10px;
}
</style>
