<template>
  <div class="computer-room">
    <!-- Top row: SHEBA + Cooling + Telemetry -->
    <div class="top-row">
      <FrostedPanel title="近未来观测透镜 / SHEBA" variant="terminal" class="sheba-panel">
        <div class="sheba-display">
          <div class="sheba-circle">
            <div class="sheba-ring ring-outer"></div>
            <div class="sheba-ring ring-mid"></div>
            <div class="sheba-ring ring-inner"></div>
            <div class="sheba-center">
              <span class="sheba-label">SHEBA</span>
              <span class="sheba-status">● ONLINE</span>
            </div>
          </div>
          <div class="sheba-data">
            <div class="data-row"><span class="data-label">TARGET</span><span class="data-value">HUMAN HISTORY FOUNDATION</span></div>
            <div class="data-row"><span class="data-label">STATUS</span><span class="data-value status-green">SECURED</span></div>
            <div class="data-row"><span class="data-label">LENS</span><span class="data-value">OPERATIONAL</span></div>
            <div class="data-row"><span class="data-label">SCAN</span><span class="data-value">{{ scanProgress }}%</span></div>
          </div>
        </div>
      </FrostedPanel>

      <FrostedPanel title="冷却系统 / COOLING SYSTEM" variant="terminal" class="cooling-panel">
        <div class="cooling-grid">
          <div v-for="(unit, idx) in coolingUnits" :key="idx" class="cooling-unit">
            <div class="cooling-header">
              <span class="cooling-label">{{ unit.name }}</span>
              <span :class="['cooling-status', unit.online ? 'status-on' : 'status-off']">{{ unit.online ? '●' : '○' }}</span>
            </div>
            <div class="cooling-bar-track">
              <div class="cooling-bar-fill" :style="{ width: unit.load + '%', background: coolingBarColor(unit.load) }"></div>
            </div>
            <div class="cooling-stats">
              <span class="cooling-temp">{{ unit.temp }}°C</span>
            </div>
            <div class="coolant-flow" v-if="unit.online">
              <div v-for="p in 3" :key="p" class="coolant-particle"
                   :style="{ animationDelay: (p * 0.4 + idx * 0.2) + 's' }"></div>
            </div>
          </div>
        </div>
      </FrostedPanel>

      <!-- NEW: Telemetry & Trajectory -->
      <FrostedPanel title="灵子转移遥测 / RAYSHIFT TELEMETRY" variant="terminal" class="telemetry-panel">
        <div class="telemetry-display">
          <!-- Spacetime Radar -->
          <div class="spacetime-radar">
            <div class="radar-ring" v-for="r in 5" :key="r" :style="{ width: (140 - r * 24) + 'px', height: (140 - r * 24) + 'px' }"></div>
            <div class="radar-sweep-arm"></div>
            <div class="radar-target" :style="{ left: '62%', top: '38%' }"></div>
            <div class="radar-center-label">SINGULARITY</div>
          </div>

          <!-- Telemetry data -->
          <div class="telemetry-data">
            <div class="telemetry-row"><span class="tele-label">TARGET</span><span class="tele-value">A.D. 2004, Fuyuki</span></div>
            <div class="telemetry-row"><span class="tele-label">DEVIATION</span><span class="tele-value deviation-val">±{{ spacetimeDeviation.toFixed(3) }}%</span></div>
            <div class="telemetry-row"><span class="tele-label">ANOMALY</span>
              <span class="tele-value" :class="anomalyDensity > 70 ? 'anomaly-high' : 'anomaly-low'">{{ anomalyDensity.toFixed(0) }}%</span>
            </div>

            <!-- Existence Anchor -->
            <div class="anchor-section">
              <span class="tele-label" style="margin-bottom:2px">EXISTENCE ANCHOR</span>
              <div class="anchor-band-container">
                <div class="anchor-band"></div>
                <div class="anchor-pulse-line"></div>
              </div>
              <span class="anchor-status">SIGNAL ACTIVE</span>
            </div>

            <!-- Anomaly particles -->
            <div class="anomaly-container" :class="{ 'anomaly-warning': anomalyDensity > 70 }">
              <div v-for="p in particleCount" :key="p" class="anomaly-particle"
                   :style="{ left: Math.random() * 100 + '%', animationDuration: (1.5 + Math.random() * 2) + 's', animationDelay: Math.random() * 2 + 's' }">
              </div>
              <div class="int-line" v-for="l in 3" :key="l" :style="{ top: (15 + l * 20) + '%', animationDelay: (l * 0.1) + 's' }"></div>
            </div>
          </div>
        </div>
      </FrostedPanel>
    </div>

    <!-- Mid row: Charts + Gauges -->
    <div class="mid-row">
      <FrostedPanel title="性能指标 / PERFORMANCE" variant="terminal" class="charts-panel">
        <div class="mini-charts">
          <div class="chart-container"><span class="chart-label">CPU LOAD</span><div ref="cpuChartRef" class="mini-chart-canvas"></div></div>
          <div class="chart-container"><span class="chart-label">TEMPERATURE</span><div ref="tempChartRef" class="mini-chart-canvas"></div></div>
          <div class="chart-container"><span class="chart-label">MEMORY</span><div ref="memChartRef" class="mini-chart-canvas"></div></div>
          <div class="chart-container"><span class="chart-label">DISK</span><div ref="diskChartRef" class="mini-chart-canvas"></div></div>
        </div>
        <div class="quick-metrics">
          <div class="qm-item"><span class="qm-label">UPTIME</span><span class="qm-value uptime">{{ uptime }}</span></div>
          <div class="qm-item"><span class="qm-label">COMPUTE</span><span class="qm-value">{{ computeLoad.toFixed(0) }}%</span></div>
          <div class="qm-item"><span class="qm-label">THERMAL</span><span class="qm-value">{{ thermalLevel.toFixed(0) }}°C</span></div>
          <div class="qm-item"><span class="qm-label">MEM</span><span class="qm-value">{{ memoryUsed.toFixed(0) }}G</span></div>
        </div>
      </FrostedPanel>

      <FrostedPanel title="储存状态 / STORAGE" variant="terminal" class="storage-panel">
        <div class="storage-gauges">
          <div ref="memGaugeRef" class="gauge-canvas"></div>
          <div ref="diskGaugeRef" class="gauge-canvas"></div>
        </div>
        <div class="storage-details">
          <div class="storage-row"><span>MEMORY</span><span>{{ memoryUsed.toFixed(0) }} / {{ memoryTotal }} GB</span></div>
          <div class="storage-row"><span>DISK</span><span>{{ diskUsed.toFixed(1) }} / {{ diskTotal }} TB</span></div>
        </div>
      </FrostedPanel>
    </div>

    <!-- Bottom row: Event Log + Alerts + Racks -->
    <div class="bottom-row">
      <FrostedPanel title="系统事件日志 / EVENT LOG" variant="terminal" class="eventlog-panel">
        <div class="event-log-container" ref="eventLogRef">
          <div v-for="(event, idx) in filteredEvents" :key="idx"
               :class="['event-entry', 'event-' + event.eventType.toLowerCase()]">
            <span class="event-time">{{ formatTime(event.createdAt) }}</span>
            <span class="event-type-badge">{{ event.eventType }}</span>
            <span class="event-message">{{ event.message }}</span>
          </div>
          <div v-if="filteredEvents.length === 0" class="event-empty">No events recorded.</div>
        </div>
        <div class="event-log-controls">
          <button v-for="f in eventFilters" :key="f.key"
                  class="evt-btn" :class="{ active: eventFilter === f.key }"
                  @click="eventFilter = eventFilter === f.key ? null : f.key">
            {{ f.label }}
          </button>
          <span class="event-count">{{ filteredEvents.length }} events</span>
        </div>
      </FrostedPanel>

      <FrostedPanel title="警报 / ALERTS" variant="terminal" class="alerts-panel">
        <div class="alerts-list">
          <div v-for="(alert, idx) in alerts" :key="idx" :class="['alert-entry', 'alert-' + alert.severity.toLowerCase()]">
            <div class="alert-header">
              <span :class="['alert-severity', 'sev-' + alert.severity.toLowerCase()]">{{ alert.severity }}</span>
              <span class="alert-source">{{ alert.source }}</span>
              <span class="alert-time">{{ formatTime(alert.createdAt) }}</span>
            </div>
            <p class="alert-message">{{ alert.message }}</p>
            <button v-if="!alert.acknowledged" class="alert-ack-btn" @click="acknowledgeAlert(alert.id)">ACK</button>
          </div>
          <div v-if="alerts.length === 0" class="alert-empty">ALL SYSTEMS NOMINAL</div>
        </div>
      </FrostedPanel>

      <FrostedPanel title="三皇算力核心 / TRISMEGISTUS" variant="terminal" class="racks-panel">
        <div class="racks-container">
          <div v-for="(rack, idx) in racks" :key="idx" class="rack-unit">
            <div class="rack-header">
              <span class="rack-name">{{ rack.name }}</span>
              <span :class="['rack-led', rack.online ? 'led-on' : 'led-off']"></span>
            </div>
            <div class="rack-body">
              <div class="rack-row"><span class="rack-label">PWR</span><span :class="['rack-indicator', rack.online ? 'pwr-on' : 'pwr-off']"></span></div>
              <div class="rack-row"><span class="rack-label">NET</span><span class="rack-indicator net-active" :style="{ animationDelay: idx * 0.3 + 's' }"></span></div>
              <div class="rack-row"><span class="rack-label">LOAD</span>
                <div class="rack-bar-track"><div class="rack-bar-fill" :style="{ width: rack.load + '%' }"></div></div>
                <span class="rack-val">{{ rack.load }}%</span>
              </div>
              <div class="rack-row"><span class="rack-label">MEM</span>
                <div class="rack-bar-track"><div class="rack-bar-fill mem-fill" :style="{ width: rack.mem + '%' }"></div></div>
                <span class="rack-val">{{ rack.mem }}%</span>
              </div>
              <div class="rack-row"><span class="rack-label">TEMP</span><span class="rack-val">{{ rack.temp }}°C</span></div>
            </div>
          </div>
        </div>
      </FrostedPanel>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted, nextTick } from 'vue'
