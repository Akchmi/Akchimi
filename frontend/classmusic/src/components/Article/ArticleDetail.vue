<template>
  <div class="container">
    <div class="create__container">
      <!-- 글 제목, 내용 -->

      <div class="create__content">
        <div style="width: 90%">
          <button class="detailButton" @click="$router.push('/article/list')">
            목록으로
          </button>
          <p style="margin: 10px; font-size: 28px">
            {{ articleDetail.title }}
          </p>
          <div class="detailTitleBottom">
            <div style="display: flex; align-items: center">
              <img
                :src="articleDetail.userProfileImage"
                class="articleDetailProfileimage"
              />
              <p style="margin-left: 10px">
                {{ articleDetail.name }}
              </p>
            </div>
            <div>
              <p>조회수 : {{ articleDetail.hit }}</p>
              <p>
                작성일 :
                {{ toLocalTimeStamp(articleDetail.createdAt) }}
              </p>
            </div>
          </div>
          <div
            v-if="articleDetail.userId === loginUser"
            style="display: flex; justify-content: right"
          >
            <button
              style="margin-right: 10px"
              @click="$router.push(`/article/update/${articleId}`)"
            >
              수정
            </button>
            <button @click="articleDelete">삭제</button>
          </div>
          <hr />
          <h3 style="margin: 10px; min-height: 300px; white-space: pre-line">
            {{ articleDetail.content }}
          </h3>

          <hr />
          <h3>첨부파일</h3>
          <div>
            <img
              v-for="(image, index) in attachedFiles"
              :src="image"
              :key="index"
              alt="Attached file"
              class="attach-image"
            />
          </div>
          <hr />
        </div>
      </div>
      <div style="display: flex; justify-content: center">
        <div style="width: 90%">
          <!--게시판 댓글-->

          <div class="commentContainer" v-if="!isLogin">
            <p>댓글을 작성하려면 로그인을 해주세요</p>
          </div>
          <div class="commentContainer" v-else>
            <textarea
              class="commentBox"
              type="text"
              v-model="nowCreateComment"
              maxlength="200"
            >
            </textarea>
            <button @click="createComment">댓글작성</button>
          </div>

          <hr />

          <div class="commentList">
            <div v-for="comment in commentList" :key="comment.id">
              <div class="comment">
                <div class="commentProfile">
                  <img
                    class="commentProfileImage"
                    :src="comment.userProfileImage"
                    alt=""
                  />
                </div>
                <div class="commentContent">
                  <div class="commentName">
                    <p style="margin-bottom: 4px">
                      {{ comment.name }}
                      <button
                        class="writerButton"
                        disabled
                        v-if="articleDetail.userId == comment.userId"
                      >
                        작성자
                      </button>
                    </p>
                  </div>

                  <div>
                    <input
                      class="commentBox"
                      type="text"
                      v-if="nowUpdateCommentId == comment.commentId"
                      v-model="nowUpdateCommentContent"
                      maxlength="200"
                    />
                    <p
                      style="min-height: 24px"
                      v-if="nowUpdateCommentId != comment.commentId"
                    >
                      {{ comment.content }}
                    </p>
                  </div>
                  <div class="commentBottom">
                    <div
                      v-if="nowUpdateCommentId != comment.commentId"
                      style="font-size: 12px; padding-top: 10px"
                    >
                      작성시간:
                      {{ toLocalTimeStamp(comment.createdAt) }}
                    </div>
                    <div>
                      <div v-if="nowUpdateCommentId == comment.commentId">
                        <button
                          @click="
                            commentUpdate(comment.commentId, comment.content)
                          "
                        >
                          수정완료
                        </button>
                      </div>
                      <div
                        v-if="
                          nowUpdateCommentId != comment.commentId &&
                          loginUser == comment.userId
                        "
                      >
                        <button
                          @click="
                            commentUpdate(comment.commentId, comment.content)
                          "
                          style="margin-right: 10px"
                        >
                          수정
                        </button>
                        <button @click="commentDelete(comment.commentId)">
                          삭제
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <hr />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { useStore, mapGetters, mapActions } from "vuex";
import { useRoute } from "vue-router";
import utils from "@/common/utils";

export default {
  data() {
    return {
      nowUpdateCommentId: null,
      nowUpdateCommentContent: "",
      nowCreateComment: "",
      attachedFiles: [],
    };
  },
  computed: {
    ...mapGetters({ articleDetail: "getArticleDetail" }),
    ...mapGetters({ isLogin: "getIsLogin" }),
    ...mapGetters({ loginUser: "getUserId" }),
    ...mapGetters({ commentList: "getCommentList" }),
  },
  methods: {
    ...mapActions(["deleteArticleDelete"]),

    articleDelete() {
      this.deleteArticleDelete(this.articleDetail.articleId);
    },

    ...mapActions(["postArticleComment"]),

    createComment() {
      this.postArticleComment({
        articleId: this.articleDetail.articleId,
        content: this.nowCreateComment,
      });
      this.nowCreateComment = "";
    },

    ...mapActions(["putArticleComment"]),
    commentUpdate(commentId, commentContent) {
      //수정을 처음 열었을 때
      if (this.nowUpdateCommentId == null) {
        this.nowUpdateCommentId = commentId;
        this.nowUpdateCommentContent = commentContent;
      }
      //수정을 하다가 다른 댓글 수정을 눌렀을 때
      else if (this.nowUpdateCommentId != commentId) {
        this.nowUpdateCommentId = commentId;
        this.nowUpdateCommentContent = commentContent;
      }
      // 수정완료를 눌렀을 때
      else {
        this.putArticleComment({
          articleId: this.articleDetail.articleId,
          commentId: commentId,
          content: this.nowUpdateCommentContent,
        });
        this.nowUpdateCommentId = null;
        this.nowUpdateCommentContent = "";
      }
    },

    ...mapActions(["deleteComment"]),
    commentDelete(commentId) {
      this.deleteComment({
        articleId: this.articleDetail.articleId,
        commentId: commentId,
      });
    },

    toLocalTimeStamp(unixTimeStamp) {
      return utils.unixTimeStampToLocalTimeStamp(unixTimeStamp);
    },
  },

  setup() {
    const store = useStore();
    const route = useRoute();
    const articleId = route.params.id;
    const todayDate = utils.todayDate();

    onMounted(() => {
      store.dispatch("getArticledetail", articleId);
      store.dispatch("getArticlecomment", articleId);
    });
    return { articleId, todayDate };
  },
};
</script>
<style lang="scss" scoped>
@import "@/assets/scss/article.scss";

.detailButton {
  font-size: 16px;
  margin: 10px;
}

.detailTitleBottom {
  display: flex;
  justify-content: space-between;
  padding: 0px 0px 10px 10px;
  align-items: center;
}

.writerButton {
  background-color: transparent;
  border: solid #edd9b7 2px;
  color: #e6b96b;
  cursor: default;
}

.articleDetailProfileimage {
  width: 50px;
  height: 50px;
  border-radius: 100%;
}

.attach-image {
  width: 170px;
  height: 220px;
  object-fit: cover;
  margin-right: 10px;
  margin-top: 10px;
}
</style>
