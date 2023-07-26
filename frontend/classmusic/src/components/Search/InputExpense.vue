<template>
    <h3>Expense</h3>
    <br>
    <Slider
    v-model="value"
    />
    <br>
    <p>한시간당 : {{ value[0]}}만원 에서 ~ {{ value[1]}} 만원 까지</p>
    <button @click="handleComplete">완료</button>
</template>

<script>
import Slider from '@vueform/slider'

export default {
    components: { Slider },
    data: () => ({
        value: [10, 90]
    })
}
</script>

<style src="@vueform/slider/themes/default.css"></style>