import * as echarts from 'echarts'
import FrostedPanel from '../FrostedPanel.vue'
import { useTechnicsStore } from '../../stores/technics'

const store = useTechnicsStore()

// --- Reactive state ---
const computeLoad = ref(42)
const thermalLevel = ref(38)
const scanProgress = ref(67)
const uptime = ref('00:00:00')
const memoryUsed = ref(32)
const memoryTotal = 64
const diskUsed = ref(2.4)
const diskTotal = 8

const coolingUnits = ref([
  { name: 'COOLANT-α', load: 72, temp: 31, online: true },
  { name: 'COOLANT-β', load: 55, temp: 27, online: true },
  { name: 'COOLANT-γ', load: 88, temp: 34, online: true },
  { name: 'CRYO-LOOP', load: 45, temp: -180, online: true },
])

const racks = ref([
  { name: 'TRISMEGISTUS-α', online: true, load: 67, temp: 42, mem: 62 },
  { name: 'TRISMEGISTUS-β', online: true, load: 45, temp: 38, mem: 48 },
  { name: 'TRISMEGISTUS-γ', online: true, load: 81, temp: 48, mem: 75 },
  { name: 'SHEBA-FE', online: true, load: 34, temp: 35, mem: 40 },
])

const eventFilter = ref(null)
const alerts = ref([])
const events = ref([])

