import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import api from '../api'

export const usePersonnelStore = defineStore('personnel', () => {
  const allPersonnel = ref([])
  const selectedPersonnel = ref(null)
  const loading = ref(false)
  const activeDepartment = ref(null)

  const departments = computed(() => {
    const deps = new Set(allPersonnel.value.map(p => p.department).filter(Boolean))
    return Array.from(deps).sort()
  })

  const filteredPersonnel = computed(() => {
    if (!activeDepartment.value) return allPersonnel.value
    return allPersonnel.value.filter(p => p.department === activeDepartment.value)
  })

  async function fetchPersonnel() {
    loading.value = true
    try {
      const res = await api.get('/api/personnel')
      allPersonnel.value = res.data
    } catch (e) {
      console.error('Failed to fetch personnel', e)
    } finally {
      loading.value = false
    }
  }

  function selectPersonnel(person) {
    selectedPersonnel.value = person
  }

  function setDepartment(dept) {
    activeDepartment.value = dept
  }

  return {
    allPersonnel, selectedPersonnel, loading, activeDepartment,
    departments, filteredPersonnel,
    fetchPersonnel, selectPersonnel, setDepartment,
  }
})
