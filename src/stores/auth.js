import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import api from '../api'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || '')
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))

  // Check if token exists — server validates it on every request.
  // If expired, the 401 interceptor in api/index.js triggers logout.
  const isAuthenticated = computed(() => !!token.value)

  const isAdmin = computed(() => {
    return user.value?.role === 'ADMIN'
  })

  function initialize() {
    const savedToken = localStorage.getItem('token')
    if (savedToken) {
      token.value = savedToken
      api.defaults.headers.common['Authorization'] = 'Bearer ' + savedToken

      // Migration: if stored user lacks role, refresh from backend
      if (user.value && !user.value.role) {
        fetchUserInfo()
      }
    }
  }

  async function fetchUserInfo() {
    try {
      const res = await api.get('/api/auth/me')
      if (res.data.role) {
        user.value = {
          username: res.data.username,
          displayName: res.data.displayName,
          role: res.data.role,
        }
        localStorage.setItem('user', JSON.stringify(user.value))
      }
    } catch (_) {
      // Token invalid — logout will happen via 401 interceptor
    }
  }

  async function login(username, password) {
    const res = await api.post('/api/auth/login', { username, password })
    const data = res.data

    token.value = data.token
    user.value = {
      username: data.username,
      displayName: data.displayName,
      role: data.role,
    }

    localStorage.setItem('token', data.token)
    localStorage.setItem('user', JSON.stringify(user.value))
    api.defaults.headers.common['Authorization'] = 'Bearer ' + data.token

    return data
  }

  function logout() {
    token.value = ''
    user.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    delete api.defaults.headers.common['Authorization']
  }

  return { token, user, isAuthenticated, isAdmin, initialize, login, logout, fetchUserInfo }
})
