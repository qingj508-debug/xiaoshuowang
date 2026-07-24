import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('@/views/HomeView')
  },
  // {
  //   path: '/about',
  //   name: 'about',
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // }
  {
    path: '/booksearch',
    name: 'booksearch',
    component: () => import('@/views/BookSearch')
  },
  {
    path: '/register',
    name: 'register', 
    component: () => import('@/views/Register')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login')
  },
  {
    path: '/oauth/login/weibo',
    name: 'weibologin',
    component: () => import('../components/OauthLogin')
  },
  {
    path: '/book/:id',
    name: 'book',
    component: () => import('@/views/Book')
  },
  {
    path: '/book/:id/:chapterId',
    name: 'bookContent',
    component: () => import('@/views/BookContent')
   
  },
  {
    path: '/chapterList/:bookId',
    name: 'chapterList',
    component: () => import('@/views/ChapterList')   
  },
  {
    path: '/author/register',
    name: 'authorRegister',
    component: () => import('@/views/author/Register')
  },
  {
    path: '/author/bookList',
    name: 'authorBookList',
    component: () => import('@/views/author/BookList')
  },
  {
    path: '/author/bookAdd',
    name: 'authorBookAdd',
    component: () => import('@/views/author/BookAdd')
  },
  {
    path: '/author/chapterList',
    name: 'authorChapterList',
    component: () => import('@/views/author/ChapterList')
  },
  {
    path: '/author/chapterAdd',
    name: 'authorChapterAdd',
    component: () => import('@/views/author/ChapterAdd')
  },
  {
    path: '/recharge',
    name: 'recharge',
    component: () => import('@/views/Recharge')
  },
  {
    path: '/pay/successed',
    name: 'paySuccessed',
    component: () => import('../components/PaySuccessed')
  },
  {
    path: '/favorites',
    name: 'favorites',
    component: () => import('@/views/member/Favorites')
  },
  {
    path: '/membercenter',
    name: 'membercenter',
    component: () => import('@/views/member/MemberCenter')
  },
  {
    path: '/comment',
    name: 'comment',
    component: () => import('@/views/member/Comment')
  },
  {
    path: '/feedback',
    name: 'feedback',
    component: () => import('@/views/member/Feedback')
  },
  {
    path: '/feedbacklist',
    name: 'feedbacklist',
    component: () => import('@/views/member/FeedbackList')
  },
  {
    path: '/setup',
    name: 'setup',
    component: () => import('@/views/member/Setup')
  },
  {
    path: '/readhistory',
    name: 'readhistory',
    component: () => import('@/views/member/ReadHistory')
  },
  {
    path: '/setname',
    name: 'setname',
    component: () => import('@/views/member/SetName')
  },
  {
    path: '/setsex',
    name: 'setsex',
    component: () => import('@/views/member/SetSex')
  },
  {
    path: '/setpassword',
    name: 'setpassword',
    component: () => import('@/views/member/SetPassword')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
