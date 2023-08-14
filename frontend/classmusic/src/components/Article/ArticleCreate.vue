<template>
  <div class="container">
    <div class="create__container">
      <div>
        <!-- 자유게시판 글쓰기 최상단 -->
        <button class="createButton" @click="$router.push('/article/list')">
          목록으로
        </button>
        <h1 style="margin: 20px; font-size: 28px">자유게시판 작성</h1>
        <hr />
      </div>

      <!-- 글 제목, 내용 -->
      <div class="create__content">
        <div style="width: 90%">
          <h3 style="margin: 10px; font-size: 24px">제목</h3>
          <input
            class="input__title"
            type="text"
            v-model="title"
            maxlength="50"
          />
          <h3 style="margin: 10px; font-size: 24px">내용</h3>
          <textarea
            class="input__content"
            type="text"
            v-model="content"
            maxlength="200"
          ></textarea>
        </div>
      </div>
      <div class="create__content__bottom">
        <div>
          <!-- 첨부파일 -->
          <button class="createButton" @click="triggerFileUpload">첨부파일 추가</button>
        </div>
        <div 
          v-for="(image, index) in attachedFiles"
          :key="index"
          class="image-container"        
        >
          <img :src="image.preview" alt="Attached file" class="attach-image" />
          <button class="image-remove" @click="removeAttachedFile(index)">
            삭제
          </button>
        </div>
          <input
            type="file"
            multiple
            ref="fileUploadInput"
            @change="handleFileUpload"
            style="display: none"
          />
        <div>
          <!-- 작성완료 버튼 -->
          <button class="createButton" @click="postArticle">작성완료</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import axios from "@/api/imageAxios.js";



export default {
  data() {
    return {
      title: "",
      content: "",
      attachedFiles: [],
    };
  },
  computed: {
    ...mapGetters({
       createId: "getArticleCreateId",
       articleId: "getAricleId",
      }),
    
  },
  methods: {
    ...mapActions(["postArticleCreate"]),

    async postArticle() {
      
      if (
        this.title.split("\n").join("").length == 0 ||
        this.title.split(" ").join("").length == 0 ||
        this.content.split("\n").join("").length == 0 ||
        this.content.split(" ").join("").length == 0
      ) {
        alert("모든 내용을 입력해주세요!");
        return;
      }
      this.postArticleCreate({
        title: this.title,
        content: this.content,       
      });
      // const articleId = 
      if (this.attachedFiles.length > 0) { 
        await this.submitImages(this.createId);
        console.log('크레이티드아이',this.createId)
      }
    },

		triggerFileUpload() {
			this.$refs.fileUploadInput.click();
		},

    removeAttachedFile(index) {
			this.attachedFiles.splice(index, 1);
		},

    handleFileUpload() {
			const selectedFiles = this.$refs.fileUploadInput.files;

			for (let i = 0; i < selectedFiles.length; i++) {
				const fileReader = new FileReader();

				fileReader.onload = (e) => {
					this.attachedFiles.push({
						preview: e.target.result,
						file: selectedFiles[i],
					});
				};
				fileReader.readAsDataURL(selectedFiles[i]);
			}
		},

    async submitImages(createId) {
			let formData = new FormData();
			this.attachedFiles.forEach((item) => {
				formData.append("image", item.file);
			});

			try {
				await axios.post(`/articles/${createId}/images`, formData);
			} catch (error) {
				console.log(error);
			}
		},

  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/article.scss";

.createButton {
  font-size: 16px;
}
</style>
