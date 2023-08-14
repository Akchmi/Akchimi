<template>
	<div>
		<!-- 구성 화면 -->
		<div class="container">
			<router-view></router-view>
		</div>
	</div>
</template>

<script>
// import { computed } from "vue";
// import { useStore } from "vuex";

export default {
	setup() {
		// const store = useStore();
	},
};
</script>
"

<style lang="scss">
@import "@/assets/scss/reset.scss";
@import "@/assets/scss/templates/common.scss";

@font-face {
	font-family: "mainfont";
	src: url("assets/font/Maplestory.ttf") format("truetype");
}

.container {
	font-family: "mainfont";
}
</style>
