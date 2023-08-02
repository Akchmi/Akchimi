<template>
	<div>
		<h3>career</h3>
		<br />
		<Slider @change="onCareerChange" :value="career" />
		<br />
		<p>경력: {{ career[0] }}년에서 ~ {{ career[1] }}년까지</p>
		<button>완료</button>
	</div>
</template>

<script>
import Slider from "@vueform/slider";

export default {
	components: { Slider },
	data: () => ({}),
	props: {
		career: Array,
	},
	methods: {
		onCareerChange(value) {
			console.log("InputCareer.vue career 수정");
			this.$emit("careerChange", value);
		},
	},
};
</script>

<style src="@vueform/slider/themes/default.css"></style>
