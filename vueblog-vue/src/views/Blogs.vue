<template>
  <div class="mcontaner">
    <Home></Home>
    <Header></Header>
    <div class="m-home">
        <div style="margin-left: 400px">
          <el-timeline style="width: 120%; text-align: left">
            <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs">
              <el-card>
                <h4 >
                  <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
                    {{blog.title}}
                  </router-link>
                </h4>
                <p>{{blog.description}}</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>

          <div class="mpage">
            <!--        <span class="demonstration">页数较少时的效果</span>-->
            <el-pagination
                layout="prev, pager, next"
                :current-page="currentPage"
                :page-size="pageSize"
                :total="total"
                @current-change=page
            >
            </el-pagination>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
  import Header from "@/components/Header";
  import Home from "@/components/Home";
  export default {
    name: "Blogs",
    components: {Home, Header},
    data(){
      return {
        blogs: {},
        currentPage: 1,
        total: 0,
        pageSize: 5,
        banners: []
      }
    },
    methods: {
      page(currentPage){
        const _this = this
        _this.$axios.get("/api/blogs?currentPage=" + currentPage).then(res => {
          console.log(res)
          _this.blogs = res.data.data.records
          _this.currentPage = res.data.data.current
          _this.total = res.data.data.total
          _this.pageSize = res.data.data.size
        })
      }
    },
    created() {
      this.page(1)
    }
  }

</script>

<style scoped>
  .mpage{
    margin: 0 auto;
    text-align: center;
    margin-left: 110px;
    padding-bottom: 50px;
  }
  .m-home {
    padding-top: 105vh !important;
    padding-bottom: 0px !important;
  }

</style>