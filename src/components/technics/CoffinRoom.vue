<template>
  <div class="coffin-room">

    <!-- LEFT: Crypter Coffin Grid -->
    <FrostedPanel title="灵子转移舱室 / COFFIN ROOM" variant="terminal" class="main-chamber">
      <div class="chamber-room">
        <div class="crypter-grid">
          <div v-for="coffin in crypterCoffins" :key="coffin.id"
            :class="['coffin-cell', 'cell-frozen', { 'cell-selected': selectedCoffin?.id === coffin.id }]"
            @click="selectCoffin(coffin)">
            <div class="pod-glass"></div>
            <div class="pod-glare"></div>
            <div class="cryo-mist"></div>
            <div class="pod-indicator ind-frozen"></div>
            <div class="cell-id">{{ coffin.designation }}</div>
            <div class="cell-occupant">{{ coffin.occupantName }}</div>
            <div class="cell-class">{{ coffin.occupantClass }}</div>
            <div class="cell-temp">{{ coffin.temperature.toFixed(1) }}°C</div>
            <div class="cell-stability">
              <div class="stability-track">
                <div class="stability-fill" :style="{ width: coffin.cryoStability + '%', background: stabilityColor(coffin.cryoStability) }"></div>
              </div>
            </div>
            <div class="cell-sync-rate" :class="syncRateClass(coffin.spiritronSync)">
              {{ coffin.spiritronSync.toFixed(1) }}%
            </div>
          </div>
        </div>

        <!-- Master Info Panel — below grid -->
        <transition name="info-fade">
          <div v-if="selectedCoffin" class="master-info-panel">
            <div class="info-header">
              <div class="info-title-row">
                <span class="info-badge">#{{ selectedCoffin.id }}</span>
                <h4 class="info-name">{{ selectedCoffin.occupantName }}</h4>
                <span class="info-name-en">{{ selectedCoffin.nameEn }}</span>
              </div>
              <button class="info-close" @click="selectedCoffin = null">✕</button>
            </div>
            <div class="info-divider"></div>
            <div class="info-body">
              <div class="info-section">
                <div class="info-section-header">
                  <span class="info-section-icon">◆</span>
                  <span class="info-section-label">御主适性 / MASTER COMPATIBILITY</span>
                  <span class="info-rating">{{ selectedCoffin.suitability }}</span>
                </div>
                <p class="info-text">{{ selectedCoffin.suitabilityDesc }}</p>
              </div>
              <div class="info-section">
                <div class="info-section-header">
                  <span class="info-section-icon">◆</span>
                  <span class="info-section-label">后勤记录 / LOGISTICS RECORD</span>
                </div>
                <p class="info-text">{{ selectedCoffin.logistics }}</p>
              </div>
              <div class="info-section">
                <div class="info-section-header">
                  <span class="info-section-icon">◆</span>
                  <span class="info-section-label">当前冷冻监控 / CRYO MONITOR</span>
                </div>
                <p class="info-text">{{ selectedCoffin.cryoMonitor }}</p>
              </div>
            </div>
            <div class="info-footer">
              <span class="info-footer-line">CHALDEAS RECORD · A-TEAM PERSONNEL FILE</span>
              <span class="info-footer-line">LAST UPDATED: 2015-12-31 · SHELTER PROTOCOL</span>
            </div>
          </div>
        </transition>
      </div>
    </FrostedPanel>

    <!-- RIGHT: Diagnostics Sidebar -->
    <div class="diagnostics-sidebar">

      <!-- Panel 1: Biometrics -->
      <FrostedPanel title="生物特征监控 / BIOMETRICS & VITALS" variant="terminal" class="sidebar-panel">
        <div class="bios-grid">
          <!-- Spiritron Sync Rate — ring gauge -->
          <div class="sync-section">
            <span class="sync-label">灵子同步率</span>
            <div class="sync-ring-wrap">
              <div class="sync-ring" :style="syncRingStyle">
                <div class="sync-ring-hole">
                  <span class="sync-value" :class="syncColorClass">{{ spiritronSyncRate.toFixed(1) }}%</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Mana Load -->
          <div class="mana-section">
            <span class="sync-label">魔力负荷</span>
            <div class="mana-value-row">
              <span class="mana-pct" :class="manaColorClass">{{ manaLoad.toFixed(0) }}%</span>
            </div>
            <div class="mana-track">
              <div class="mana-fill" :style="{ width: manaLoad + '%' }"></div>
              <div v-for="spike in manaSpikes" :key="spike.id"
                   class="mana-spike" :style="{ left: spike.value + '%' }"
                   :class="{ 'spike-active': isSpikeActive(spike) }"></div>
            </div>
            <div class="mana-labels">
              <span>STABLE</span>
              <span>OVERLOAD</span>
            </div>
          </div>
        </div>

        <!-- ECG Waveform -->
        <div class="ecg-section">
          <div class="ecg-header">
            <span class="sync-label">心脑波段 / ECG · EEG</span>
            <span class="ecg-bpm">{{ ecgBpm }} BPM</span>
          </div>
          <div class="ecg-canvas-wrap">
            <canvas ref="ecgCanvasRef" class="ecg-canvas"></canvas>
            <div class="ecg-overlay-labels">
              <span class="ecg-label-lead lead-i">I</span>
              <span class="ecg-label-lead lead-ii">II</span>
              <span class="ecg-label-lead lead-eeg">EEG</span>
            </div>
          </div>
        </div>
      </FrostedPanel>

      <!-- Panel 2: Micro-Environment -->
      <FrostedPanel title="舱体微环境 / MICRO-ENVIRONMENT" variant="terminal" class="sidebar-panel">
        <!-- Cabin readings 2×2 -->
        <div class="cabin-grid">
          <div class="cabin-cell">
            <span class="cabin-label">PRESSURE</span>
            <span class="cabin-value">{{ cabinPressure.toFixed(1) }} kPa</span>
          </div>
          <div class="cabin-cell">
            <span class="cabin-label">O₂ LEVEL</span>
            <span class="cabin-value">{{ o2Level.toFixed(1) }}%</span>
          </div>
          <div class="cabin-cell">
            <span class="cabin-label">NUTRIENT</span>
            <span class="cabin-value">{{ nutrientLevel.toFixed(0) }}%</span>
          </div>
          <div class="cabin-cell">
            <span class="cabin-label">TEMP</span>
            <span class="cabin-value">{{ avgTemp }}</span>
          </div>
        </div>

        <!-- Cryo-sleep status -->
        <div class="cryo-section">
          <div class="cryo-header">
            <span class="sync-label">冷冻睡眠 / CRYO-SLEEP</span>
          </div>
          <div class="cryo-block">
            <div class="cryo-temp-display">-196.0°C</div>
            <div class="cryo-info">
              <span>TARGET TEMPERATURE</span>
              <span class="cryo-count">{{ frozenCount }} UNITS ACTIVE</span>
            </div>
          </div>
        </div>

        <!-- Armor integrity -->
        <div class="armor-section">
          <div class="armor-row">
            <span class="cabin-label">HULL</span>
            <div class="armor-track"><div class="armor-fill" :style="{ width: hullDurability + '%' }"></div></div>
            <span class="armor-val">{{ hullDurability }}%</span>
          </div>
          <div class="armor-row">
            <span class="cabin-label">BARRIER</span>
            <div class="armor-track"><div class="armor-fill barrier-fill" :style="{ width: magicBarrier + '%' }"></div></div>
            <span class="armor-val">{{ magicBarrier }}%</span>
          </div>
        </div>
      </FrostedPanel>

      <!-- Panel 3: Emergency Protocols -->
      <FrostedPanel title="紧急协议 / EMERGENCY PROTOCOLS" variant="terminal" class="sidebar-panel emergency-panel">
        <!-- Emergency Pull-back -->
        <div class="emergency-container">
          <!-- Protective cover -->
          <div v-if="!coverLifted" class="emergency-cover" @click="liftCover">
            <span class="cover-arrow">▲</span>
            <span class="cover-label">LIFT COVER</span>
          </div>
          <!-- The button -->
          <button class="emergency-button"
            :class="{ 'btn-activated': emergencyActivated }"
            :disabled="!coverLifted || emergencyActivated"
            @click="triggerEmergencyPullback">
            <span class="btn-icon">⚠</span>
            <span class="btn-text">{{ emergencyActivated ? 'PULL-BACK IN PROGRESS...' : 'EMERGENCY PULL-BACK' }}</span>
          </button>
          <transition name="flash">
            <div v-if="showFlash" class="emergency-flash"></div>
          </transition>
        </div>

        <!-- Toggle switches -->
        <div class="toggle-row">
          <span class="toggle-label">魔力过载保护</span>
          <label class="toggle-switch">
            <input type="checkbox" v-model="manaOverloadProtection" />
            <span class="toggle-slider" :class="{ 'slider-active': manaOverloadProtection }"></span>
          </label>
        </div>
        <div class="toggle-row">
          <span class="toggle-label">灵基供能切断</span>
          <label class="toggle-switch">
            <input type="checkbox" v-model="supplyCutoff" />
            <span class="toggle-slider" :class="{ 'slider-danger': supplyCutoff }"></span>
          </label>
        </div>

        <!-- Blackbox log -->
        <div class="blackbox-section">
          <div class="bb-header">// COMMS BLACKBOX //</div>
          <div class="blackbox-stream" ref="blackboxRef">
            <div v-for="(entry, i) in blackboxEntries" :key="i"
                 :class="['bb-entry', { 'bb-emergency': entry.emergency }]">
              <span class="bb-time">[{{ entry.time }}]</span>
              <span class="bb-msg">{{ entry.msg }}</span>
            </div>
          </div>
        </div>
      </FrostedPanel>
    </div>

  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted, nextTick } from 'vue'
