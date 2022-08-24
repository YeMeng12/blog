<template>
  <div class="home">
    <Header></Header>
    <div class="blog-banner banner">
      <h1 class="banner-title">博客详情</h1>
    </div>
    <!--基本信息-->
    <div id="waypoint" class="m-container-small m-blog animated fadeInLeft align-center">
      <div class="ui container">
        <div class="ui top attached segment">
          <div class="ui horizontal link list">
            <div class="item">
              <img v-bind:src="dataList.avatar" class="ui avatar image">
              <div class="content"><a class="header">{{dataList.nickname}}</a></div>
            </div>
            <div class="item">
              <i class="calendar icon"></i> {{dataList.created}}
            </div>
            <div class="item">
              <i class="eye icon"></i> {{dataList.viewCount}}
            </div>
            <div class="item">
              <i class="thumbs up outline icon"></i> {{dataList.thumbs}}
            </div>
          </div>
        </div>

        <div class="ui attached padded segment">
          <!--内容-->
          <h2 class="ui center aligned header" v-text="dataList.title"></h2>
          <el-link icon="el-icon-edit" v-if="ownBlog">
            <router-link :to="{name: 'postBlogs', params:{blogId: dataList.id}}">
              编辑
            </router-link>
          </el-link>
          <br>
          <div class="markdown-body" v-html="blog.content" style="text-align: left">
          </div>
        </div>

        <div id="comment-container" class="ui bottom attached segment">
          <!--留言区域列表-->
          <div class="ui blue segment">
            <div class="ui threaded comments" style="max-width: 100%">
              <h3 class="ui dividing header">Comments</h3>
              <div class="comment" v-for="item in commentList" :key="item.commentId">
                <a class="avatar">
                  <img src="https://dspncdn.com/a1/media/originals/89/39/da/8939da328e2907838a932ae262c894e0.jpg">
                </a>
                <div class="content comment-show">
                  <a class="author">user</a>
                  <div class="metadata">
                    <span class="date">{{item.created}}</span>
                  </div>
                  <div class="text" v-html="item.content"></div>
                  <div class="actions" >
                    <a class="reply" @click="replyComment(item)">回复</a>
                    <a class="reply" @click="deleteComment(item.id)" v-show="item.userId === userId">删除</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="ui form">
            <el-form ref="addForm" :model="formData">Comments
              <div class="field">
                <textarea name="content" v-model="formData.content"></textarea>
              </div>
            </el-form>
            <div class="fields">
              <div class="field  m-margin-bottom-small m-mobile-wide">
                <button class="ui blue button m-mobile-wide" @click="addComment"><i class="edit icon"></i>发布</button>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
    <!--        toolbar-->
    <div id="toolbar" class="m-padded m-fixed m-right-bottom" >
      <div class="ui vertical icon buttons ">
        <button type="button" class="ui toc blue button" >目录
        </button>
        <a id="toBottom-button" class="ui blue button" >留言</a>
        <button class="ui wechat icon button"><i class="weixin icon"></i></button>
        <button class="ui icon button" @click="thumbsUp">
          <i v-if="thumbsFlag" class="thumbs up icon"></i>
          <i v-else class="thumbs up outline icon"></i>
        </button>
        <button class="ui icon button" @click="favorite">
          <i v-if="favoriteFlag" class="star icon"></i>
          <i v-else class="star outline icon"></i>
        </button>
        <div id="toTop-button" class="ui icon button" ><i class="chevron up icon"></i></div>
      </div>
    </div>
    <div class="ui toc-container flowing popup transition hidden" style="width: 250px!important;">
      <ol style="overflow: auto" class="js-toc">
      </ol>
    </div>
    <div id="qrcode" class="ui wechat-qr flowing popup transition hidden " style="width: 130px !important;">
    </div>
    <br>
  </div>

</template>

