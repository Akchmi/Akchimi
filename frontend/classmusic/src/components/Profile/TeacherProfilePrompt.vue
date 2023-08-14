<template>
	<div class="teacher-registration-box">
		<h3 class="teacher-registration-message">아직 강사 등록을 하지 않으셨군요?</h3>
		<button @click="this.$router.push('/profile/teacherprofilecreate')">
			강사 등록하러 가기
		</button>
	</div>
</template>

<script>
export default {};
</script>

<style scoped>
.teacher-registration-message {
	margin-bottom: 20px;
}

.teacher-registration-box {
	position: absolute;
	top: 40%;
	left: 50%;
	width: 40%;
	height: 40%;
	transform: translate(-60%, -20%);
	border: 1.4px solid #edd9b7;
	border-radius: 0.25rem;
	padding: 20px;
	box-sizing: border-box;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}
</style>