// --- Telemetry state ---
const spacetimeDeviation = ref(0.037)
const anomalyDensity = ref(43)
const particleCount = computed(() => anomalyDensity.value > 70 ? 40 : 20)

const eventFilters = [
  { key: 'SYSTEM', label: 'SYS' },
  { key: 'COOLING', label: 'COOL' },
  { key: 'NETWORK', label: 'NET' },
  { key: 'COMPUTE', label: 'CPU' },
]

const filteredEvents = computed(() => {
  if (!eventFilter.value) return events.value
  return events.value.filter(e => e.eventType === eventFilter.value)
})

// --- ECharts refs ---
const cpuChartRef = ref(null)
const tempChartRef = ref(null)
const memChartRef = ref(null)
const diskChartRef = ref(null)
const memGaugeRef = ref(null)
const diskGaugeRef = ref(null)

let cpuChart = null, tempChart = null, memChart = null, diskChart = null
let memGauge = null, diskGauge = null
let interval = null

// --- Data buffers (rolling 20 points) ---
const cpuData = ref(Array.from({ length: 20 }, (_, i) => ({ time: i, value: 35 + Math.random() * 30 })))
const tempData = ref(Array.from({ length: 20 }, (_, i) => ({ time: i, value: 33 + Math.random() * 12 })))
const memData = ref(Array.from({ length: 20 }, (_, i) => ({ time: i, value: 40 + Math.random() * 25 })))
const diskData = ref(Array.from({ length: 20 }, (_, i) => ({ time: i, value: 20 + Math.random() * 20 })))

// --- Chart helper functions ---

function createMiniLine(el, data, color, min, max) {
  if (!el) return null
  const chart = echarts.init(el)
  chart.setOption({
    grid: { left: 2, right: 2, top: 6, bottom: 4 },
    xAxis: { type: 'category', show: false, data: data.map(d => d.time) },
    yAxis: { type: 'value', show: false, min, max, splitLine: { show: false } },
    series: [{
      type: 'line', data: data.map(d => d.value), smooth: true,
      showSymbol: false, animation: false,
      lineStyle: { color, width: 1.5 },
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: color + '40' },
          { offset: 1, color: color + '05' }
        ])
      }
    }]
  })
  return chart
}