import FrostedPanel from '../FrostedPanel.vue'
import { useTechnicsStore } from '../../stores/technics'

const store = useTechnicsStore()
const selectedCoffin = ref(null)

function selectCoffin(coffin) {
  selectedCoffin.value = selectedCoffin.value?.id === coffin.id ? null : coffin
}

const frozenCount = computed(() => 7)

const avgTemp = computed(() => {
  const temps = crypterCoffins.map(c => c.temperature)
  const sum = temps.reduce((a, b) => a + b, 0)
  return (sum / temps.length).toFixed(1) + '°C'
})

const crypterCoffins = [
  {
    id: 1, designation: 'COFFIN-01',
    occupantName: '基尔什塔利亚·沃代姆', occupantClass: 'A-Team · 队长',
    nameEn: 'Kirschtaria Wodime',
    temperature: -192.3, cryoStability: 94.2, spiritronSync: 98.7,
    suitability: 'A+',
    suitabilityDesc: '拟定A组总队长。',
    logistics: '时钟塔十二科之首"天体科"的主席领主继承人。根据天体科的硬核魔术测算，他是全方位无死角的绝对天才，具备极高顺位的星空测算魔力回路。在模拟战中，他展现出了近乎恐怖的指挥艺术。',
    cryoMonitor: '生命体征微弱但极其稳定，其强大的魔术回路正在冷舱中以极低的频率产生拟似共鸣。他是雷夫教授炸弹袭击中最核心的针对目标。',
  },
  {
    id: 2, designation: 'COFFIN-02',
    occupantName: '芥雏子', occupantClass: 'A-Team',
    nameEn: 'Hinako Akuta',
    temperature: -190.4, cryoStability: 91.8, spiritronSync: 95.2,
    suitability: 'A',
    suitabilityDesc: '植物科推荐。',
    logistics: '医疗部特招的灵子转移天才，自称是时钟塔植物科出身。不爱交际，历史测试成绩极其优秀，尤其精通中国古代史。但在常规体检中，她的多项生理指标（如血液活性、代谢周期）呈现出极其诡异的静止状态。',
    cryoMonitor: '极度异常。冷舱显示她的肉体几乎没有受到低温冻伤的负面影响，细胞活性在零下数十度依然维持在满额状态，医疗部甚至无法解析她肉体的深层结构。',
  },
  {
    id: 3, designation: 'COFFIN-03',
    occupantName: '卡多克·泽姆露普斯', occupantClass: 'A-Team',
    nameEn: 'Kadoc Zemlupus',
    temperature: -191.0, cryoStability: 82.1, spiritronSync: 78.6,
    suitability: 'A-',
    suitabilityDesc: '技术同步率极高。',
    logistics: '边缘魔术家族出身的平民。虽然纯粹的魔术回路数量在A组垫底，但他对灵子转移的"间隙"有着异乎常人的敏感度，在英灵精神同步率测试中拿到了迦勒底最高分。擅长细微的"兽性魔术"和咒符拆解。',
    cryoMonitor: '肉体受损严重。由于回路较为脆弱，雷夫教授的爆炸对他的肉体造成了毁灭性打击，目前全靠冷舱的灵子修复液勉强维持心跳。',
  },
  {
    id: 4, designation: 'COFFIN-04',
    occupantName: '奥菲莉娅·法姆索罗尼', occupantClass: 'A-Team',
    nameEn: 'Ophelia Phamrsolone',
    temperature: -189.7, cryoStability: 88.5, spiritronSync: 91.3,
    suitability: 'A',
    suitabilityDesc: '降灵科极密培养。',
    logistics: '时钟塔降灵科引以为傲的精英。她生来便拥有一只被时钟塔高度保密的特异器官——宝石级的"迁延之魔眼"。在硬核设定中，这只魔眼能够通过视线强行干涉可能性的走向，将概率锁定。',
    cryoMonitor: '魔眼部分已被迦勒底技术部实施最高级别的术式封印，防止其在无意识的弥留状态下释放概率扭曲，干扰冷舱整体的电力平衡。',
  },
  {
    id: 5, designation: 'COFFIN-05',
    occupantName: '斯堪的纳维亚·佩佩隆奇诺', occupantClass: 'A-Team',
    nameEn: 'Peperoncino',
    temperature: -188.9, cryoStability: 79.4, spiritronSync: 73.8,
    suitability: 'A',
    suitabilityDesc: '海外特招自由人。',
    logistics: '没有任何传统魔术名门背景的自由魔术师。虽然平时举止轻浮、自称大姐头，但保密局档案显示他极度危险。他精通神智学、修验道以及佛教古老暗杀术，具有断绝因果和操纵局部空间缝隙的硬核实战技巧。',
    cryoMonitor: '意志监控显示极其活跃。即使肉体在冷舱中被完全冻结，他的精神波形依然保持着某种类似"禅定"的深层呼吸律动，正在自主抵抗死亡。',
  },
  {
    id: 6, designation: 'COFFIN-06',
    occupantName: '贝利尔·古特', occupantClass: 'A-Team',
    nameEn: 'Beryl Gut',
    temperature: -187.6, cryoStability: 71.3, spiritronSync: 65.4,
    suitability: 'A',
    suitabilityDesc: '传授科特工。',
    logistics: '时钟塔传授科出身，外号"狼人"。在迦勒底的评测中，他是纯粹的战斗与诅咒术专家。他不具备宏大的魔术理论，但极其擅长利用非人混血的特异体质将魔力转化为致命的毒素与诅咒，是一个纯粹为了在绝境中撕裂敌人而准备的"脏活"执行者。',
    cryoMonitor: '脑波极其混乱，在冷冻状态下仍不时爆发出带有强烈攻击性的愉悦波形。技术部已对其冷舱加装了三重神经抑制剂。',
  },
  {
    id: 7, designation: 'COFFIN-07',
    occupantName: '戴维·布鲁布克', occupantClass: 'A-Team',
    nameEn: 'Daybit Sem Void',
    temperature: -193.1, cryoStability: 96.7, spiritronSync: 99.1,
    suitability: '无法测定',
    suitabilityDesc: '传承科异类。',
    logistics: '来自时钟塔最神秘的"传承科"。他是一个没有任何情感起伏的绝对理性怪物。此人在迦勒底的模拟测试中展现出了令人毛骨悚然的计算力——他能以极低阶的术式构筑，通过绝对精准的战术推演，在模拟战中完美反杀高位神秘。',
    cryoMonitor: '他的冷舱是整个管制室里最安静的一具。无论是心率、脑电波还是魔力逸散度，全部精准地卡在死亡线之上的1%处，就像一个精准闭合的高精密机械，静静地等待着复苏的指令。',
  },
]

