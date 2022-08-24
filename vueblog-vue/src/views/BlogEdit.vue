<template>
  <div>
    <Header></Header>
    <div class="blog-banner banner">
      <h1 class="banner-title">编辑博客</h1>
    </div>
    <div id="waypoint" class="m-container-small m-blog animated fadeInLeft align-center">
      <div class="ui container">
        <div class="ui  attached padded segment">
          <div class="m-content">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
              <el-form-item label="标题" prop="title">
                <el-input v-model="ruleForm.title"></el-input>
              </el-form-item>

              <el-form-item label="摘要" prop="description">
                <el-input type="textarea" v-model="ruleForm.description"></el-input>
              </el-form-item>

              <el-form-item label="内容" prop="content">
                <mavon-editor v-model="ruleForm.content"></mavon-editor>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
              </el-form-item>
            </el-form>

          </div>
          </div>
      </div>
  </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
export default {
  name: "BlogEdit",
  components: {Header},
  data() {
    return {
      ruleForm: {
        id: '',
        title: '',
        description: '',
        content: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入摘要', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入正文', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this
          this.$axios.post("/api/blogs/edit", this.ruleForm,{
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          }).then(res => {
            // console.log(res)
            _this.$alert('操作成功', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                _this.$router.push("/blogs")
              }
            });
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    const blogId = this.$route.params.blogId
    // console.log(blogId)
    const _this = this
    if(blogId){
      this.$axios.get("/api/blogs/" + blogId).then(res => {
        const blog = res.data.data
        _this.ruleForm.id = blog.id
        _this.ruleForm.title = blog.title
        _this.ruleForm.description = blog.description
        _this.ruleForm.content.id = blog.content
      })
    }

  }
}

</script>


<style scoped>
  .m-content{
    text-align: center;
  }
  .blog-banner {
    height: 50vh;
    /*background: url(https://dspncdn.com/a1/media/originals/89/39/da/8939da328e2907838a932ae262c894e0.jpg) center*/
    /*center / cover no-repeat;*/
    background-color: #164641;
  }
  .m-blog {
    padding-top: 45vh !important;
    padding-bottom: 0px !important;
    position: absolute;
    left: 20%;
    /*transform: translate(-50%);*/
    width: 100%;
  }

</style>