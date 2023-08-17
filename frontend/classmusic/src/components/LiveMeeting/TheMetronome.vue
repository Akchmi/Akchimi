<template>
  <div class="metronome-container">
    <div>
      <h3>Metronome</h3>
      <h1>{{ nowBpm }} BPM</h1>
      <div class="metronome-control">
        <input
          class="bpm-input"
          type="range"
          id="bpm"
          min="40"
          max="220"
          v-model="nowBpm"
        />
        <button class="start-button" @click="togglePlay">
          {{ playBtnText }}
        </button>
      </div>
      <audio ref="sound" :src="soundPath"></audio>

      <div
        ref="metronome"
        :class="
          this.side == 0
            ? 'metronome is-playing-right'
            : 'metronome is-playing-left'
        "
      >
        <div ref="blink" class="metronome__blink"></div>
        <div class="metronome__viz">
          <div class="metronome__line"></div>
          <div
            ref="indicator"
            :style="cssVariable"
            class="metronome__indicator"
          ></div>
        </div>
      </div>
    </div>
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
      soundPath: soundFile,

      duration: null,
      side: 0,
      vizWidth: "150px",
    };
  },
  computed: {
    cssVariable() {
      return {
        "--right-width": this.vizWidth,
        "--left-width": "-" + this.vizWidth,
      };
    },
  },

  unmounted() {
    this.isPlay = false;
  },

  methods: {
    togglePlay() {
      if (this.isPlay) {
        clearInterval(this.timer);
        this.isPlay = false;
        this.playBtnText = "Start";
      } else {
        this.playBtnText = "Stop";
        this.isPlay = true;
        this.doTick();
      }
    },
    playSound() {
      this.$refs.sound.currentTime = 0;
      this.$refs.sound.play();
    },
    realBpm(bpm) {
      return (60 * 1000) / bpm;
    },
    tick() {
      this.vizWidth = this.$refs.metronome.offsetWidth / 2 + "px";
      this.$refs.blink.style.animationDuration = this.duration + "ms";
      this.$refs.indicator.style.animationDuration = this.duration + "ms";
      if (this.side == 0) {
        this.side = 1;
      } else {
        this.side = 0;
      }
      this.playSound();
    },

    doTick() {
      this.updateValues();
      if (this.isPlay) {
        this.tick();
        setTimeout(this.doTick, this.duration);
      }
    },
    updateValues() {
      this.duration = this.realBpm(this.nowBpm);
    },
  },
};
</script>

<style lang="scss" scoped>
.metronome-control {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.bpm-input {
  background-color: black;
}

.start-button {
  width: 70px;
  background-color: white;
  border: solid #edd9b7 2px;
  color: #e6b96b;
}

.metronome-container {
  width: 325px;
  height: 150px;
  margin-top: 10px;
  padding-top: 10px;
  text-align: center;
  background-color: #edd9b7;
  border-radius: 10px;
  position: absolute;
  left: 17%;
  bottom: 0px;
}

.metronome {
  width: 70%;
  background-color: #ffffff;
  padding: 20px;
  position: absolute;
  border-radius: 8px;
  bottom: 0%;
  left: 50%;
  transform: translate(-50%, -50%);

  &__blink {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    border-radius: 8px;
    background-color: rgba(255, 255, 255, 0.06);
    opacity: 0;
  }

  &__viz {
    position: relative;
  }

  &__line {
    height: 1px;
    background: linear-gradient(
      90deg,
      rgba(255, 255, 255, 0),
      #3b40d8,
      rgba(255, 255, 255, 0)
    );
    position: relative;

    &:before {
      content: "";
      height: 20px;
      width: 1px;
      position: absolute;
      left: 50%;
      top: -9px;
      background-color: #fff;
      opacity: 0.3;
    }
  }

  &__indicator {
    width: 11px;
    height: 11px;
    border-radius: 50%;
    background-color: #3b40d8;
    margin-left: -5px;
    margin-top: -5px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translateX(0);
  }
}

.metronome.is-playing-right,
.metronome.is-playing-left {
  .metronome__blink {
    animation-name: blink;
    animation-iteration-count: 1;
    animation-direction: normal;
    animation-timing-function: ease-in-out;
  }
  .metronome__indicator {
    animation-name: metronome-right;
    animation-iteration-count: 1;
    animation-direction: normal;
  }
}

.metronome.is-playing-right {
  .metronome__indicator {
    animation-name: metronome-right;
  }
}

.metronome.is-playing-left {
  .metronome__indicator {
    animation-name: metronome-left;
  }
}

@keyframes metronome-right {
  0% {
    transform: translateX(0px);
    animation-timing-function: cubic-bezier(
      0.25,
      0.46,
      0.45,
      0.94
    ); /* easeOutQuad */
  }

  50% {
    transform: translateX(var(--right-width));
    animation-timing-function: cubic-bezier(
      0.55,
      0.085,
      0.68,
      0.53
    ); /* easeInQuad */
  }

  100% {
    transform: translateX(0px);
  }

  0%,
  100% {
    opacity: 1;
  }

  10%,
  90% {
    opacity: 0.7;
  }

  50% {
    opacity: 0.5;
  }
}

@keyframes metronome-left {
  0% {
    transform: translateX(0px);
    animation-timing-function: cubic-bezier(
      0.25,
      0.46,
      0.45,
      0.94
    ); /* easeOutQuad */
  }

  50% {
    transform: translateX(var(--left-width));
    animation-timing-function: cubic-bezier(
      0.55,
      0.085,
      0.68,
      0.53
    ); /* easeInQuad */
  }

  100% {
    transform: translateX(0px);
  }

  0%,
  100% {
    opacity: 1;
  }

  10%,
  90% {
    opacity: 0.7;
  }

  50% {
    opacity: 0;
  }
}

@keyframes blink {
  0% {
    opacity: 0;
  }

  30%,
  70% {
    opacity: 0.3;
  }

  100% {
    opacity: 1;
  }
}
</style>