function stabilityColor(val) {
  if (val > 80) return '#4cd964'
  if (val > 60) return '#ffcc00'
  return '#ff4444'
}

function syncRateClass(val) {
  if (val > 90) return 'sync-green'
  if (val > 70) return 'sync-yellow'
  return 'sync-red'
}

// --- Biometrics state (from store refs) ---
const spiritronSyncRate = computed(() => store.spiritronSyncRate)
const manaLoad = computed(() => store.manaLoad)
const manaSpikes = computed(() => store.manaSpikes)
const cabinPressure = computed(() => store.cabinPressure)
const o2Level = computed(() => store.o2Level)
const nutrientLevel = computed(() => store.nutrientLevel)
const hullDurability = computed(() => store.hullDurability)
const magicBarrier = computed(() => store.magicBarrier)

// --- Sync ring ---
const syncRingStyle = computed(() => {
  const rate = spiritronSyncRate.value
  const deg = (rate / 100) * 360
  let color
  if (rate >= 90) color = '#4cd964'
  else if (rate >= 60) color = '#ffcc00'
  else color = '#ff4444'
  return {
    background: `conic-gradient(${color} 0deg, ${color} ${deg}deg, rgba(255,255,255,0.06) ${deg}deg, rgba(255,255,255,0.06) 360deg)`,
  }
})

