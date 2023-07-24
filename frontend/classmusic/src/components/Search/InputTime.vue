<template>
    <div>
      <h3>Select Days</h3>
      
      <div>
        <label v-for="day in days" :key="day">
          <input type="checkbox" :value="day" v-model="selectedDays">
          {{ day }}
        </label>
      </div>
      <br>
      <br>
      <Slider
        v-model="value"
        :max="24"
      />
      <br>
      <button @click="handleComplete">완료</button>
    </div>
  </template>
  
  <script>
  import Slider from '@vueform/slider'
  
  export default {
      components: { Slider },
      data: () => ({
          days: ['월', '화', '수', '목', '금', '토', '일'],
          selectedDays: [],
          value: [10, 24]
      }),
      methods: {
        handleComplete() {
          console.log('Selected days:', this.selectedDays);
          console.log('Slider value:', this.value);

        },
      },
  }
  </script>
  
  <style src="@vueform/slider/themes/default.css"></style>