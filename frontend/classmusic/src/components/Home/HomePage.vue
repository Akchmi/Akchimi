<template>
  <div class="container">
    <div>
      <!-- <img
        id="mainImage"
        src="@/assets/images/home/mainImage.png"
        alt="메인이미지"
      /> -->
      <TheBanner :title="'악기라는 취미에 미치다.'" />
    </div>

    <div class="mainSearch">
      <input
        class="mainSearchBar"
        type="text"
        placeholder="검색어를 입력해주세요"
        v-model="nowSearchKeyword"
        @keyup.enter="searchKeyword(nowSearchKeyword)"
      />
      <img
        id="mainSearchButton"
        src="@/assets/images/home/searchButton.png"
        alt="메인검색버튼"
        @click="searchKeyword(nowSearchKeyword)"
      />
    </div>
    <div>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/pianoButton.png"
          alt=""
          @click="searchPiano"
        />
      </button>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/baseButton.png"
          alt=""
          @click="searchBase"
        />
      </button>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/guitarButton.png"
          alt=""
          @click="searchGuitar"
        />
      </button>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/violinButton.png"
          alt=""
          @click="searchViolin"
        />
      </button>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/drumButton.png"
          alt=""
          @click="searchDrum"
        />
      </button>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/etcButton.png"
          alt=""
          @click="searchAll"
        />
      </button>
    </div>
  </div>
</template>

<script>
import TheBanner from "@/components/common/TheMainBanner";
import { mapActions, useStore } from "vuex";

export default {
  components: {
    TheBanner,
  },
  data() {
    return {
      nowSearchKeyword: "",
    };
  },
  methods: {
    ...mapActions(["commitKeyword"]),
    ...mapActions(["commitInstrument"]),
    ...mapActions(["searchTeacher"]),

    searchKeyword(searchKeyword) {
      this.commitKeyword(searchKeyword);
      this.searchTeacher();

      this.$router.push("/search");
    },
    searchPiano() {
      this.commitKeyword("");
      this.commitInstrument("피아노");
      this.searchTeacher();

      this.$router.push("/search");
    },
    searchBase() {
      this.commitKeyword("");
      this.commitInstrument("베이스");
      this.searchTeacher();

      this.$router.push("/search");
    },
    searchGuitar() {
      this.commitKeyword("");
      this.commitInstrument("기타");
      this.searchTeacher();

      this.$router.push("/search");
    },
    searchViolin() {
      this.commitKeyword("");
      this.commitInstrument("바이올린");
      this.searchTeacher();

      this.$router.push("/search");
    },
    searchDrum() {
      this.commitKeyword("");
      this.commitInstrument("드럼");
      this.searchTeacher();

      this.$router.push("/search");
    },
    searchAll() {
      this.commitKeyword("");
      this.commitInstrument("악기종류");
      this.searchTeacher();

      this.$router.push("/search");
    },
  },
  setup() {
    const store = useStore();
    store.dispatch("commitInstrument", "악기종류");
    store.dispatch("commitGender", "");
    store.dispatch("commitKeyword", "");
    store.dispatch("commitClassDay", "1111111");
    store.dispatch("commitCost", [0, 100]);
    store.dispatch("commitTime", [0, 100]);
    store.dispatch("commitCareer", [0, 100]);
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/home.scss";

.container {
  justify-content: center;
  text-align: center;
  align-items: center;
}

#mainImage {
  width: 100%;
}

.mainSearch {
  margin-top: 100px;
  margin-bottom: 15px;
  display: flex;
  justify-content: center;
  align-content: center;
}

.mainSearchBar {
  width: 400px;
  height: 50px;
  border-radius: 2em;
  border: 3px solid #edd9b7;
  padding-left: 30px;
  padding-right: 100px;
}

#mainSearchButton {
  width: 40px;
  height: 40px;
  margin-top: 10px;
  margin-left: -60px;
}

#mainSearchButton:hover {
  width: 42px;
  height: 42px;
}

.intstumentButton {
  widows: 60px;
  height: 60px;
}

button {
  background-color: transparent;
  padding: 0;
  border: transparent;
}

button:hover {
  border: solid black 1px;
}
</style>