const syncColorClass = computed(() => {
  const rate = spiritronSyncRate.value
  if (rate >= 90) return 'sync-green'
  if (rate >= 60) return 'sync-yellow'
  return 'sync-red'
})

const manaColorClass = computed(() => {
  const m = manaLoad.value
  if (m < 50) return 'mana-green'
  if (m < 80) return 'mana-yellow'
  return 'mana-red'
})

// --- Emergency protocols ---
const coverLifted = ref(false)
const emergencyActivated = ref(false)
const showFlash = ref(false)
const manaOverloadProtection = ref(true)
const supplyCutoff = ref(false)
const blackboxEntries = ref([
  { time: '--:--:--', msg: 'Blackbox recorder initialized', emergency: false },
  { time: '--:--:--', msg: 'System self-diagnostic PASSED', emergency: false },
  { time: '--:--:--', msg: 'Spiritron communication link established', emergency: false },
  { time: '--:--:--', msg: 'Coffin integrity network nominal', emergency: false },
  { time: '--:--:--', msg: 'Waiting for rayshift orders...', emergency: false },
])

function addBBEntry(msg, emergency = false) {
  const now = new Date()
  const time = now.toLocaleTimeString('ja-JP', { hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false })
  blackboxEntries.value.unshift({ time, msg, emergency })
  if (blackboxEntries.value.length > 15) blackboxEntries.value.pop()
}

function liftCover() {
  coverLifted.value = true
}

function triggerEmergencyPullback() {
  if (!coverLifted.value || emergencyActivated.value) return
  emergencyActivated.value = true
  showFlash.value = true
  addBBEntry('⚠ EMERGENCY PULL-BACK ACTIVATED', true)

  setTimeout(() => { showFlash.value = false }, 600)

  setTimeout(() => {
    emergencyActivated.value = false
    coverLifted.value = false
    addBBEntry('SYSTEM: Pull-back cycle complete. Cover re-engaged.')
  }, 3000)
}

// --- ECG canvas ---
const ecgCanvasRef = ref(null)
const ecgBpm = ref(72)
let ecgAnimId = null
let ecgPhase = 0

