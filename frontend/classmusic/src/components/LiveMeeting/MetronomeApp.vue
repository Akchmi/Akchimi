<template>
  <div class="metronome-container">
    <h3 class="text-center mt-4">Metronome</h3>
    <h1 class="text-center text-info mt-3 mb-3">{{ nowBpm }} BPM</h1>
    <input
      class="form-control"
      type="range"
      id="bpm"
      min="40"
      max="220"
      v-model="nowBpm"
    />
    <audio ref="sound" :src="soundPath"></audio>
    <button class="btn" :class="btnClass" @click="togglePlay">
      {{ playBtnText }}
    </button>
  </div>
</template>

<script>
import soundFile from "@/assets/sounds/Impact8.mp3";
export default {
  data() {
    return {
      nowBpm: 60,
      isPlay: false,
      playBtnText: "Start",
      timer: null,
      soundPath: soundFile,
    };
  },
  methods: {
    togglePlay() {
      if (this.isPlay) {
        clearInterval(this.timer);
        this.isPlay = false;
        this.playBtnText = "Start";
      } else {
        this.playBtnText = "Stop";
        this.playSound();
        this.timer = setInterval(this.playSound, this.realBpm(this.nowBpm));
        this.isPlay = true;
      }
    },
    playSound() {
      this.$refs.sound.currentTime = 0;
      this.$refs.sound.play();
    },
    realBpm(bpm) {
      return (60 * 1000) / bpm;
    },
  },
  computed: {
    btnClass() {
      return this.isPlay ? "btn-danger" : "btn-primary";
    },
  },
};
</script>

<style>
.metronome-container {
  width: 90%;
  height: 15%;
  margin-top: 10px;
  padding-top: 10px;
  text-align: center;
  background-color: #edd9b7;
  border-radius: 10px;
}
</style>
