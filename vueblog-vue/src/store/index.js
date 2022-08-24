import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem("token"),
    userInfo: JSON.parse(sessionStorage.getItem("userInfo")),
    isCollapse: false, // 个人后台侧边栏展开状态
  },
  getters: {
    //get
    getUserInfo: state => {
      return state.userInfo
    }
  },
  mutations: {
    //set 存储token，我们用的是localStorage
    SET_TOKEN:(state, token) => {
      state.token = token
      localStorage.setItem("token", token)
    },
    //，存储用户信息，我们用的是sessionStorage
    SET_USERINFO:(state, userInfo) => {
      state.userInfo = userInfo
      //浏览器关闭，session storage可能会丢失
      sessionStorage.setItem("userInfo", JSON.stringify(userInfo))
    },
    REMOVE_INFO:(state) => {
      state.token = ''
      state.userInfo = {}
      localStorage.setItem("token", '')
      sessionStorage.setItem("userInfo", JSON.stringify(''))
    },
  },

  actions: {
  },
  modules: {
  }
})