function updateMiniLine(chart, data) {
  if (!chart) return
  chart.setOption({
    xAxis: { data: data.map(d => d.time) },
    series: [{ data: data.map(d => d.value) }]
  })
}

function createGauge(el, value, max, name, color) {
  if (!el) return null
  const chart = echarts.init(el)
  chart.setOption({
    series: [{
      type: 'gauge', center: ['50%', '55%'], radius: '90%',
      startAngle: 220, endAngle: -40, min: 0, max,
      progress: { show: true, width: 6, itemStyle: { color } },
      axisLine: { lineStyle: { width: 6, color: [[1, 'rgba(255,255,255,0.06)']] } },
      axisTick: { show: false },
      splitLine: { show: false },
      axisLabel: { show: false },
      detail: {
        valueAnimation: true, formatter: '{value}',
        color: 'rgba(255,255,255,0.7)', fontSize: 14,
        fontFamily: 'JetBrains Mono', offsetCenter: [0, 8]
      },
      data: [{ value, name }]
    }]
  })
  return chart
}

function updateGauge(chart, value) {
  if (!chart) return
  chart.setOption({ series: [{ data: [{ value }] }] })
}

// --- Event & alert helpers ---

function generateEvents() {
  const types = ['SYSTEM', 'COOLING', 'NETWORK', 'COMPUTE']
  const msgs = {
    SYSTEM: ['SHEBA lens calibration cycle started', 'System health check PASSED',
      'Daily data compaction complete', 'Backup relay established',
      'User auth audit log rotated', 'Coffin monitor restarted'],
    COOLING: ['Coolant-α flow normalized to 72%', 'CRYO-LOOP stable at -180.2°C',
      'Coolant-β secondary pump engaged', 'Heat exchanger at 94.2%',
      'Coolant-γ spike suppressed', 'Coolant-α vibration normal'],
    NETWORK: ['Data link stable — latency 12ms', 'Relay 04 handshake confirmed',
      'External handshake completed', 'Sheba uplink -63 dBm',
      'Mesh reconfiguration done', 'Firewall rules updated'],
    COMPUTE: ['Trismegistus-α batch #8741 done', 'Sheba FE parallel active',
      'AI inference in 3.2s', 'GPU clock at 1.8 GHz',
      'Trismegistus-β memory reconciled', 'Load balancing initiated'],
  }
  const data = []
  const now = Date.now()
  for (let i = 29; i >= 0; i--) {
    const type = types[i % types.length]
    const list = msgs[type]
    data.push({
      id: i, eventType: type,
      message: list[i % list.length],
      createdAt: new Date(now - (30 - i) * 180000).toISOString()
    })
  }
  return data
}

function generateAlerts() {
  return [
    { id: 1, severity: 'WARNING', source: 'SHEBA', message: 'Lens aberration detected in quadrant 3 — scheduled recalibration recommended', acknowledged: false, createdAt: new Date(Date.now() - 300000).toISOString() },
    { id: 2, severity: 'INFO', source: 'COOLING', message: 'Coolant-α pump efficiency dropped 3% over 24h — routine maintenance advised', acknowledged: false, createdAt: new Date(Date.now() - 1200000).toISOString() },
    { id: 3, severity: 'CRITICAL', source: 'COFFIN', message: 'COFFIN-17 cryo stability at 18% — immediate inspection required', acknowledged: true, createdAt: new Date(Date.now() - 3600000).toISOString() },
    { id: 4, severity: 'WARNING', source: 'TRISMEGISTUS', message: 'Trismegistus-γ compute load exceeding 85% threshold — consider load balancing', acknowledged: false, createdAt: new Date(Date.now() - 2700000).toISOString() },
  ]
}

function formatTime(iso) {
  if (!iso) return '--:--'
  const d = new Date(iso)
  return d.toLocaleTimeString('ja-JP', { hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false })
}

function acknowledgeAlert(id) {
  const a = alerts.value.find(a => a.id === id)
  if (a) a.acknowledged = true
}

function coolingBarColor(load) {
  if (load < 60) return '#4cd964'
  if (load < 85) return '#ffcc00'
  return '#ff4444'
}

