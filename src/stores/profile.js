import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '../api'

export const useProfileStore = defineStore('profile', () => {
  const profile = ref(null)
  const loading = ref(false)

  async function fetchProfile() {
    loading.value = true
    try {
      const res = await api.get('/api/profiles/me')
      profile.value = res.data
    } catch (e) {
      console.error('Failed to fetch profile', e)
    } finally {
      loading.value = false
    }
  }

  async function updateProfile(data) {
    const res = await api.put('/api/profiles/me', data)
    profile.value = res.data
    return res.data
  }

  async function uploadAvatar(file) {
    const formData = new FormData()
    formData.append('file', file)
    const res = await api.post('/api/upload/avatar', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
    if (profile.value) {
      profile.value.avatarUrl = res.data.avatarUrl
    }
    return res.data
  }

  return { profile, loading, fetchProfile, updateProfile, uploadAvatar }
})
