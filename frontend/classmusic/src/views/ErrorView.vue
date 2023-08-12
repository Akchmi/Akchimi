<template>
	<div>
		<div>
			<NavBar></NavBar>
		</div>
		<TheBanner :title="'사소한 문제가 발생했습니다.'" />
		<router-view></router-view>
	</div>
</template>

<script>
import NavBar from "@/components/common/NavBar.vue";
import TheBanner from "@/components/common/TheBanner";

export default {
	name: "ErrorView",
	data() {
		return {};
	},
	components: {
		NavBar,
		TheBanner,
	},

	methods: {},
};
</script>

<style lang="scss" scoped></style>
