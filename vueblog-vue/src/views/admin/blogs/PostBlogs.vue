<template>
  <div class="blogs">
    <div class="content-header">
      <h1 style="text-align: left; color: black">博客管理<small>发布博客</small></h1>
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
        <el-breadcrumb-item :to="{ path: '/' }">后台管理</el-breadcrumb-item>
        <el-breadcrumb-item>博客管理</el-breadcrumb-item>
        <el-breadcrumb-item>发布博客</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
      <div class="app-container">
        <div class="blog-box" style="">
        <el-form ref="addForm" :model="formData" :rules="rules" >
          <div class="required field">
            <div class="ui left labeled input">
              <el-form-item prop="title">
                <el-button type="primary">
                  <i class="el-icon-arrow-down el-icon-s-opportunity"></i>
                </el-button>
                <el-input v-model="formData.title" placeholder="请输入标题" style="width: 790px"></el-input>
              </el-form-item>
            </div>
          </div>
            <div class="mavonEditor" style="margin-top: 10px;">
              <el-form-item prop="content">
                <mavon-editor :codeStyle="markdownOption.codeStyle"
                              ref=md
                              style="max-height: 500px"
                              :scrollStyle="true"
                              :ishljs="true"
                              :toolbars="markdownOption"
                              v-model="formData.content"/>
              </el-form-item>
          </div>

          <div class="field" style="margin-top: 8px;margin-left: 155px">
            <el-form-item prop="description" style="margin-top: 8px">
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 5}"
                placeholder="请写一下关于文章的摘要，这将让你的博客显示在首页时，帮助你吸引更多的读者"
                v-model="formData.description" style="margin-top: 10px;width: 950px">
              </el-input>
            </el-form-item>
          </div>
          <div class="inline fields" style="margin-top: 10px;margin-left: 155px">
            <el-checkbox v-model="formData.recommend">推荐</el-checkbox>
            <el-checkbox v-model="formData.appreciation">赞赏</el-checkbox>
            <el-checkbox v-model="formData.commentAble">评论</el-checkbox>
          </div>
          <div class="ui right aligned container">
            <button type="button" class="ui button" onclick="window.history.go(-1)" >返回</button>
            <button type="button" id="save-btn" class="ui secondary button">保存</button>
            <button type="button" v-if="buttonFlag" id="publish-btn" class="ui my-blue button" @click="addOrUpdateBlog">发布</button>
            <button type="button" v-if="!buttonFlag" id="update-btn" class="ui my-blue button" @click="addOrUpdateBlog">更新</button>
          </div>
        </el-form>
        </div>
      </div>
    <br>
    <br>
  </div>
</template>

<script>
export default {
  data () {
    return {
      buttonFlag: false, // false显示发布,true显示更新
      // 被激活的链接地址
      avatar: '',
      rules: { // 校验规则
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入正文内容', trigger: 'blur' },
          { min: 10, message: '长度最少是 10 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '麻烦写一个简单的摘要', trigger: 'blur' },
          { min: 10, max: 110, message: '长度在 10 到 110 个字符之间', trigger: 'blur' }
        ]
      },
      formData: {
        title: '', // 博客标题
        content: '#### 使用 markdown 编辑器来开始书写你的博客吧!&emsp;已经支持markdown编辑器上传图片的功能', // 正文文本
        recommend: true, // 是否推荐
        appreciation: false, // 是否开启赞赏
        commentAble: true, // 是否开启评论
        value: [], // 标签列表,
        flag: '', // 发布状态 (草稿还是发布)
        description: ''
      }, // 表单数据
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true // 预览
      },
      contentEditor: '',
      markdownOption: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
        codeStyle: 'monokai-sublime'
      }
    }
  },
  created () {
    this.getBlog()
  },
  methods: {
    async getBlog(){
      const blogId = this.$route.params.blogId
      console.log("blogId " + blogId)
      const _this = this
      if (blogId) { // 代表是编辑博客要回显数据
        this.buttonFlag = false
        const { data: res } = await this.$axios.get("/api/blogs/" + blogId)
        this.formData.title = res.data.title
        this.formData.description = res.data.description
        this.formData.content = res.data.content
      } else {
        this.buttonFlag = true
      }
    },
    addOrUpdateBlog() {
      // 进行表单校验
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          // 表单校验通过，发ajax请求，把数据录入至后台处理
          this.formData.flag = '发布'
          // const _this = this
          this.$axios.post("/api/blogs/edit", this.formData, {
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          }).then(res => {
            this.$alert('操作成功', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$router.push("/blogs")
              }
            });
          })
        } else { // 校验不通过
          this.$message.error('校验失败，请检查输入格式')
          return false
        }
      })
    },
  }
}
</script>

<style scoped>
  @import "../../../assets/css/me.css";
.mavonEditor {
  width: 75%;
  height: 100%;
  margin: 0 auto;
}
  .blog-box {
    position: relative;
    border-radius: 3px;
    background: #ffffff;
    border-top: 3px solid #3c8dbc;
    padding: 10px;
    margin-bottom: 20px;
    width: 100%;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
  }
  .avatar-uploader .el-upload {
    border: 5px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 60px;
    height: 60px;
    display: block;
  }
</style>
