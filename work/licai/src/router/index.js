import { createRouter, createWebHistory } from 'vue-router'
import IndexView from "@/views/IndexView";
const routes = [
  {
    path: '/',
    name: 'IndexView',
    component: IndexView
  },
  {
    path: '/product/detail',
    name: 'ProdDetailView',
    component: () => import('../views/ProdDetailView.vue')
  },
  {
    path: '/product/list',
    name: 'ProdListView',
    component: () => import('../views/ProdListView.vue')
  },
  {
    path: '/user/login',
    name: 'LoginView',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/user/register',
    name: 'RegisterView',
    component: () => import('../views/RegisterView.vue')
  },
  {
    path: '/user/realName',
    name: 'RealNameView',
    component: () => import('../views/RealNameView.vue')
  },
  {
    path: '/user/center',
    name: 'UserCenterView',
    component: () => import('../views/UserCenterView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
