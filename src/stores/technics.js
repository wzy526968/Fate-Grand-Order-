import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '../api'

export const useTechnicsStore = defineStore('technics', () => {
  const coffins = ref([])
  const resources = ref([])
  const loading = ref(false)
  const error = ref('')

  // System metrics, alerts, events
  const systemMetrics = ref({})  // keyed by metricKey
  const alerts = ref([])
  const eventLogs = ref([])
  const metricsLoading = ref(false)

  // --- Coffins ---

  async function fetchCoffins() {
    loading.value = true
    error.value = ''
    try {
      const res = await api.get('/api/technics/coffins')
      coffins.value = res.data
    } catch (e) {
      console.error('Failed to fetch coffins', e)
      error.value = 'Failed to load coffin data'
    } finally {
      loading.value = false
    }
  }

  // --- Resources ---

  async function fetchResources() {
    loading.value = true
    error.value = ''
    try {
      const res = await api.get('/api/technics/resources')
      resources.value = res.data
    } catch (e) {
      console.error('Failed to fetch resources', e)
      error.value = 'Failed to load resource data'
    } finally {
      loading.value = false
    }
  }

  async function convertResource(fromType, fromQty, toType) {
    try {
      const res = await api.put('/api/technics/resources/convert', {
        fromType,
        fromQty,
        toType,
      })
      await fetchResources()
      return res.data
    } catch (e) {
      throw new Error(e.response?.data?.error || 'Conversion failed')
    }
  }

  // --- System Metrics ---

  async function fetchMetricsHistory(metricKey, limit = 20) {
    metricsLoading.value = true
    try {
      const res = await api.get(`/api/technics/metrics/${metricKey}/history?limit=${limit}`)
      systemMetrics.value[metricKey] = res.data
    } catch (e) {
      console.warn('Failed to fetch metrics, using simulated data', e)
      generateSimulatedMetrics(metricKey, limit)
    } finally {
      metricsLoading.value = false
    }
  }

  function generateSimulatedMetrics(metricKey, limit = 20) {
    const data = []
    const baseValues = { cpu_load: 45, temperature: 38, memory_usage: 55, disk_usage: 30 }
    const base = baseValues[metricKey] || 50
    for (let i = 0; i < limit; i++) {
      data.push({
        id: i,
        metricKey,
        metricValue: base + (Math.random() - 0.5) * 20,
        recordedAt: new Date(Date.now() - (limit - i) * 60000).toISOString(),
      })
    }
    systemMetrics.value[metricKey] = data
  }

  // --- Alerts ---

  async function fetchAlerts() {
    try {
      const res = await api.get('/api/technics/alerts')
      alerts.value = res.data
    } catch (e) {
      console.warn('Failed to fetch alerts, using simulated data', e)
      generateSimulatedAlerts()
    }
  }

  function generateSimulatedAlerts() {
    alerts.value = [
      { id: 1, severity: 'WARNING', source: 'SHEBA', message: 'Lens aberration detected in quadrant 3 — scheduled recalibration recommended', acknowledged: false, createdAt: new Date(Date.now() - 300000).toISOString() },
      { id: 2, severity: 'INFO', source: 'COOLING', message: 'Coolant-α pump efficiency dropped 3% over 24h', acknowledged: false, createdAt: new Date(Date.now() - 1200000).toISOString() },
      { id: 3, severity: 'CRITICAL', source: 'COFFIN', message: 'COFFIN-17 cryo stability at 18% — immediate inspection required', acknowledged: true, createdAt: new Date(Date.now() - 3600000).toISOString() },
      { id: 4, severity: 'WARNING', source: 'TRISMEGISTUS', message: 'Trismegistus-γ compute load exceeding 85% threshold', acknowledged: false, createdAt: new Date(Date.now() - 2700000).toISOString() },
    ]
  }

  // --- Event Logs ---

  async function fetchEvents(limit = 50) {
    try {
      const res = await api.get(`/api/technics/events?limit=${limit}`)
      eventLogs.value = res.data
    } catch (e) {
      console.warn('Failed to fetch events, using simulated data', e)
      generateSimulatedEvents(limit)
    }
  }

  function generateSimulatedEvents(limit = 30) {
    const types = ['SYSTEM', 'COOLING', 'NETWORK', 'COMPUTE']
    const messages = {
      SYSTEM: [
        'SHEBA lens calibration cycle started',
        'Trismegistus-α firmware update queued',
        'System health check PASSED',
        'Daily data compaction complete — 2.3 GB recovered',
        'Backup relay to Wandering Sea established',
      ],
      COOLING: [
        'Coolant-α flow rate normalized to 72%',
        'CRYO-LOOP temperature stable at -180.2°C',
        'Coolant-β secondary pump engaged',
        'Heat exchanger efficiency at 94.2%',
        'Coolant-γ temperature spike suppressed',
      ],
      NETWORK: [
        'Data link to Chaldea mainframe stable — latency 12ms',
        'Relay 04 handshake confirmed — packet loss 0.02%',
        'External interface handshake with Wandering Sea completed',
        'Sheba uplink signal strength -63 dBm',
        'Internal mesh network reconfiguration complete',
      ],
      COMPUTE: [
        'Trismegistus-α batch job #8741 completed',
        'Sheba FE parallel processing engine active',
        'AI prediction model inference completed in 3.2s',
        'GPU cluster clock rate stabilized at 1.8 GHz',
        'Trismegistus-γ load balancing initiated',
      ],
    }
    const data = []
    const now = Date.now()
    for (let i = 0; i < limit; i++) {
      const type = types[Math.floor(Math.random() * types.length)]
      const msgs = messages[type]
      data.push({
        id: i,
        eventType: type,
        message: msgs[i % msgs.length],
        createdAt: new Date(now - (limit - i) * 180000).toISOString(),
      })
    }
    eventLogs.value = data
  }

  // --- Biometrics simulation (Coffin Room) ---
  const spiritronSyncRate = ref(94.7)
  const manaLoad = ref(32)
  const manaSpikes = ref([])
  const cabinPressure = ref(101.3)
  const o2Level = ref(78.4)
  const nutrientLevel = ref(65.0)
  const hullDurability = ref(87)
  const magicBarrier = ref(72)

  let bioInterval = null

  function startBiometricSimulation() {
    if (bioInterval) return
    bioInterval = setInterval(() => {
      // Random fluctuations
      spiritronSyncRate.value = Math.max(60, Math.min(100, spiritronSyncRate.value + (Math.random() - 0.5) * 2))
      manaLoad.value = Math.max(0, Math.min(100, manaLoad.value + (Math.random() - 0.5) * 5))
      cabinPressure.value = Math.max(95, Math.min(108, cabinPressure.value + (Math.random() - 0.5) * 2))
      o2Level.value = Math.max(70, Math.min(85, o2Level.value + (Math.random() - 0.5) * 1.5))
      nutrientLevel.value = Math.max(50, Math.min(80, nutrientLevel.value + (Math.random() - 0.3) * 1))
      hullDurability.value = Math.max(60, Math.min(100, hullDurability.value + (Math.random() - 0.5) * 0.3))
      magicBarrier.value = Math.max(50, Math.min(90, magicBarrier.value + (Math.random() - 0.5) * 0.5))

      // Random mana spikes (simulating NP usage)
      if (Math.random() < 0.08) {
        const spike = { id: Date.now(), value: 60 + Math.random() * 35, time: Date.now() }
        manaSpikes.value.push(spike)
        // Auto-remove after 2.5s
        setTimeout(() => {
          manaSpikes.value = manaSpikes.value.filter(s => s.id !== spike.id)
        }, 2500)
      }
    }, 1500)
  }

  function stopBiometricSimulation() {
    if (bioInterval) {
      clearInterval(bioInterval)
      bioInterval = null
    }
  }

  return {
    coffins, resources, loading, error,
    systemMetrics, alerts, eventLogs, metricsLoading,
    fetchCoffins, fetchResources, convertResource,
    fetchMetricsHistory, generateSimulatedMetrics,
    fetchAlerts, generateSimulatedAlerts,
    fetchEvents, generateSimulatedEvents,
    // Biometrics
    spiritronSyncRate, manaLoad, manaSpikes,
    cabinPressure, o2Level, nutrientLevel,
    hullDurability, magicBarrier,
    startBiometricSimulation, stopBiometricSimulation,
  }
})
