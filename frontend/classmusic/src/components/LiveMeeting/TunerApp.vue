<template>
  <div>
    <button
      @click.prevent="$root.$toggleGuitarTuner()"
      :class="['button', { isActive: isTunerActive }]"
    >
      {{ isTunerActive ? "Stop" : "Start" }}
    </button>
    <div class="tuner-container">
      <GuitarTuner @statusUpdate="statusUpdate" @dataUpdate="dataUpdate" />
    </div>
    <!-- <p @click="$emit('close, false')">닫기</p> -->
  </div>
</template>

<script>
import GuitarTuner from "./src/GuitarTuner.vue";

export default {
  name: "App",
  components: { GuitarTuner },
  data() {
    return {
      isTunerActive: false,
      data: {},
    };
  },
  methods: {
    statusUpdate(status) {
      this.isTunerActive = status;
    },
    dataUpdate(data) {
      this.data = data;
    },
  },
};
</script>

<style lang="scss" scpoed>

.tuner-container {
  position: relative;
  width: 400px;
  height: 400px;
  margin: 0 auto;
  background-color: #333;
}
</style>