function drawECG() {
  const canvas = ecgCanvasRef.value
  if (!canvas) { ecgAnimId = requestAnimationFrame(drawECG); return }

  const rect = canvas.getBoundingClientRect()
  const dpr = window.devicePixelRatio || 1
  canvas.width = rect.width * dpr
  canvas.height = rect.height * dpr
  const ctx = canvas.getContext('2d')
  ctx.scale(dpr, dpr)
  const w = rect.width
  const h = rect.height
  const midY = h / 2

  ctx.clearRect(0, 0, w, h)

  // Grid
  ctx.strokeStyle = 'rgba(87,150,208,0.06)'
  ctx.lineWidth = 0.5
  for (let x = 0; x < w; x += 25) { ctx.beginPath(); ctx.moveTo(x, 0); ctx.lineTo(x, h); ctx.stroke() }
  for (let y = 0; y < h; y += 25) { ctx.beginPath(); ctx.moveTo(0, y); ctx.lineTo(w, y); ctx.stroke() }

  // Baseline
  ctx.strokeStyle = 'rgba(87,150,208,0.08)'
  ctx.beginPath(); ctx.moveTo(0, midY); ctx.lineTo(w, midY); ctx.stroke()

  // ECG waveform
  ctx.strokeStyle = '#4cd964'
  ctx.lineWidth = 1.5
  ctx.shadowBlur = 4
  ctx.shadowColor = '#4cd964'
  ctx.beginPath()

  for (let x = 0; x < w; x++) {
    const t = (x / w) * 4 + ecgPhase
    const cyclePos = t % 1
    let y = midY

    if (cyclePos < 0.05) { y = midY }
    else if (cyclePos < 0.12) { y = midY - 4 * Math.sin((cyclePos - 0.05) / 0.07 * Math.PI) }
    else if (cyclePos < 0.30) { y = midY }
    else if (cyclePos < 0.33) { y = midY - 28 * Math.sin((cyclePos - 0.30) / 0.03 * Math.PI) }
    else if (cyclePos < 0.36) { y = midY + 5 * Math.sin((cyclePos - 0.33) / 0.03 * Math.PI) }
    else if (cyclePos < 0.40) { y = midY - 22 * Math.sin((cyclePos - 0.36) / 0.04 * Math.PI) }
    else if (cyclePos < 0.60) { y = midY }
    else if (cyclePos < 0.72) { y = midY + 3 * Math.sin((cyclePos - 0.60) / 0.12 * Math.PI) }
    else { y = midY }

    y += (Math.random() - 0.5) * 0.4
    if (x === 0) ctx.moveTo(x, y); else ctx.lineTo(x, y)
  }
  ctx.stroke()

  // EEG overlay
  ctx.strokeStyle = 'rgba(87,150,208,0.2)'
  ctx.lineWidth = 0.8
  ctx.shadowBlur = 0
  ctx.beginPath()
  for (let x = 0; x < w; x++) {
    const t = (x / w) * 8 + ecgPhase * 1.3
    const y = midY + Math.sin(t * 5) * 2 + Math.sin(t * 13) * 1.5 + Math.sin(t * 31) * 0.8
    if (x === 0) ctx.moveTo(x, y); else ctx.lineTo(x, y)
  }
  ctx.stroke()

  ecgPhase += 0.008
  ecgAnimId = requestAnimationFrame(drawECG)
}

// --- Helpers ---
function isSpikeActive(spike) { return Date.now() - spike.time < 200 }

// --- Lifecycle ---
let bpmInterval = null

onMounted(() => {
  store.startBiometricSimulation()

  // Start ECG
  nextTick(() => { ecgAnimId = requestAnimationFrame(drawECG) })

  // BPM jitter
  bpmInterval = setInterval(() => {
    ecgBpm.value = Math.max(60, Math.min(90, ecgBpm.value + (Math.random() - 0.5) * 3))
  }, 3000)
})

onUnmounted(() => {
  store.stopBiometricSimulation()
  if (ecgAnimId) cancelAnimationFrame(ecgAnimId)
  if (bpmInterval) clearInterval(bpmInterval)
})
</script>

<style scoped>
/* ============================================
   COFFIN ROOM — 2-Column Layout
   ============================================ */
.coffin-room {
  display: flex;
  gap: 10px;
  height: 100%;
  position: relative;
  overflow: auto;
}
.main-chamber { flex: 3; min-width: 0; }
.diagnostics-sidebar {
  flex: 0 0 340px;
  display: flex;
  flex-direction: column;
  gap: 8px;
  overflow-y: auto;
  overflow-x: hidden;
  max-height: 100%;
  padding-right: 2px;
}
.diagnostics-sidebar::-webkit-scrollbar { width: 2px; }
.diagnostics-sidebar::-webkit-scrollbar-thumb { background: rgba(87,150,208,0.3); }
.sidebar-panel { flex-shrink: 0; }
.emergency-panel { flex-shrink: 0; }

/* ===== Chamber Room ===== */
.chamber-room {
  position: relative;
  background: radial-gradient(ellipse at 50% 0%, rgba(87,150,208,0.04) 0%, transparent 70%), linear-gradient(180deg, rgba(0,0,0,0.2) 0%, transparent 20%);
  border: 1px solid rgba(255,255,255,0.04); padding: 10px; overflow-y: auto;
}

/* ===== Crypter Grid ===== */
.crypter-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 6px;
}

.coffin-cell {
  border: 1px solid rgba(87,150,208,0.2);
  cursor: pointer; transition: all 0.25s ease;
  display: flex; flex-direction: column; align-items: center;
  min-height: 100px; position: relative; overflow: hidden;
  padding: 8px 4px 6px; border-radius: 4px;
  background: linear-gradient(180deg, rgba(87,150,208,0.1) 0%, rgba(87,150,208,0.03) 100%);
  box-shadow: inset 0 0 16px rgba(87,150,208,0.06);
}
.coffin-cell:hover {
  border-color: rgba(87,150,208,0.5);
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(0,0,0,0.4), inset 0 0 16px rgba(87,150,208,0.06);
}
.coffin-cell.cell-selected {
  border-color: #5796D0;
  box-shadow: 0 0 16px rgba(87,150,208,0.3), inset 0 0 20px rgba(87,150,208,0.08);
}

