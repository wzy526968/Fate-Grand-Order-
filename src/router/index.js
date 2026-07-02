import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const routes = [
  {
    path: '/',
    name: 'Chaldea',
    component: () => import('../earth.vue'),
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginPage.vue'),
  },
  {
    path: '/facilities',
    name: 'Facilities',
    component: () => import('../views/TechnicalArea.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/sandbox',
    name: 'Sandbox',
    component: () => import('../views/TrismegistusSandbox.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/staff',
    name: 'Staff',
    component: () => import('../views/StaffPage.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/ProfilePage.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/portal',
    name: 'Portal',
    component: () => import('../views/SingularityPortal.vue'),
  },
  {
    path: '/singularities',
    name: 'SingularityList',
    component: () => import('../views/SingularityList.vue'),
  },
  {
    path: '/lostbelts',
    name: 'LostbeltList',
    component: () => import('../views/LostbeltList.vue'),
  },
  {
    path: '/logs',
    name: 'LogIndex',
    component: () => import('../views/LogArticleView.vue'),
  },
  {
    path: '/logs/:id',
    name: 'LogArticle',
    component: () => import('../views/LogArticleView.vue'),
  },
  {
    path: '/singularity-logs',
    name: 'SingularityLogIndex',
    component: () => import('../views/LogArticleView.vue'),
  },
  {
    path: '/singularity-logs/:id',
    name: 'SingularityLogArticle',
    component: () => import('../views/LogArticleView.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    }
    return { top: 0 }
  },
})

router.beforeEach((to, from, next) => {
  const auth = useAuthStore()

  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'Login', query: { redirect: to.fullPath } })
  } else if (to.name === 'Login' && auth.isAuthenticated) {
    next({ name: 'Sandbox' })
  } else {
    next()
  }
})

export default router
