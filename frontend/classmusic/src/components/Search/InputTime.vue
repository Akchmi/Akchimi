<template>
	<div>
		<h3>Select Days</h3>

		<div>
			<label v-for="(value, day) in days" :key="day">
				<input type="checkbox" @change="onDaysChange($event, day)" />
				{{ day }}
			</label>
		</div>
		<br />
		<br />
		<Slider @change="onTimeChange" :value="time" :max="24" />
		<br />
		<button>완료</button>
	</div>
</template>

<script>
import Slider from "@vueform/slider";

export default {
	components: { Slider },
	props: {
		days: Object,
		time: Array,
	},
	data: () => ({}),
	methods: {
		onTimeChange(value) {
			console.log("InputTime.vue time 수정");
			this.$emit("timeChange", value);
		},
		onDaysChange(event, day) {
			console.log("InputTime.vue days 수정");
			this.$emit("dayChange", event.target.checked, day);
		},
	},
};
</script>

<style src="@vueform/slider/themes/default.css"></style>