.pod-glass { position: absolute; inset: 0; background: linear-gradient(180deg, rgba(255,255,255,0.03) 0%, transparent 40%, rgba(0,0,0,0.05) 100%); pointer-events: none; }
.pod-glare { position: absolute; top: 2px; left: 4px; width: 30%; height: 5px; background: linear-gradient(90deg, rgba(255,255,255,0.08), transparent); border-radius: 50%; pointer-events: none; }

.cryo-mist { position: absolute; inset: 0; pointer-events: none; background: radial-gradient(ellipse at 30% 40%, rgba(200,230,255,0.15) 0%, transparent 60%), radial-gradient(ellipse at 70% 60%, rgba(200,230,255,0.1) 0%, transparent 50%), linear-gradient(180deg, rgba(200,240,255,0.06) 0%, rgba(200,240,255,0.03) 40%, transparent 70%); animation: cryo-drift 4s ease-in-out infinite alternate; }
@keyframes cryo-drift { 0%{opacity:0.3;transform:translateY(0) scale(1)} 50%{opacity:0.7;transform:translateY(-1px) scale(1.02)} 100%{opacity:0.3;transform:translateY(1px) scale(0.98)} }

.pod-indicator { width: 6px; height: 6px; border-radius: 50%; margin-bottom: 3px; flex-shrink: 0; }
.ind-frozen { background: #5796D0; box-shadow: 0 0 6px rgba(87,150,208,0.5); }

.cell-id { font-family:'JetBrains Mono',monospace; font-size: 7px; color: rgba(87,150,208,0.4); letter-spacing: 0.5px; position: relative; z-index: 1; }
.cell-occupant { font-size: 10px; color: rgba(255,255,255,0.7); margin-top: 3px; text-align: center; line-height: 1.3; position: relative; z-index: 1; font-weight: 500; }
.cell-class { font-size: 7px; font-family:'JetBrains Mono',monospace; color: rgba(255,255,255,0.25); margin-top: 1px; letter-spacing: 1px; position: relative; z-index: 1; }
.cell-temp { font-size: 11px; font-family:'JetBrains Mono',monospace; color: #5796D0; margin-top: 4px; position: relative; z-index: 1; text-shadow: 0 0 8px rgba(87,150,208,0.2); }
.cell-stability { width: 80%; margin-top: 4px; height: 3px; position: relative; z-index: 1; }
.stability-track { height: 3px; background: rgba(255,255,255,0.06); overflow: hidden; }
.stability-fill { height: 100%; transition: width 0.5s ease; }
.cell-sync-rate { font-size: 8px; font-family:'JetBrains Mono',monospace; margin-top: 3px; position: relative; z-index: 1; font-weight: 600; }

/* ===== Biometrics Panel ===== */
.bios-grid { display: flex; gap: 8px; margin-bottom: 8px; }

/* Sync Ring */
.sync-section { flex: 1; display: flex; flex-direction: column; align-items: center; gap: 4px; }
.sync-label { font-size: 7px; font-family:'JetBrains Mono',monospace; color: rgba(255,255,255,0.3); letter-spacing: 1px; display: block; }
.sync-ring-wrap { position: relative; }
.sync-ring {
  width: 72px; height: 72px; border-radius: 50%;
  mask: radial-gradient(circle, transparent 55%, #000 56%);
  -webkit-mask: radial-gradient(circle, transparent 55%, #000 56%);
}
.sync-ring-hole {
  position: absolute; top: 50%; left: 50%;
  transform: translate(-50%, -50%);
  display: flex; align-items: center; justify-content: center;
}
.sync-value { font-size: 12px; font-family:'JetBrains Mono',monospace; font-weight: 600; }
.sync-green { color: #4cd964; }
.sync-yellow { color: #ffcc00; }
.sync-red { color: #ff4444; }

/* Mana Load */
.mana-section { flex: 1; display: flex; flex-direction: column; gap: 3px; }
.mana-value-row { text-align: center; }
.mana-pct { font-size: 18px; font-family:'JetBrains Mono',monospace; font-weight: 600; }
.mana-green { color: #4cd964; }
.mana-yellow { color: #ffcc00; }
.mana-red { color: #ff4444; }
.mana-track { height: 8px; background: rgba(255,255,255,0.06); border: 1px solid rgba(255,255,255,0.08); position: relative; overflow: visible; }
.mana-fill { height: 100%; background: linear-gradient(90deg, #4cd964 0%, #ffcc00 60%, #ff4444 85%); transition: width 0.5s cubic-bezier(0.4,0,0.2,1); }
.mana-spike { position: absolute; top: -4px; width: 2px; height: 16px; background: #ff4444; box-shadow: 0 0 6px rgba(255,68,68,0.6); opacity: 0; transition: opacity 0.3s; }
.mana-spike.spike-active { opacity: 1; }
.mana-labels { display: flex; justify-content: space-between; font-size: 6px; font-family:'JetBrains Mono',monospace; color: rgba(255,255,255,0.15); }

/* ECG */
.ecg-section { margin-top: 2px; }
.ecg-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 3px; }
.ecg-bpm { font-size: 9px; font-family:'JetBrains Mono',monospace; color: #4cd964; }
.ecg-canvas-wrap { position: relative; }
.ecg-canvas { width: 100%; height: 64px; background: rgba(0,0,0,0.25); border: 1px solid rgba(255,255,255,0.06); display: block; }
.ecg-overlay-labels { position: absolute; left: 4px; top: 4px; display: flex; gap: 6px; }
.ecg-label-lead { font-size: 6px; font-family:'JetBrains Mono',monospace; }
.lead-i { color: #4cd964; }
.lead-ii { color: rgba(87,150,208,0.3); }
.lead-eeg { color: rgba(87,150,208,0.15); }

/* ===== Micro-Environment Panel ===== */
.cabin-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 3px; margin-bottom: 8px; }
.cabin-cell { border: 1px solid rgba(255,255,255,0.04); padding: 4px 6px; background: rgba(0,0,0,0.1); }
.cabin-label { font-size: 7px; font-family:'JetBrains Mono',monospace; color: rgba(255,255,255,0.3); display: block; letter-spacing: 0.5px; }
.cabin-value { font-size: 12px; font-family:'JetBrains Mono',monospace; color: #5796D0; letter-spacing: 0.5px; }

/* Cryo */
.cryo-section { margin-bottom: 8px; }
.cryo-header { margin-bottom: 4px; }
.cryo-block { display: flex; align-items: center; gap: 10px; padding: 6px; border: 1px solid rgba(87,150,208,0.15); background: linear-gradient(180deg, rgba(87,150,208,0.06), transparent); }
.cryo-temp-display { font-size: 24px; font-family:'JetBrains Mono',monospace; color: #5796D0; text-shadow: 0 0 12px rgba(87,150,208,0.3); letter-spacing: 1px; }
.cryo-info { display: flex; flex-direction: column; gap: 2px; }
.cryo-info span { font-size: 7px; font-family:'JetBrains Mono',monospace; color: rgba(255,255,255,0.3); }
.cryo-count { color: #5796D0 !important; font-size: 8px !important; }

/* Armor */
.armor-section { display: flex; flex-direction: column; gap: 4px; }
.armor-row { display: flex; align-items: center; gap: 6px; }
.armor-track { flex: 1; height: 5px; background: rgba(255,255,255,0.06); border: 1px solid rgba(255,255,255,0.08); overflow: hidden; }
.armor-fill { height: 100%; background: linear-gradient(90deg, #4cd964, #ffcc00, #ff4444); transition: width 0.8s ease; }
.armor-fill.barrier-fill { background: linear-gradient(90deg, #5796D0, #7bb3e0, #5796D0); }
.armor-val { font-size: 8px; font-family:'JetBrains Mono',monospace; color: rgba(255,255,255,0.5); width: 30px; text-align: right; }

/* ===== Emergency Protocols Panel ===== */
.emergency-container { position: relative; height: 60px; display: flex; align-items: center; justify-content: center; margin-bottom: 6px; }

/* Protective cover */
.emergency-cover { position: absolute; z-index: 2; inset: 0; background: linear-gradient(180deg, rgba(255,68,68,0.12), rgba(255,68,68,0.04)); border: 2px solid rgba(255,68,68,0.25); cursor: pointer; display: flex; flex-direction: column; align-items: center; justify-content: center; gap: 2px; transition: all 0.35s cubic-bezier(0.4,0,0.2,1); user-select: none; }
.emergency-cover:hover { border-color: rgba(255,68,68,0.5); background: linear-gradient(180deg, rgba(255,68,68,0.2), rgba(255,68,68,0.06)); }
.cover-arrow { font-size: 12px; color: rgba(255,68,68,0.6); animation: cover-bounce 1s ease-in-out infinite; }
.cover-label { font-family:'JetBrains Mono',monospace; font-size: 7px; color: rgba(255,68,68,0.5); letter-spacing: 2px; }
@keyframes cover-bounce { 0%,100%{transform:translateY(0)} 50%{transform:translateY(-3px)} }

/* Emergency button */
.emergency-button { width: 95%; height: 48px; background: linear-gradient(180deg, #cc2222, #881111); border: 2px solid rgba(255,68,68,0.3); color: white; font-family:'JetBrains Mono',monospace; font-size: 8px; letter-spacing: 2px; cursor: pointer; display: flex; align-items: center; justify-content: center; gap: 6px; box-shadow: 0 0 16px rgba(255,68,68,0.15); transition: all 0.25s; }
.emergency-button:disabled { opacity: 0.2; cursor: default; box-shadow: none; }
.emergency-button:not(:disabled):hover { box-shadow: 0 0 30px rgba(255,68,68,0.4); border-color: rgba(255,68,68,0.6); transform: scale(1.03); }
.btn-activated { animation: emergency-pulse 0.4s ease-in-out infinite; }
@keyframes emergency-pulse { 0%,100%{box-shadow:0 0 20px rgba(255,68,68,0.4)} 50%{box-shadow:0 0 50px rgba(255,68,68,0.8)} }
.emergency-flash { position: absolute; inset: 0; background: rgba(255,68,68,0.15); pointer-events: none; }
.flash-enter-active { transition: opacity 0.1s; }
.flash-leave-active { transition: opacity 0.4s; }
.flash-enter-from, .flash-leave-to { opacity: 0; }

/* Toggle switches */
.toggle-row { display: flex; justify-content: space-between; align-items: center; padding: 3px 0; }
.toggle-label { font-size: 8px; font-family:'JetBrains Mono',monospace; color: rgba(255,255,255,0.4); }
.toggle-switch { width: 32px; height: 16px; position: relative; display: inline-block; }
.toggle-switch input { display: none; }
.toggle-slider { position: absolute; inset: 0; background: rgba(255,255,255,0.08); cursor: pointer; transition: 0.3s; border: 1px solid rgba(255,255,255,0.1); }
.toggle-slider::before { content: ''; position: absolute; width: 10px; height: 10px; left: 2px; top: 2px; background: rgba(255,255,255,0.4); transition: 0.3s; }
input:checked + .toggle-slider.slider-active { background: #4cd964; border-color: #4cd964; }
input:checked + .toggle-slider.slider-danger { background: #ff4444; border-color: #ff4444; }
input:checked + .toggle-slider::before { transform: translateX(16px); background: white; }

/* Blackbox */
.blackbox-section { margin-top: 6px; }
.bb-header { font-size: 7px; font-family:'JetBrains Mono',monospace; letter-spacing: 2px; color: rgba(87,150,208,0.4); margin-bottom: 3px; }
.blackbox-stream { height: 72px; overflow-y: auto; background: rgba(0,0,0,0.2); border: 1px solid rgba(255,255,255,0.04); padding: 3px 4px; }
.blackbox-stream::-webkit-scrollbar { width: 2px; }
.blackbox-stream::-webkit-scrollbar-thumb { background: rgba(87,150,208,0.3); }
.bb-entry { font-size: 6px; font-family:'JetBrains Mono',monospace; color: rgba(255,255,255,0.35); padding: 1px 0; border-bottom: 1px solid rgba(255,255,255,0.02); }
.bb-time { color: rgba(255,255,255,0.15); margin-right: 4px; }
.bb-emergency { color: #ff4444; animation: blink 0.5s step-end infinite; }
@keyframes blink { 0%,100%{opacity:1} 50%{opacity:0.2} }

/* ===== Master Info Panel ===== */
.master-info-panel {
  margin-top: 10px;
  border: 1px solid rgba(87, 150, 208, 0.2);
  background: rgba(6, 18, 42, 0.95);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
.info-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 14px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
  flex-shrink: 0;
}
.info-title-row {
  display: flex;
  align-items: center;
  gap: 8px;
}
.info-badge {
  font-size: 9px;
  font-family: 'JetBrains Mono', monospace;
  color: rgba(87, 150, 208, 0.7);
  border: 1px solid rgba(87, 150, 208, 0.2);
  padding: 1px 5px;
}
.info-name {
  font-size: 13px;
  font-weight: 600;
  margin: 0;
  color: rgb(255, 255, 255);
}
.info-name-en {
  font-size: 10px;
  color: rgba(255, 255, 255, 0.3);
  font-family: 'Georgia', serif;
}
.info-close {
  background: none;
  border: 1px solid transparent;
  color: rgba(255, 255, 255, 0.2);
  cursor: pointer;
  font-size: 12px;
  padding: 2px 6px;
  transition: all 0.15s;
}
.info-close:hover {
  color: rgb(255, 255, 255);
  border-color: rgba(255, 255, 255, 0.15);
}
.info-divider {
  height: 1px;
  background: rgba(87, 150, 208, 0.1);
  flex-shrink: 0;
}
.info-body {
  padding: 10px 14px 8px;
  flex: 1;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.info-section {
  margin-bottom: 0;
}
.info-section-header {
  display: flex;
  align-items: center;
  gap: 5px;
  margin-bottom: 3px;
}
.info-section-icon {
  font-size: 7px;
  color: rgba(87, 150, 208, 0.5);
}
.info-section-label {
  font-size: 9px;
  color: rgba(87, 150, 208, 0.7);
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  letter-spacing: 1px;
  font-weight: 500;
}
.info-rating {
  margin-left: auto;
  font-size: 10px;
  font-weight: 600;
  color: #fbbf24;
  font-family: 'JetBrains Mono', monospace;
  border: 1px solid rgba(251, 191, 36, 0.2);
  padding: 0 6px;
}
.info-text {
  font-size: 10.5px;
  color: rgba(255, 255, 255, 0.5);
  line-height: 1.7;
  margin: 0;
  letter-spacing: 0.2px;
}
.info-footer {
  display: flex;
  justify-content: space-between;
  padding: 6px 14px;
  border-top: 1px solid rgba(255, 255, 255, 0.04);
  flex-shrink: 0;
}
.info-footer-line {
  font-size: 7px;
  color: rgba(255, 255, 255, 0.1);
  font-family: 'JetBrains Mono', monospace;
  letter-spacing: 1px;
}

/* Overlay fade transition */
.info-fade-enter-active,
.info-fade-leave-active {
  transition: all 0.25s ease;
}
.info-fade-enter-from,
.info-fade-leave-to {
  opacity: 0;
  transform: scale(0.97);
}

/* ===== Responsive ===== */
@media (max-width: 1000px) {
  .coffin-room { flex-direction: column; overflow: auto; }
  .diagnostics-sidebar { flex: none; flex-direction: row; flex-wrap: wrap; width: 100%; max-height: none; overflow: visible; }
  .diagnostics-sidebar .sidebar-panel { flex: 1; min-width: 240px; }
  .crypter-grid { grid-template-columns: repeat(auto-fill, minmax(100px, 1fr)); }
}
</style>
