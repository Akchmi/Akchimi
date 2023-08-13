<template>
	<div class="container">
		<div class="create__container">
			<div>
				<!-- 공지사항 글쓰기 최상단 -->
				<h1>공지사항 작성</h1>
				<hr />
			</div>

			<div>
				<!-- 글 제목, 내용 -->
				<h3>제목</h3>
				<input class="input__title" type="text" v-model="title" />
				<h3>내용</h3>
				<textarea
					class="input__content"
					type="text"
					v-model="content"
				/>
				<!-- <hr /> -->
			</div>

			<!-- 첨부파일 -->
			<!-- <div>
				<button>첨부파일 추가</button>
			</div> -->

			<div class="notice-create__buttons">
				<!-- 작성완료 버튼 -->
				<button @click="$router.push('/notice/list')">목록으로</button>
				<button @click="postNotice">작성완료</button>
			</div>
		</div>
	</div>
</template>

<script>
import { mapActions } from "vuex";
export default {
	data() {
		return {
			title: "",
			content: "",
			file: "",
		};
	},
	methods: {
		...mapActions(["postNoticeCreate"]),

		postNotice() {
			if (
				this.title.split("\n").join("").length == 0 ||
				this.title.split(" ").join("").length == 0 ||
				this.content.split("\n").join("").length == 0 ||
				this.content.split(" ").join("").length == 0
			) {
				alert("모든 내용을 입력해주세요!");
				return;
			}
			this.postNoticeCreate({
				title: this.title,
				content: this.content,
				file: this.file,
			});
		},
	},
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/notice.scss";

.notice-create__buttons {
	display: flex;
	align-items: center;
	justify-content: space-between;
	margin-top: 10px;
}
</style>
