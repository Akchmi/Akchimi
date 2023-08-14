<template>
  <div class="tuner-container">
    <div class="tuner">
      <!-- <button
        @click.prevent="$root.$toggleGuitarTuner()"
        :class="['tuner-button', { isActive: isTunerActive }]"
      >
        {{ isTunerActive ? "Stop" : "Start" }}
      </button> -->
      <GuitarTuner @statusUpdate="statusUpdate" @dataUpdate="dataUpdate" />
    </div>
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

.tuner{
  position: relative;
  // width: 300px;
  // height: 300px;
  width: 100%;
  margin: 0 auto;
  background-color: #edd9b7;
}
.tuner-button{
  height: 10%;
  width: 40%;
  position: sticky;
  background-color: transparent;
  color: black;
  font-weight: bolder;
  top: 100px;
  left: 100px;
}
</style>
