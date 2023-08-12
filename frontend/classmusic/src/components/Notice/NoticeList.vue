<template>
	<div class="container">
		<div class="notice__list">
			<div class="notiec__list__buttons">
				<!-- 공지사항 리스트 최상단 -->
				<button v-if="userType == 2" @click="goNoticeCreate">글 작성</button>
			</div>

			<div>
				<!-- 공지사항 게시물 리스트 -->
				<table>
					<thead>
						<tr>
							<th style="width: 10%">글 번호</th>
							<th style="width: 50%">제목</th>
							<th style="width: 20%">작성자</th>
							<th style="width: 20%">작성일자</th>
						</tr>
					</thead>

					<tbody>
						<tr v-for="notice in noticeList" :key="notice.id">
							<td>{{ notice.noticeId }}</td>
							<td
								@click="$router.push(`/notice/${notice.noticeId}`)"
								style="cursor: pointer"
							>
								{{ notice.title }}
							</td>
							<td>관리자</td>
							<td>{{ toLocalTimeStamp(notice.createAt) }}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 공지사항 리스트 검색바-->
			<div class="noticeSearchBar">
				<div class="noticeSearchSelect">
					<select class="noticeSearchSelectBar" v-model="selectedSearchCategory">
						<option v-for="(item, idx) in searchCategory" :key="idx" :value="item">
							{{ item }}
						</option>
					</select>
				</div>
				<input
					class="noticeSearchInput"
					style="margin: 10px"
					type="text"
					v-model="searchQuery"
					@keyup.enter="runSearch"
				/>

				<img
					@click="runSearch"
					id="noticeSearchButton"
					src="@/assets/images/home/searchButton.png"
					alt="메인검색버튼"
				/>
			</div>

			<div class="noticePageBox">
				<!-- 페이지 번호-->
				<button @click="pageDown">이전</button>
				<button
					class="pageBtn"
					v-for="page in pages"
					:key="page"
					@click="pageChange(page)"
					v-show="page <= lastpage"
				>
					{{ page }}
				</button>
				<button @click="pageUp">다음</button>
			</div>
		</div>
	</div>
</template>

<script>
import { onMounted } from "vue";
import { useStore, mapGetters, mapActions } from "vuex";
import utils from "@/common/utils";
// import axios from "@/common/axios";

export default {
	data() {
		return {
			pages: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
			pageNo: 1,
			searchQuery: "",
			selectedSearchCategory: "전체",
		};
	},

	computed: {
		...mapGetters({ noticeList: "getNoticeList" }),
		...mapGetters({ isLogin: "getIsLogin" }),
		...mapGetters({ userType: "getUsertype" }),
		...mapGetters({ lastpage: "getEndPageNo" }),
	},
	methods: {
		...mapActions(["getNoticelist"]),
		runSearch() {
			this.getNoticelist({
				pageNo: this.pageNo,
				keyword: this.searchQuery,
				searchType: this.selectedSearchCategory,
			});
		},

		pageDown() {
			if (this.pages[0] == 1) {
				alert("최근 페이지입니다.");
				return;
			}

			for (let i = 0; i < 10; i++) {
				this.pages[i] -= 10;
			}
		},

		pageUp() {
			if (this.pages[this.pages.length - 1] >= this.lastpage) {
				alert("마지막 페이지입니다.");
				return;
			}

			for (let i = 0; i < 10; i++) {
				this.pages[i] += 10;
			}
		},

		pageChange(page) {
			this.getNoticelist({
				pageNo: page,
				keyword: this.searchQuery,
				searchType: this.selectedSearchCategory,
			});
		},
		goNoticeCreate() {
			this.$router.push("/notice/create");
		},
		toLocalTimeStamp(unixTimeStamp) {
			return utils.unixTimeStampToLocalTimeStamp(unixTimeStamp);
		},
	},
	setup() {
		const searchCategory = ["전체", "제목", "내용"];
		const store = useStore();

		onMounted(() => {
			store.dispatch("getNoticelist", {
				pageNo: 1,
				keyword: "",
				searchType: "전체",
			});
		});
		return { searchCategory };
	},
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/notice.scss";

.notiec__list__buttons {
	display: flex;
	flex-direction: row;
	justify-content: right;
	align-items: center;
	margin-bottom: 10px;
}
</style>
