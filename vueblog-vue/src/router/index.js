import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import BlogDetail from '../views/BlogDetail.vue'
import BlogEdit from '../views/BlogEdit.vue'
import Music from "@/views/Music"; //
import Discovery from "@/views/music/Discovery";
import Signup from "@/views/Signup";
import AdminHome from "@/components/AdminHome";
import Welcome from "@/views/admin/Welcome";
import FavoriteBlogs from "@/views/admin/blogs/FavoriteBlogs";
import BlogsView from "@/views/admin/blogs/BlogsView";
import postBlogs from "@/views/admin/blogs/PostBlogs";
import Users from "@/views/admin/users/Users";
const Playlists = () => import( '../views/music/Playlists.vue')
const Mvs = () => import( '../views/music/Mvs.vue')
const Songs = () => import('../views/music/Songs.vue')
const Result = () => import('../views/music/Result.vue')
const Mv = () => import('../views/music/Mv.vue')
const Playlist = () => import('../views/music/Playlist.vue')
Vue.use(VueRouter)
export const constantRoutes = [
    {
        path: '/',
        name: 'Index',
        redirect: { name: 'Blogs' }
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/blogs',
        name: 'Blogs',
        // 懒加载
        component: () => import('../views/Blogs.vue')
    },
    {
        path: '/blog/add', // 注意放在 path: '/blog/:blogId'之前
        name: 'BlogAdd',
        meta: {
            requireAuth: true
        },
        component: BlogEdit
    },
    {
        path: '/blog/:blogId',
        name: 'BlogDetail',
        component: BlogDetail
    },
    {
        path: '/blog/:blogId/edit',
        name: 'BlogEdit',
        meta: {
            requireAuth: true
        },
        component: BlogEdit
    },
    {
        path: '/adminHome',
        component: AdminHome,
        redirect: '/welcome',
        children: [
            { path: '/welcome', component: Welcome },
            { path: '/blogsView', component: BlogsView },
            {
                path: '/postBlogs',
                name:'postBlogs',
                component: postBlogs },
            { path: '/favoriteBlogs', component: FavoriteBlogs },
            { path: '/users', component: Users },

        ]
    },

    {
        path: '/signup',
        name: 'Signup',
        component: Signup
    },
    {
        path: '/music',
        name: 'music',
        component: Music,
        children: [
            {
                // 发现音乐
                path: '/discovery',
                component: Discovery
            },
            {
                // 推荐歌单
                path: '/Playlists',
                component: Playlists
            },
            {
                // 推荐歌单
                path: '/Playlist',
                component: Playlist
            },
            {
                // 最新音乐
                path: '/Songs',
                component: Songs
            },
            {
                // 最新音乐
                path: '/Mvs',
                component: Mvs
            },
            // mv详情
            {
                path: '/Mv',
                component: Mv
            },
            // 搜索结果页
            {
                path: '/Result',
                component: Result
            }
        ]
    },
];
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: constantRoutes,
});
export default router