function updateLiveData() {
  // Push new data points and shift
  const pushData = (arr, newVal) => {
    arr.value.push({ time: arr.value.length, value: newVal })
    arr.value.shift()
  }

  computeLoad.value = Math.max(10, Math.min(95, computeLoad.value + (Math.random() - 0.5) * 6))
  thermalLevel.value = Math.max(30, Math.min(70, thermalLevel.value + (Math.random() - 0.5) * 2))
  spacetimeDeviation.value = Math.max(0.01, Math.min(0.5, spacetimeDeviation.value + (Math.random() - 0.5) * 0.02))
  anomalyDensity.value = Math.max(5, Math.min(95, anomalyDensity.value + (Math.random() - 0.5) * 4))
  memoryUsed.value = Math.max(20, Math.min(58, memoryUsed.value + (Math.random() - 0.5) * 3))
  diskUsed.value = Math.max(1, Math.min(6, diskUsed.value + (Math.random() - 0.5) * 0.2))
  scanProgress.value = Math.max(0, Math.min(100, scanProgress.value + (Math.random() - 0.5) * 2))

  pushData(cpuData, computeLoad.value)
  pushData(tempData, thermalLevel.value)
  pushData(memData, memoryUsed.value)
  pushData(diskData, diskUsed.value)

  updateMiniLine(cpuChart, cpuData.value)
  updateMiniLine(tempChart, tempData.value)
  updateMiniLine(memChart, memData.value)
  updateMiniLine(diskChart, diskData.value)
  updateGauge(memGauge, memoryUsed.value)
  updateGauge(diskGauge, diskUsed.value)
}

// --- Lifecycle ---

onMounted(async () => {
  // Load data from store (backend or simulated fallback)
  events.value = store.eventLogs.length > 0 ? store.eventLogs : generateEvents()
  alerts.value = store.alerts.length > 0 ? store.alerts : generateAlerts()

  // Init charts on next tick
  await nextTick()

  cpuChart = createMiniLine(cpuChartRef.value, cpuData.value, '#5796D0', 0, 100)
  tempChart = createMiniLine(tempChartRef.value, tempData.value, '#ff9500', 20, 80)
  memChart = createMiniLine(memChartRef.value, memData.value, '#4cd964', 0, 100)
  diskChart = createMiniLine(diskChartRef.value, diskData.value, '#ffcc00', 0, 100)
  memGauge = createGauge(memGaugeRef.value, memoryUsed.value, memoryTotal, 'MEM', '#5796D0')
  diskGauge = createGauge(diskGaugeRef.value, diskUsed.value, diskTotal, 'DISK', '#ffcc00')

  // Start live simulation
  let seconds = 0
  interval = setInterval(() => {
    seconds++
    const h = String(Math.floor(seconds / 3600)).padStart(2, '0')
    const m = String(Math.floor((seconds % 3600) / 60)).padStart(2, '0')
    const s = String(seconds % 60).padStart(2, '0')
    uptime.value = `${h}:${m}:${s}`
    updateLiveData()
  }, 2000)
})

onUnmounted(() => {
  [cpuChart, tempChart, memChart, diskChart, memGauge, diskGauge].forEach(c => c?.dispose())
  if (interval) clearInterval(interval)
})
</script>

