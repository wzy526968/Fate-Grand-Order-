import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '../api'

export const useServantStore = defineStore('servant', () => {
  const servants = ref([])
  const selectedServant = ref(null)
  const loading = ref(false)

  async function fetchServants() {
    loading.value = true
    try {
      const res = await api.get('/api/servants')
      servants.value = res.data
    } catch (e) {
      console.error('Failed to fetch servants', e)
    } finally {
      loading.value = false
    }
  }

  function selectServant(servant) {
    selectedServant.value = servant
  }

  async function createServant(data) {
    const res = await api.post('/api/servants', data)
    servants.value.push(res.data)
    return res.data
  }

  async function updateServant(id, data) {
    const res = await api.put(`/api/servants/${id}`, data)
    const idx = servants.value.findIndex(s => s.id === id)
    if (idx !== -1) {
      servants.value[idx] = res.data
    }
    if (selectedServant.value?.id === id) {
      selectedServant.value = res.data
    }
    return res.data
  }

  async function deleteServant(id) {
    await api.delete(`/api/servants/${id}`)
    servants.value = servants.value.filter(s => s.id !== id)
    if (selectedServant.value?.id === id) {
      selectedServant.value = null
    }
  }

  return { servants, selectedServant, loading, fetchServants, selectServant, createServant, updateServant, deleteServant }
})
