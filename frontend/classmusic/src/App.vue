<template>
	<!-- 구성 화면 -->
	<div class="main-page-container">
		<router-view class="main-page"></router-view>
		<TheFooter></TheFooter>
	</div>
</template>

<script>
import TheFooter from "@/components/common/TheFooter";
export default {
	name: "AppVue",
	components: {
		TheFooter,
	},
};
</script>

<style lang="scss">
@import "@/assets/scss/reset.scss";
@import "@/assets/scss/templates/common.scss";

@font-face {
	font-family: "mainfont";
	src: url("assets/font/Maplestory.ttf") format("truetype");
}

.main-page-container {
	height: 115vh;
	display: flex;
	flex-direction: column;
}

.main-page {
	font-family: "mainfont";
	flex: 1;
}
</style>