<style scoped>
.computer-room {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* Top row: SHEBA + Cooling + Telemetry */
.top-row { display: flex; gap: 12px; }

/* Mid row: Charts + Gauges */
.mid-row { display: flex; gap: 12px; }

.bottom-row {
  display: flex;
  gap: 12px;
}
.bottom-row .eventlog-panel { flex: 2; }
.bottom-row .alerts-panel { flex: 1.5; }
.bottom-row .racks-panel { flex: 2; }

/* ===== SHEBA ===== */
.sheba-display {
  display: flex;
  align-items: center;
  gap: 20px;
}
.sheba-circle {
  position: relative;
  width: 120px; height: 120px;
  flex-shrink: 0;
  display: flex; align-items: center; justify-content: center;
}
.sheba-ring {
  position: absolute;
  border-radius: 50%;
  border: 1px solid #5796D0;
  opacity: 0.5;
}
.ring-outer { width: 120px; height: 120px; border-width: 1.5px; animation: sheba-scan 6s linear infinite; }
.ring-mid { width: 86px; height: 86px; border-style: dashed; animation: sheba-scan 4s linear infinite reverse; }
.ring-inner { width: 52px; height: 52px; border-style: dotted; animation: sheba-scan 3s linear infinite; }
.sheba-center {
  display: flex; flex-direction: column; align-items: center; gap: 2px;
}
.sheba-label { font-size: 8px; font-family: 'JetBrains Mono', monospace; letter-spacing: 2px; color: rgba(87,150,208,0.8); }
.sheba-status { font-size: 7px; color: #4cd964; font-family: 'JetBrains Mono', monospace; animation: blink 2s step-end infinite; }
.sheba-data { display: flex; flex-direction: column; gap: 5px; flex: 1; }
.data-row { display: flex; justify-content: space-between; align-items: center; padding: 3px 0; border-bottom: 1px solid rgba(255,255,255,0.04); }
.data-label { font-size: 8px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.35); letter-spacing: 1px; }
.data-value { font-size: 9px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.8); }
.status-green { color: #4cd964; }

/* ===== Cooling System ===== */
.cooling-grid { display: flex; flex-direction: column; gap: 8px; }
.cooling-unit {
  padding: 6px 8px;
  border: 1px solid rgba(255,255,255,0.06);
  background: rgba(0,0,0,0.12);
  position: relative; overflow: hidden;
}
.cooling-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 3px; }
.cooling-label { font-size: 8px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.5); letter-spacing: 1px; }
.cooling-status { font-size: 8px; }
.status-on { color: #4cd964; }
.status-off { color: #ff4444; }
.cooling-bar-track { height: 5px; background: rgba(255,255,255,0.06); border: 1px solid rgba(255,255,255,0.08); overflow: hidden; margin-bottom: 3px; }
.cooling-bar-fill { height: 100%; transition: width 1s ease; }
.cooling-stats { display: flex; justify-content: space-between; font-size: 7px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.3); }

/* Coolant flow particles */
.coolant-flow {
  position: absolute; bottom: 0; left: 0; right: 0; height: 2px;
  display: flex; gap: 4px;
}
.coolant-particle {
  width: 8px; height: 2px;
  background: #5796D0; opacity: 0;
  animation: coolant-stream 2s linear infinite;
}
@keyframes coolant-stream {
  0%   { transform: translateX(-12px); opacity: 0; }
  30%  { opacity: 0.7; }
  70%  { opacity: 0.7; }
  100% { transform: translateX(320px); opacity: 0; }
}

/* ===== Mini Charts ===== */
.mini-charts {
  display: grid; grid-template-columns: 1fr 1fr; gap: 8px;
}
.chart-container {
  border: 1px solid rgba(255,255,255,0.06); background: rgba(0,0,0,0.1); padding: 4px;
}
.chart-label {
  font-size: 7px; font-family: 'JetBrains Mono', monospace;
  color: rgba(255,255,255,0.3); letter-spacing: 1px; display: block; margin-bottom: 2px;
}
.mini-chart-canvas { width: 100%; height: 50px; }

.quick-metrics {
  display: flex; gap: 8px; margin-top: 6px; padding-top: 6px;
  border-top: 1px solid rgba(255,255,255,0.05);
}
.qm-item { flex: 1; display: flex; flex-direction: column; align-items: center; gap: 1px; }
.qm-label { font-size: 7px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.25); }
.qm-value { font-size: 11px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.8); }
.uptime { letter-spacing: 1px; }

/* ===== Storage Gauges ===== */
.storage-panel { min-height: 140px; }
.storage-gauges { display: flex; gap: 4px; }
.gauge-canvas { width: 50%; height: 100px; }
.storage-details { margin-top: 4px; }
.storage-row {
  display: flex; justify-content: space-between;
  font-size: 8px; font-family: 'JetBrains Mono', monospace;
  color: rgba(255,255,255,0.4); padding: 2px 0;
}

