import {createRouter, createWebHistory} from "vue-router";
import {getToken} from "@/auth";

const routes = [
  {
    path: '/register',
    name: "注册",
    component: () => import("@/view/common/RegisterView.vue"),
    children: []
  },
  {
    path: '/login',
    name: "登录",
    component: () => import("@/view/common/LoginView.vue"),
    children: []
  },
  {
    path: '/findPassword',
    name: "找回密码",
    component: () => import("@/view/common/FindPassword.vue"),
    children: []
  },
  {
    path: '/404',
    name: '404',
    component: () => import("@/components/PageStats404.vue")
  },
  {
    path: '/',
    name: "homeIndex",
    component: () => import("@/view/home/HomeIndex.vue"),
    children: [
      {
        path: 'config',
        name: "ConfigGroup",
        component: () => import("@/view/common/config/ConfigIndex.vue"),
        children: [
        ]
      },
    ]
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return {top: 0}
    }
  },
});
router.beforeEach(async (to, from) => {
  if (to.name === '登录') {
    return;
  }
  console.debug(from.path, to.name);
  // 检查用户是否已登录 // ❗️ 避免无限重定向
  if (!getToken() && (to.name !== '注册' || to.name !== '登录') && to.name) {
    // 将用户重定向到登录页面
    return {name: '登录'}
  }
})
export default router;