<script>
import 'github-markdown-css/github-markdown.css'
import Header from "@/components/Header";
export default {
  name: "BlogDetail",
  components: {Header},
  data() {
    return {
      blog: {
        content: '',
      },
      formData: {
        blogId: '',
        content: '请输入评论信息...', // 评论内容
        userId: '',
      },
      userId: '',
      ownComment: false,
      ownBlog: false,
      commentList: [],
      dataList: [],
      nickname: '',
      // 被激活的链接地址
      avatar: '',
      thumbsFlag: false,
      favoriteFlag: false
    }
  },
  created: function () {
    this.userId = this.$store.getters.getUserInfo.id
    const blogId = this.$route.params.blogId
    const _this = this
    if (blogId) {
      this.$axios.get("/api/blogs/" + blogId).then(res => {
        const blog = res.data.data
        _this.dataList = blog

        var MarkdownIt = require("markdown-it")
        var md = new MarkdownIt();

        //render
        var result = md.render(blog.content)

        _this.blog.content = result
        _this.ownBlog = (blog.userId === _this.$store.getters.getUserInfo.id)
      })

      this.getCommentList()
    }
  },
  methods: {
    async favorite () {
      if (this.toLogin()) {
        const blogId = this.$route.params.blogId
        const { data: res } = await this.$axios.get("/api/blogs/favorite/" + blogId, {
          headers: {
            "Authorization": localStorage.getItem("token")
          }
        })
        if (res.code === 200) {
          this.$message.success({
            message: '收藏成功',
            type: 'success'
          })
          this.favoriteFlag = true
        }
      }
    },
    async thumbsUp () {
      if (this.toLogin()) {
        const blogId = this.$route.params.blogId
        const { data: res } = await this.$axios.get("/api/blogs/thumbsUp/" + blogId, {
          headers: {
            "Authorization": localStorage.getItem("token")
          }
        })
        if (res.code === 200) {
          this.$message.success({
            message: '点赞成功',
            type: 'success'
          })
          this.thumbsFlag = true
        } else {
          this.$message.info(res.message)
          this.thumbsFlag = false
        }
      }
    },
    toLogin() {
      const tokenStr = this.$store.state.token
      console.log(tokenStr)
      // 后端指定接口验证了token的正确性
      if (!tokenStr) {
        this.$confirm('登录后才能发表评论或者点赞，请问是否先登录？', '提示', { // 确认框
          type: 'info'
        }).then(() => {
          this.$router.push('/login')
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '你选择不登录'
          })
          return false
        })
      }
      return !!tokenStr
    },
    async deleteComment(commentId) {
      this.$confirm('确定删除该评论吗？', '提示', { // 确认框
        type: 'warning'
      }).then(() => {
        // 表单校验通过，发ajax请求，把数据录入至后台处理
        this.$axios.get('/api/commentList/delete/' + commentId).then((res) => {
          if (res.data.code === 200) {
            this.getCommentList()
            this.formData.content = '请输入评论信息...'
            // 弹出提示信息
            this.$message({
              message: '删除评论成功',
              type: 'success'
            })
          } else { // 执行失败
            this.$message.error(res.data.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '删除操作已取消'
        })
      })
    },
    async addComment() {
      if (this.toLogin()) {
        this.formData.blogId = this.$route.params.blogId
        // 表单校验通过，发ajax请求，把数据录入至后台处理
        this.$axios.post('/api/commentList/edit', this.formData, {
          headers: {
            "Authorization": localStorage.getItem("token")
          }
        }).then((res) => {
          this.getCommentList()
        })
      }
    },
    async getCommentList() {
      const blogId = this.$route.params.blogId
      this.$axios.get('/api/commentList/' + blogId).then(res => {
        const comment = res.data.data
        this.commentList = comment
        this.ownComment = (comment.userId === this.$store.getters.getUserInfo.id)
      })
    },
  }
}
</script>

<style scoped>
.m-blog {
  color: black;
  padding-top: 50vh !important;
  padding-bottom: 0px !important;
  position: absolute;
  left: 20%;
  /*transform: translate(50%);*/
  width: 80%;
}
.blog-banner {
  height: 50vh;
  background-color: #164641;
}
.comment-show{
  position: relative;
  margin-top: 0px;
  text-align: left;
}

</style>