/* ===== Event Log ===== */
.event-log-container {
  height: 130px; overflow-y: auto; font-family: 'JetBrains Mono', monospace;
}
.event-log-container::-webkit-scrollbar { width: 2px; }
.event-log-container::-webkit-scrollbar-thumb { background: rgba(87,150,208,0.3); }
.event-entry {
  display: flex; gap: 6px; padding: 2px 0;
  border-bottom: 1px solid rgba(255,255,255,0.02);
  font-size: 8px; animation: event-fade-in 0.3s ease;
}
@keyframes event-fade-in {
  0% { opacity: 0; transform: translateX(-4px); }
  100% { opacity: 1; transform: translateX(0); }
}
.event-time { color: rgba(255,255,255,0.15); flex-shrink: 0; width: 52px; }
.event-type-badge { flex-shrink: 0; width: 44px; text-align: center; font-size: 7px; letter-spacing: 0.5px; }
.event-system .event-type-badge { color: #5796D0; }
.event-cooling .event-type-badge { color: #4cd964; }
.event-network .event-type-badge { color: #ffcc00; }
.event-compute .event-type-badge { color: #ff9500; }
.event-message { color: rgba(255,255,255,0.4); flex: 1; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.event-empty { color: rgba(255,255,255,0.15); text-align: center; padding: 16px; font-size: 9px; }
.event-log-controls { display: flex; gap: 3px; margin-top: 6px; align-items: center; }
.evt-btn {
  padding: 2px 6px; font-size: 7px; font-family: 'JetBrains Mono', monospace;
  background: transparent; border: 1px solid rgba(255,255,255,0.1);
  color: rgba(255,255,255,0.3); cursor: pointer; transition: all 0.2s;
}
.evt-btn:hover, .evt-btn.active { color: #5796D0; border-color: #5796D0; }
.event-count { margin-left: auto; font-size: 7px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.2); }

/* ===== Alerts ===== */
.alerts-list { display: flex; flex-direction: column; gap: 5px; }
.alert-entry {
  padding: 6px; border: 1px solid rgba(255,255,255,0.06);
  background: rgba(0,0,0,0.12); animation: alert-slide 0.4s ease;
}
@keyframes alert-slide {
  0% { opacity: 0; transform: translateY(-4px); }
  100% { opacity: 1; transform: translateY(0); }
}
.alert-header { display: flex; gap: 6px; align-items: center; margin-bottom: 3px; }
.alert-severity {
  font-size: 7px; font-family: 'JetBrains Mono', monospace; letter-spacing: 1px;
  padding: 1px 5px; border: 1px solid;
}
.sev-critical { color: #ff4444; border-color: #ff4444; animation: damage-pulse 1.5s ease-in-out infinite; }
.sev-warning { color: #ffcc00; border-color: #ffcc00; }
.sev-info { color: #5796D0; border-color: #5796D0; }
@keyframes damage-pulse {
  0%, 100% { opacity: 0.4; }
  50% { opacity: 1; }
}
.alert-source { font-size: 7px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.3); }
.alert-time { margin-left: auto; font-size: 7px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.2); }
.alert-message { font-size: 8px; color: rgba(255,255,255,0.45); font-family: 'JetBrains Mono', monospace; margin: 0; line-height: 1.4; }
.alert-ack-btn {
  margin-top: 3px; padding: 2px 6px; font-size: 7px;
  font-family: 'JetBrains Mono', monospace;
  background: transparent; border: 1px solid rgba(255,255,255,0.1);
  color: rgba(255,255,255,0.3); cursor: pointer; transition: all 0.2s;
}
.alert-ack-btn:hover { color: #4cd964; border-color: #4cd964; }
.alert-empty { text-align: center; color: #4cd964; font-size: 9px; font-family: 'JetBrains Mono', monospace; padding: 12px; letter-spacing: 2px; }

/* ===== Trismegistus Racks ===== */
.racks-container { display: flex; gap: 8px; flex-wrap: wrap; }
.rack-unit {
  flex: 1; min-width: 140px; max-width: 200px;
  border: 1px solid rgba(255,255,255,0.1); background: rgba(0,0,0,0.2);
}
.rack-header { display: flex; align-items: center; justify-content: space-between; padding: 4px 8px; border-bottom: 1px solid rgba(255,255,255,0.08); }
.rack-name { font-size: 8px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.5); letter-spacing: 1px; }
.rack-led { width: 5px; height: 5px; border-radius: 50%; }
.led-on { background: #4cd964; box-shadow: 0 0 4px #4cd964; animation: blink 2s step-end infinite; }
.led-off { background: #ff4444; }
.rack-body { padding: 6px 8px; display: flex; flex-direction: column; gap: 4px; }
.rack-row { display: flex; align-items: center; gap: 4px; }
.rack-label { font-size: 7px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.3); width: 28px; flex-shrink: 0; }
.rack-indicator { width: 16px; height: 3px; border-radius: 1px; }
.pwr-on { background: #4cd964; }
.pwr-off { background: rgba(255,68,68,0.3); }
.net-active { background: #ffcc00; animation: net-blink 1s step-end infinite; }
@keyframes net-blink { 0%, 100% { opacity: 1; } 50% { opacity: 0.2; } }
.rack-bar-track { flex: 1; height: 3px; background: rgba(255,255,255,0.06); border: 1px solid rgba(255,255,255,0.08); overflow: hidden; }
.rack-bar-fill { height: 100%; background: #5796D0; transition: width 2s ease; }
.mem-fill { background: #7bb3e0; }
.rack-val { font-size: 7px; font-family: 'JetBrains Mono', monospace; color: rgba(255,255,255,0.4); width: 30px; text-align: right; }

@keyframes sheba-scan { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
@keyframes blink { 0%, 100% { opacity: 1; } 50% { opacity: 0.3; } }

/* ===== Telemetry Panel ===== */
.telemetry-panel { flex: 1; }
.telemetry-display { display: flex; gap: 12px; align-items: flex-start; }
.spacetime-radar { position: relative; width: 140px; height: 140px; flex-shrink: 0; display: flex; align-items: center; justify-content: center; }
.spacetime-radar .radar-ring { position: absolute; border-radius: 50%; border: 1px solid rgba(87,150,208,0.15); }
.radar-sweep-arm { position: absolute; top: 50%; left: 50%; width: 50%; height: 1px; background: linear-gradient(90deg, #5796D0, transparent); transform-origin: 0 0; animation: radar-sweep-rotate 4s linear infinite; }
@keyframes radar-sweep-rotate { 100% { transform: rotate(360deg); } }
.radar-target { position: absolute; width: 4px; height: 4px; background: #ff4444; border-radius: 50%; box-shadow: 0 0 6px rgba(255,68,68,0.6); animation: target-pulse 1s ease-in-out infinite; }
@keyframes target-pulse { 0%,100%{opacity:0.5;transform:scale(1)} 50%{opacity:1;transform:scale(1.5)} }
.radar-center-label { font-size: 6px; font-family:'JetBrains Mono',monospace; color: rgba(87,150,208,0.3); letter-spacing: 1px; }
.telemetry-data { flex: 1; display: flex; flex-direction: column; gap: 4px; }
.telemetry-row { display: flex; justify-content: space-between; align-items: center; padding: 3px 0; border-bottom: 1px solid rgba(255,255,255,0.04); }
.tele-label { font-size: 7px; font-family:'JetBrains Mono',monospace; color: rgba(255,255,255,0.3); letter-spacing: 1px; }
.tele-value { font-size: 9px; font-family:'JetBrains Mono',monospace; color: rgba(255,255,255,0.7); }
.deviation-val { color: #ffcc00; }
.anomaly-low { color: #4cd964; }
.anomaly-high { color: #ff4444; }
.anchor-section { margin-top: 2px; }
.anchor-band-container { height: 20px; position: relative; overflow: hidden; background: rgba(0,0,0,0.15); border: 1px solid rgba(87,150,208,0.1); }
.anchor-band { position: absolute; inset: 0; background: linear-gradient(90deg, transparent, rgba(87,150,208,0.2), rgba(87,150,208,0.4), rgba(87,150,208,0.2), transparent); animation: anchor-sweep 2s ease-in-out infinite; }
@keyframes anchor-sweep { 0%{transform:translateX(-100%)} 100%{transform:translateX(100%)} }
.anchor-pulse-line { position: absolute; left: 50%; top: 0; bottom: 0; width: 1px; background: #5796D0; box-shadow: 0 0 6px #5796D0; animation: anchor-pulse 1.5s ease-in-out infinite; }
@keyframes anchor-pulse { 0%,100%{opacity:0.2} 50%{opacity:1} }
.anchor-status { font-size: 6px; font-family:'JetBrains Mono',monospace; color: #4cd964; letter-spacing: 1px; display: block; margin-top: 2px; }
.anomaly-container { position: relative; height: 50px; overflow: hidden; background: rgba(0,0,0,0.15); border: 1px solid rgba(255,68,68,0.06); margin-top: 4px; }
.anomaly-container.anomaly-warning { border-color: rgba(255,68,68,0.3); box-shadow: 0 0 8px rgba(255,68,68,0.05); }
.anomaly-particle { position: absolute; width: 2px; height: 2px; background: rgba(255,255,255,0.3); border-radius: 50%; animation: anomaly-fall linear infinite; pointer-events: none; }
@keyframes anomaly-fall { 0%{transform:translateY(-5px);opacity:0} 10%{opacity:1} 90%{opacity:1} 100%{transform:translateY(60px) translateX(15px);opacity:0} }
.int-line { position: absolute; width: 100%; height: 1px; background: rgba(255,255,255,0.04); animation: int-scroll 0.3s linear infinite; pointer-events: none; }
@keyframes int-scroll { 0%{transform:translateX(-100%)} 100%{transform:translateX(100%)} }

/* ===== Responsive ===== */
@media (max-width: 1000px) {
  .top-row { flex-direction: column; }
  .mid-row { flex-direction: column; }
  .bottom-row { flex-direction: column; }
}
</style>
