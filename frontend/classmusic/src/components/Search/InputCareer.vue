<template>
  <h3>career</h3>
  <br>
  <Slider
  v-model="value"
  />
  <br>
  <p>경력: {{ value[0]}}년에서 ~ {{ value[1]}}년까지</p>
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