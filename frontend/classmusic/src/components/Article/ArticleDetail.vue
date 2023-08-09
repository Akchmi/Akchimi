<template>
  <div class="container">
    <div class="create__container">
      <div>
        <button @click="$router.push('/article/list')">목록으로</button>
        <hr />

        <div>
          <h2>{{ articleDetail.title }}</h2>
        </div>

        <div>
          <p>조회수:{{ articleDetail.hit }}</p>
          <p>작성자:{{ articleDetail.name }}</p>
        </div>
        <hr />

        <div>
          <h3>{{ articleDetail.content }}</h3>
        </div>
        <hr />

        <h3>첨부파일</h3>

        <div v-if="articleDetail.userId === loginUser">
          <button @click="$router.push(`/article/update/${articleId}`)">
            수정
          </button>
          <button @click="articleDelete">삭제</button>
        </div>
      </div>
      <div>
        <hr />
        <!--게시판 댓글-->
        <div v-if="!isLogin">
          <p>댓글을 작성하려면 로그인을 해주세요</p>
        </div>
        <div v-else>
          <input class="commentBox" type="text" v-model="nowCreateComment" />
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
                닉네임: {{ comment.name }}
                <br />
                <div>
                  <input
                    class="commentBox"
                    type="text"
                    v-if="nowUpdateCommentId == comment.commentId"
                    v-model="nowUpdateCommentContent"
                  />
                  <p v-if="nowUpdateCommentId != comment.commentId">
                    {{ comment.content }}
                  </p>
                </div>
                <div class="commentBottom">
                  <div v-if="nowUpdateCommentId != comment.commentId">
                    작성시간: {{ comment.createdAt }}
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
                    <div v-if="nowUpdateCommentId != comment.commentId">
                      <button
                        @click="
                          commentUpdate(comment.commentId, comment.content)
                        "
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
</template>

<script>
import { onMounted } from "vue";
import { useStore, mapGetters, mapActions } from "vuex";
import { useRoute } from "vue-router";

export default {
  data() {
    return {
      nowUpdateCommentId: null,
      nowUpdateCommentContent: "",
      nowCreateComment: "",
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
  },

  setup() {
    const store = useStore();
    const route = useRoute();
    const articleId = route.params.id;

    onMounted(() => {
      store.dispatch("getArticledetail", articleId);
      store.dispatch("getArticlecomment", articleId);
    });

    return { articleId };
  },
};
</script>
<style lang="scss" scoped>
@import "@/assets/scss/article.scss";
</style>
