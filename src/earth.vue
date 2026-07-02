<template>
  <div class="chaldea-container">
    <!-- CRT scanline overlay -->
    <div class="scanline-overlay"></div>
    <!-- 加载屏幕 -->
    <Transition name="fade">
      <div v-if="loading" class="loading-screen">
        <div class="loading-content">
          <div class="chaldea-logo-anim">
            <div class="logo-outer-ring"></div>
            <div class="logo-inner-ring"></div>
            <span class="logo-symbol">✧</span>
          </div>
          <div class="loading-bar-track">
            <div class="loading-bar-fill" :style="{ width: loadPct + '%' }"></div>
          </div>
          <p class="loading-label">{{ loadLabel }}</p>
        </div>
      </div>
    </Transition>

    <!-- 3D 地球 -->
    <div ref="earthRef" class="earth-canvas"></div>

    <!-- 顶部栏 -->
    <header class="top-bar">
      <div class="tb-left">
        <span class="tb-icon">✦</span>
        <span class="tb-title">人理保障机构 CHALDEA</span>
      </div>
      <div class="tb-center">
        <span class="tb-clock">{{ clockStr }}</span>
        <span class="tb-date">{{ dateStr }}</span>
      </div>
      <div class="tb-right">
        <div class="tb-sq">
          <span class="sq-icon">◇</span>
          <span class="sq-num">{{ sq }}</span>
        </div>
        <div class="tb-fp">
          <span class="fp-icon">●</span>
          <span class="fp-num">{{ fp.toLocaleString() }}</span>
        </div>
        <div class="tb-auth">
          <template v-if="authStore.isAuthenticated">
            <router-link to="/sandbox" class="tb-login-btn">终端 ▼</router-link>
          </template>
          <template v-else>
            <router-link to="/login" class="tb-login-btn">LOGIN</router-link>
          </template>
        </div>
      </div>
    </header>

    <!-- 左侧面板 -->
    <aside class="panel left-panel">
      <div class="pnl-hdr">
        <h2>■ 观测报告</h2>
        <div class="pnl-line"></div>
      </div>

      <div class="pnl-grid">
        <div class="pnl-cell">
          <span class="pcell-lbl">系统状态</span>
          <span class="pcell-val alive">● ACTIVE</span>
        </div>
        <div class="pnl-cell">
          <span class="pcell-lbl">探测到特异点</span>
          <span class="pcell-val warn">● {{ singularityList.length }}</span>
        </div>
        <div class="pnl-cell">
          <span class="pcell-lbl">人理奠基值</span>
          <span class="pcell-val danger">● {{ pct }}%</span>
        </div>
      </div>

      <div class="pnl-divider"></div>
      <div class="pnl-subtitle"><span class="sub-icon">⟐</span> 特异点列表</div>

      <ul class="singu-list">
        <li v-for="(s, i) in singularityList" :key="s.id"
            :class="['singu-item', { active: activeIdx === i }]"
            @click="focusSingu(i)">
          <span class="s-dot" :style="{ background: s.color, boxShadow: '0 0 8px ' + s.color }"></span>
          <span class="s-num">{{ String(i + 1).padStart(2, '0') }}</span>
          <span class="s-name">{{ s.name }}</span>
          <span class="s-era">{{ s.era }}</span>
        </li>
      </ul>
    </aside>

    <!-- 右侧面板 -->
    <aside class="panel right-panel">
      <div class="pnl-hdr">
        <h3>■ 灵子转移状态</h3>
        <div class="pnl-line"></div>
      </div>

      <div class="sys-rows">
        <div class="sys-row">
          <span>反召唤系统</span>
          <span class="ok">NOMINAL</span>
        </div>
        <div class="sys-row">
          <span>灵子演算装置</span>
          <span class="ok">STANDBY</span>
        </div>
        <div class="sys-row">
          <span>魔力供给</span>
          <span :class="manaCls">{{ manaTxt }}</span>
        </div>
        <div class="sys-row">
          <span>时空稳定率</span>
          <span :class="stabCls">{{ stab }}%</span>
        </div>
      </div>

      <div class="pnl-divider"></div>
      <div class="pnl-subtitle"><span class="sub-icon">⏳</span> 御主适应系数</div>

      <div class="master-bars">
        <div v-for="m in masters" :key="m.name" class="mbar">
          <div class="mbar-hdr">
            <span>{{ m.name }}</span>
            <span>{{ m.rate }}%</span>
          </div>
          <div class="mbar-track">
            <div class="mbar-fill" :style="{ width: m.rate + '%', background: mbarColor(m.rate) }"></div>
          </div>
        </div>
      </div>

      <div class="pnl-divider"></div>
      <div class="pnl-subtitle"><span class="sub-icon">◈</span> 数据流</div>
      <div class="datastream">
        <p v-for="(line, i) in streamLines" :key="i" class="stream-line">{{ line }}</p>
      </div>
    </aside>

    <!-- 令呪 -->
    <div class="seals-bar">
      <div v-for="i in 3" :key="i"
           :class="['seal', { dead: i > seals }]"
           @click="i <= seals && (seals = i - 1)">
        <svg viewBox="0 0 100 96">
          <path d="M50 4 L62 36 L96 36 L70 58 L80 92 L50 72 L20 92 L30 58 L4 36 L38 36Z"
                :fill="i <= seals ? '#ff3b30' : 'rgba(255,59,48,0.1)'"
                :stroke="i <= seals ? '#ff3b30' : 'rgba(255,59,48,0.2)'"
                stroke-width="2.5" />
        </svg>
      </div>
      <span class="seals-label">令呪</span>
    </div>

    <!-- 灵子转移按钮 -->
    <button class="rayshift-btn" @click="doRayshift" :disabled="rayshifting">
      <span class="rb-main">{{ rayshifting ? '转移中...' : '灵子转移' }}</span>
      <span class="rb-sub">RAYSIGHT DIVERSION</span>
    </button>

  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed, nextTick } from 'vue'
import { useAuthStore } from './stores/auth'

import * as THREE from 'three'
import { OrbitControls } from 'three/addons/controls/OrbitControls.js'
import { CSS2DRenderer, CSS2DObject } from 'three/addons/renderers/CSS2DRenderer.js'
import { Line2 } from 'three/addons/lines/Line2.js'
import { LineGeometry } from 'three/addons/lines/LineGeometry.js'
import { LineMaterial } from 'three/addons/lines/LineMaterial.js'
import { feature } from 'topojson-client'

const authStore = useAuthStore()

/* ============================
   状态
   ============================ */
const earthRef = ref(null)

// Three.js
let scene = null
let camera = null
let renderer = null
let labelRenderer = null
let controls = null
let globe = null
let lineMat = null
let animationId = null
let labelObjects = []

let animTimer = null
let streamTimer = null

// 加载
const loading = ref(true)
const loadPct = ref(0)
const loadLabel = ref('正在启动反召唤系统...')
const stages = [
  '正在启动反召唤系统...',
  '灵子演算装置初始化...',
  '同步迦勒底亚斯...',
  '校准时空坐标...',
  '系统就绪。',
]

// 时钟
const clockStr = ref('')
const dateStr = ref('')
const tick = () => {
  const d = new Date()
  clockStr.value = d.toLocaleTimeString('zh-CN', { hour12: false })
  dateStr.value = d.toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' })
}

const sq = ref(999)
const fp = ref(284750)

const singularityList = ref([
  { id: 1,  name: '特异点F: 冬木 (A.D.2004)',       era: 'AD 2004', lng: 90, lat: 10.44, color: '#ff6b35' },
  { id: 2,  name: '第一特异点: 奥尔良 (A.D.1431)',    era: 'AD 1431', lng: 1.90,   lat: 47.90, color: '#ff4500' },
  { id: 3,  name: '第二特异点: 七丘之城 (A.D.0060)',  era: 'AD 0060', lng: 12.49,  lat: 41.90, color: '#ffaa00' },
  { id: 4,  name: '第三特异点: 俄刻阿诺斯 (A.D.1573)',era: 'AD 1573', lng: -70.00, lat: 21.00, color: '#00bfff' },
  { id: 5,  name: '第四特异点: 伦敦 (A.D.1888)',      era: 'AD 1888', lng: -0.12,  lat: 51.50, color: '#9b59b6' },
  { id: 6,  name: '第五特异点: 北美 (A.D.1783)',      era: 'AD 1783', lng: -77.03, lat: 38.90, color: '#2ecc71' },
  { id: 7,  name: '第七特异点: 巴比伦尼亚 (B.C.2655)',era: 'BC 2655', lng: 44.40,  lat: 32.50, color: '#e74c3c' },
])
const activeIdx = ref(-1)

const pct = computed(() => Math.max(0, 100 - singularityList.value.length * 14))

// 令呪
const seals = ref(3)

// 御主数据
const masters = ref([
  { name: '藤丸立香',  rate: 97 },
  { name: '玛修·基列莱特', rate: 100 },
  { name: '达·芬奇',   rate: 72 },
  { name: '罗马尼·阿基曼', rate: 48 },
])

// 系统状态
const mana = ref(88)
const manaTxt = computed(() => mana.value > 60 ? 'OK' : mana.value > 30 ? 'CAUTION' : 'LOW')
const manaCls = computed(() => mana.value > 60 ? 'ok' : mana.value > 30 ? 'warn' : 'danger')
const stab = ref(97)
const stabCls = computed(() => stab.value > 80 ? 'ok' : 'warn')

// 数据流
const streamLines = ref([
  '> 正在检索时空异常系数...',
  '> 灵子转移进入待机状态',
  '> 反召唤系统：NOMINAL',
])

// 灵子转移
const rayshifting = ref(false)

/* ============================
   经纬度 → Three.js Vector3
   ============================ */
function latLonToVector3(lat, lon, radius) {
  const phi = (90 - lat) * Math.PI / 180
  const theta = (lon + 180) * Math.PI / 180
  return new THREE.Vector3(
    -radius * Math.sin(phi) * Math.sin(theta),
    radius * Math.cos(phi),
    radius * Math.sin(phi) * Math.cos(theta)
  )
}

/* ============================
   加载动画
   ============================ */
function runLoading() {
  let i = 0
  const step = () => {
    if (i >= stages.length) {
      loading.value = false
      nextTick(() => initEarth())
      return
    }
    loadLabel.value = stages[i]
    const target = ((i + 1) / stages.length) * 100
    let p = (i / stages.length) * 100
    const dur = setInterval(() => {
      p += 2
      loadPct.value = Math.min(p, target)
      if (p >= target) { clearInterval(dur); i++; setTimeout(step, 300) }
    }, 30)
  }
  step()
}

/* ============================
   初始化 Three.js 地球场景
   ============================ */
function initEarth() {
  if (!earthRef.value) return

  // === 场景 / 相机 / 渲染器 ===
  scene = new THREE.Scene()
  camera = new THREE.PerspectiveCamera(45, 1, 0.1, 1000)
  // 相机初始视角看向第二特异点（七丘之城/罗马）
  const camPos = latLonToVector3(41.90, 12.49, 2.8)
  camera.position.copy(camPos)
  camera.lookAt(0, 0, 0)

  // WebGL 渲染器
  const container = earthRef.value
  const w = container.clientWidth
  const h = container.clientHeight
  camera.aspect = w / h
  camera.updateProjectionMatrix()
  renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true })
  renderer.setSize(w, h)
  renderer.setPixelRatio(Math.max(window.devicePixelRatio || 1, 2))
  renderer.domElement.style.display = 'block'
  renderer.domElement.style.position = 'absolute'
  renderer.domElement.style.top = '0'
  renderer.domElement.style.left = '0'
  container.appendChild(renderer.domElement)

  // CSS2D 标签渲染器
  labelRenderer = new CSS2DRenderer()
  labelRenderer.setSize(w, h)
  labelRenderer.domElement.style.position = 'absolute'
  labelRenderer.domElement.style.top = '0'
  labelRenderer.domElement.style.left = '0'
  labelRenderer.domElement.style.pointerEvents = 'none'
  container.appendChild(labelRenderer.domElement)

  // 控制器
  controls = new OrbitControls(camera, renderer.domElement)
  controls.enableDamping = true
  controls.dampingFactor = 0.05
  controls.autoRotate = true
  controls.autoRotateSpeed = 0.5
  controls.enableZoom = true
  controls.minDistance = 1.5
  controls.maxDistance = 5

  // === 海洋球体 ===
  const globeGeometry = new THREE.SphereGeometry(1, 64, 64)
  const globeMaterial = new THREE.MeshPhongMaterial({
    color: 0x0a2f6a,
    emissive: 0x051840,
    shininess: 10,
    specular: new THREE.Color(0x1a5cff),
  })
  globe = new THREE.Mesh(globeGeometry, globeMaterial)
  scene.add(globe)

  // === 星空 ===
  const starsGeometry = new THREE.BufferGeometry()
  const starsCount = 50000
  const starsPositions = new Float32Array(starsCount * 3)
  for (let i = 0; i < starsCount * 3; i += 3) {
    const r = 50 + Math.random() * 50
    const theta = Math.random() * Math.PI * 2
    const phi = Math.acos(Math.random() * 2 - 1)
    starsPositions[i] = r * Math.sin(phi) * Math.cos(theta)
    starsPositions[i + 1] = r * Math.sin(phi) * Math.sin(theta)
    starsPositions[i + 2] = r * Math.cos(phi)
  }
  starsGeometry.setAttribute('position', new THREE.BufferAttribute(starsPositions, 3))
  const starsMaterial = new THREE.PointsMaterial({
    color: 0xffffff,
    size: 0.15,
    transparent: true,
    opacity: 0.8,
  })
  const stars = new THREE.Points(starsGeometry, starsMaterial)
  scene.add(stars)

  // === 灯光 ===
  const ambientLight = new THREE.AmbientLight(0x404060)
  scene.add(ambientLight)
  const directionalLight = new THREE.DirectionalLight(0xffffff, 1.5)
  directionalLight.position.set(5, 5, 5)
  scene.add(directionalLight)
  const backLight = new THREE.DirectionalLight(0x1a5cff, 0.5)
  backLight.position.set(-5, -3, -5)
  scene.add(backLight)

  // === 特异点标签 ===
  labelObjects = []
  singularityList.value.forEach(s => {
    const div = document.createElement('div')
    div.className = 'singularity-label'
    div.textContent = s.name

    const labelObj = new CSS2DObject(div)
    const pos = latLonToVector3(s.lat, s.lng, 1)
    labelObj.position.copy(pos)

    labelObjects.push({
      element: div,
      localPositions: pos.clone()
    })

    globe.add(labelObj)
  })

  // === 大陆轮廓 ===
  lineMat = new LineMaterial({
    color: 0x2663b5,
    linewidth: 2.5,
    transparent: true,
    opacity: 0.55,
    resolution: new THREE.Vector2(window.innerWidth, window.innerHeight),
    dashed: false,
  })

  const globeRadius = 1
  function drawThickPolygon(coordinates) {
    coordinates.forEach(ring => {
      if (ring.length < 5) return
      const positions = []
      ring.forEach(coord => {
        const lon = coord[0]
        const lat = coord[1]
        const vec = latLonToVector3(lat, lon, globeRadius * 1.006)
        positions.push(vec.x, vec.y, vec.z)
      })
      const lineGeo = new LineGeometry()
      lineGeo.setPositions(positions)
      const line = new Line2(lineGeo, lineMat)
      line.computeLineDistances()
      scene.add(line)
    })
  }

  // 异步加载 TopoJSON 地理数据
  fetch('https://cdn.jsdelivr.net/npm/world-atlas@2/countries-110m.json')
    .then(r => r.json())
    .then(topoData => {
      const geoData = feature(topoData, topoData.objects.countries)
      geoData.features.forEach(f => {
        const g = f.geometry
        if (g.type === 'Polygon') {
          drawThickPolygon(g.coordinates)
        } else if (g.type === 'MultiPolygon') {
          g.coordinates.forEach(poly => drawThickPolygon(poly))
        }
      })
    })
    .catch(err => console.error('Failed to load world map:', err))

  // === 动画循环 ===
  const raycaster = new THREE.Raycaster()

  function animate() {
    animationId = requestAnimationFrame(animate)
    controls.update()

    // 标签背面遮挡检测
    labelObjects.forEach(label => {
      const wp = label.localPositions.clone().applyMatrix4(globe.matrixWorld)
      const dir = wp.clone().sub(camera.position).normalize()
      raycaster.set(camera.position, dir)
      const intersects = raycaster.intersectObject(globe)
      if (intersects.length > 0) {
        const hitDist = intersects[0].distance
        const labelDist = camera.position.distanceTo(wp)
        label.element.style.opacity = hitDist < labelDist - 0.005 ? '0' : '1'
      } else {
        label.element.style.opacity = '1'
      }
    })

    renderer.render(scene, camera)
    labelRenderer.render(scene, camera)
  }
  animate()
}

/* ============================
   聚焦特异点
   ============================ */
let focusAnimId = null

function focusSingu(i) {
  if (i < 0 || i >= singularityList.value.length) return
  activeIdx.value = i
  const s = singularityList.value[i]

  clearTimeout(animTimer)
  if (focusAnimId) { cancelAnimationFrame(focusAnimId); focusAnimId = null }

  controls.autoRotate = false

  const targetPos = latLonToVector3(s.lat, s.lng, 0)
  const camTarget = latLonToVector3(s.lat, s.lng, 2.8)

  const startCam = camera.position.clone()
  const startTarget = controls.target.clone()
  const duration = 1200
  const startTime = performance.now()

  function step() {
    const t = Math.min((performance.now() - startTime) / duration, 1)
    const ease = 1 - Math.pow(1 - t, 3) // ease-out cubic

    camera.position.lerpVectors(startCam, camTarget, ease)
    controls.target.lerpVectors(startTarget, targetPos, ease)
    controls.update()

    if (t < 1) {
      focusAnimId = requestAnimationFrame(step)
    } else {
      focusAnimId = null
      animTimer = setTimeout(() => {
        controls.autoRotate = true
        activeIdx.value = -1
      }, 4000)
    }
  }
  step()
}

/* ============================
   数据流滚动
   ============================ */
const pool = [
  '> 正在检索时空异常系数...',
  '> 灵子转移进入待机状态',
  '> 反召唤系统：NOMINAL',
  '> 魔力填充率 89.7%',
  '> 观测到时空扭曲 - 第{{N}}象限',
  '> 迦勒底亚斯同步率 99.98%',
  '> 虚数空间稳定',
  '> 示巴：无异常',
  '> 灵子筐体待机中',
  '> 拉普拉斯终端在线',
]
let poolIdx = 0

function cycleStream() {
  streamLines.value = streamLines.value.slice(1)
  let txt = pool[poolIdx % pool.length]
  txt = txt.replace('{{N}}', String(Math.floor(Math.random() * 4) + 1))
  streamLines.value.push(txt)
  poolIdx++
}

/* ============================
   灵子转移
   ============================ */
function doRayshift() {
  if (rayshifting.value || seals.value <= 0) return
  rayshifting.value = true
  seals.value--
  setTimeout(() => {
    rayshifting.value = false
    mana.value = Math.max(20, mana.value - 5)
    stab.value = Math.max(50, stab.value - 3 + Math.floor(Math.random() * 6))
  }, 3000)
}

/* ============================
   辅助
   ============================ */
function mbarColor(r) {
  return r > 85 ? '#34c759' : r > 60 ? '#ff9f0a' : '#ff3b30'
}

/* ============================
   生命周期
   ============================ */
onMounted(() => {
  tick()
  setInterval(tick, 1000)
  runLoading()
  streamTimer = setInterval(cycleStream, 4000)

  // 全屏切换
  function onResize() {
    if (!camera || !renderer || !labelRenderer || !earthRef.value) return
    const w = earthRef.value.clientWidth
    const h = earthRef.value.clientHeight
    if (w === 0 || h === 0) return
    camera.aspect = w / h
    camera.updateProjectionMatrix()
    renderer.setSize(w, h)
    labelRenderer.setSize(w, h)
    if (lineMat) lineMat.resolution.set(w, h)
  }

  const fullscreenHandler = () => setTimeout(onResize, 120)
  document.addEventListener('fullscreenchange', fullscreenHandler)
  document.addEventListener('webkitfullscreenchange', fullscreenHandler)

  // resize
  let resizeTimer
  window.addEventListener('resize', () => {
    clearTimeout(resizeTimer)
    resizeTimer = setTimeout(onResize, 150)
  })

  // 模拟系统参数波动
  setInterval(() => {
    mana.value = Math.min(100, Math.max(20, mana.value + (Math.random() > 0.5 ? 1 : -1)))
    stab.value = Math.min(100, Math.max(50, stab.value + (Math.random() > 0.5 ? 1 : -1) * 0.5))
  }, 2000)
})

onUnmounted(() => {
  if (animationId) cancelAnimationFrame(animationId)
  if (focusAnimId) cancelAnimationFrame(focusAnimId)
  clearTimeout(animTimer)
  clearInterval(streamTimer)

  // 清理 Three.js 场景
  if (renderer) {
    renderer.dispose()
    renderer.domElement.remove()
  }
  if (labelRenderer) {
    labelRenderer.domElement.remove()
  }
  scene = null
  camera = null
  controls = null
  globe = null
  labelObjects = []
})
</script>

 <style scoped>
/* =============================================
   GeekTyper Blue Terminal Aesthetic
   — Blue hacker terminal / retro CRT style
   ============================================= */

/* ---------- 全局 ---------- */
.chaldea-container {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background: rgb(8, 24, 56);
  /* Textured background overlay */
  background-image:
    repeating-linear-gradient(
      0deg,
      transparent,
      transparent 2px,
      rgba(255,255,255,0.02) 2px,
      rgba(255,255,255,0.02) 4px
    ),
    radial-gradient(
      ellipse at 20% 20%,
      rgba(87,150,208,0.06) 0%,
      transparent 60%
    ),
    radial-gradient(
      ellipse at 80% 80%,
      rgba(0,0,0,0.2) 0%,
      transparent 50%
    );
  font-family: Arial, 'Segoe UI', sans-serif;
  user-select: none;
  color: rgb(255, 255, 255);
}

/* ---------- CRT Scanline Overlay ---------- */
.scanline-overlay {
  position: fixed;
  inset: 0;
  z-index: 9998;
  pointer-events: none;
  background: repeating-linear-gradient(
    0deg,
    transparent,
    transparent 2px,
    rgba(0,0,0,0.06) 2px,
    rgba(0,0,0,0.06) 4px
  );
  animation: scanline-flicker 0.1s infinite;
  opacity: 0.5;
}

@keyframes scanline-flicker {
  0%   { opacity: 0.3; }
  50%  { opacity: 0.5; }
  100% { opacity: 0.3; }
}

/* ---------- 加载屏幕 ---------- */
.loading-screen {
  position: fixed;
  inset: 0;
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgb(8, 24, 56);
  background-image:
    repeating-linear-gradient(
      0deg,
      transparent,
      transparent 3px,
      rgba(255,255,255,0.02) 3px,
      rgba(255,255,255,0.02) 6px
    );
}
.loading-content { text-align: center; }
.chaldea-logo-anim {
  position: relative;
  width: 80px;
  height: 80px;
  margin: 0 auto 32px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.logo-outer-ring {
  position: absolute;
  width: 100%; height: 100%;
  border: 1px solid rgba(255,255,255,0.5);
  border-radius: 50%;
  animation: spin 6s linear infinite;
  box-shadow: 0 0 12px rgba(255,255,255,0.15);
}
.logo-inner-ring {
  position: absolute;
  width: 70%; height: 70%;
  border: 1px solid rgba(255,255,255,0.3);
  border-radius: 50%;
  animation: spin-reverse 4s linear infinite;
}
.logo-symbol {
  font-size: 28px;
  color: rgb(255, 255, 255);
  text-shadow: 0 0 12px rgba(255,255,255,0.6);
  animation: pulse-glow 2s ease-in-out infinite alternate;
}
.loading-bar-track {
  width: 240px;
  height: 4px;
  background: rgba(255,255,255,0.2);
  margin: 0 auto;
  border: 1px solid rgba(255,255,255,0.3);
  border-radius: 2px;
  overflow: hidden;
}
.loading-bar-fill {
  height: 100%;
  background: rgb(255, 255, 255);
  transition: width 0.1s linear;
  border-radius: 2px;
  box-shadow: 0 0 8px rgba(255,255,255,0.5);
}
.loading-label {
  margin-top: 16px;
  font-size: 12px;
  letter-spacing: 2px;
  color: rgb(255, 255, 255);
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  text-shadow: 0 0 6px rgba(255,255,255,0.3);
  opacity: 0.85;
}

/* ---------- 3D 地球 ---------- */
.earth-canvas {
  position: absolute;
  inset: 0;
  z-index: 1;
  overflow: hidden;
}

/* ---------- 顶部栏 — 终端风格 ---------- */
.top-bar {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  z-index: 20;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 24px;
  background: rgba(0,0,0,0.2);
  border-bottom: 1px solid rgba(255,255,255,0.2);
}
.tb-left { display: flex; align-items: center; gap: 10px; }
.tb-icon { font-size: 16px; color: rgb(255,255,255); text-shadow: 0 0 8px rgba(255,255,255,0.4); }
.tb-title {
  font-size: 13px;
  font-weight: 600;
  letter-spacing: 2px;
  color: rgb(255, 255, 255);
  text-shadow: 0 0 6px rgba(255,255,255,0.2);
}
.tb-center { display: flex; align-items: center; gap: 14px; }
.tb-clock {
  font-size: 14px;
  font-weight: 400;
  color: rgb(255, 255, 255);
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  letter-spacing: 1px;
  text-shadow: 0 0 6px rgba(255,255,255,0.2);
}
.tb-date { font-size: 11px; color: rgba(255,255,255,0.7); }
.tb-right { display: flex; align-items: center; gap: 20px; }
.tb-sq, .tb-fp { display: flex; align-items: center; gap: 5px; font-size: 12px; }
.sq-icon { color: rgb(255,255,255); font-size: 12px; text-shadow: 0 0 6px rgba(255,255,255,0.3); }
.sq-num { color: rgb(255, 255, 255); font-weight: 600; }
.fp-icon { color: rgb(255, 255, 255); font-size: 8px; }
.fp-num { color: rgba(255,255,255,0.7); }
.tb-auth { margin-left: 8px; }
.tb-login-btn {
  font-size: 11px;
  color: rgba(255,255,255,0.8);
  text-decoration: none;
  padding: 4px 12px;
  border: 1px solid rgba(255,255,255,0.25);
  letter-spacing: 1px;
  transition: all 0.2s;
}
.tb-login-btn:hover {
  color: #fff;
  border-color: rgba(255,255,255,0.5);
  box-shadow: 0 0 10px rgba(255,255,255,0.1);
}

/* ---------- 面板通用 — 终端窗口风格 ---------- */
.panel {
  position: absolute;
  z-index: 10;
  width: 300px;
  padding: 16px;
  color: rgb(255, 255, 255);
  background: rgba(0, 0, 0, 0.25);
  border: 1px solid rgb(255, 255, 255);
  border-radius: 0;
  box-shadow: 0 0 16px rgba(255,255,255,0.1);
  transition: border-color 0.25s ease, box-shadow 0.25s ease;
}
.panel:hover {
  border-color: rgb(255, 255, 255);
  box-shadow: 0 0 24px rgba(255,255,255,0.2);
}
.left-panel { top: 66px; left: 20px; }
.right-panel { top: 66px; right: 20px; }

.pnl-hdr {
  margin-bottom: 14px;
  padding-bottom: 8px;
  border-bottom: 1px solid rgba(255,255,255,0.2);
}
.pnl-hdr h2,
.pnl-hdr h3 {
  margin: 0;
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 2px;
  color: rgb(255, 255, 255);
  text-transform: uppercase;
  text-shadow: 0 0 4px rgba(255,255,255,0.2);
}
.pnl-line {
  display: none;
}

/* ---------- 观测网格 ---------- */
.pnl-grid { display: flex; flex-direction: column; gap: 6px; }
.pnl-cell {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 0;
  border-bottom: 1px solid rgba(255,255,255,0.1);
}
.pnl-cell:last-child { border-bottom: none; }
.pcell-lbl { font-size: 12px; color: rgba(255,255,255,0.75); }
.pcell-val {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 11px;
  letter-spacing: 0.5px;
  font-weight: 600;
}
.pcell-val.alive { color: #4cd964; text-shadow: 0 0 6px rgba(76,217,100,0.4); }
.pcell-val.warn { color: #ffcc00; text-shadow: 0 0 6px rgba(255,204,0,0.3); }
.pcell-val.danger { color: #ff4444; text-shadow: 0 0 6px rgba(255,68,68,0.4); }

.pnl-divider {
  height: 1px;
  background: rgba(255,255,255,0.15);
  margin: 12px 0;
}
.pnl-subtitle {
  font-size: 10px;
  letter-spacing: 1.5px;
  color: rgba(255,255,255,0.6);
  text-transform: uppercase;
  margin-bottom: 8px;
}
.sub-icon { margin-right: 4px; }

/* ---------- 特异点列表 ---------- */
.singu-list {
  list-style: none;
  padding: 0;
  margin: 0;
  max-height: 220px;
  overflow-y: auto;
}
.singu-list::-webkit-scrollbar { width: 3px; }
.singu-list::-webkit-scrollbar-track { background: rgba(0,0,0,0.1); }
.singu-list::-webkit-scrollbar-thumb { background: rgba(255,255,255,0.3); border-radius: 0; }

.singu-item {
  display: flex;
  align-items: center;
  padding: 5px 8px;
  font-size: 11px;
  cursor: pointer;
  transition: background 0.15s;
  border: 1px solid transparent;
}
.singu-item:hover { background: rgba(255,255,255,0.08); }
.singu-item.active {
  background: rgba(255,255,255,0.12);
  border-color: rgba(255,255,255,0.4);
}
.s-dot { width: 5px; height: 5px; border-radius: 50%; flex-shrink: 0; margin-right: 8px; }
.s-num {
  width: 18px;
  color: rgba(255,255,255,0.4);
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 10px;
}
.s-name { flex: 1; margin: 0 6px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; color: rgb(255,255,255); }
.s-era { color: rgba(255,255,255,0.4); font-size: 10px; white-space: nowrap; }

/* ---------- 系统状态 ---------- */
.sys-rows { display: flex; flex-direction: column; gap: 5px; }
.sys-row {
  display: flex;
  justify-content: space-between;
  padding: 4px 0;
  border-bottom: 1px solid rgba(255,255,255,0.08);
  font-size: 12px;
  color: rgba(255,255,255,0.75);
}
.sys-row:last-child { border-bottom: none; }
.sys-row .ok { color: #4cd964; }
.sys-row .warn { color: #ffcc00; }
.sys-row .danger { color: #ff4444; }

/* ---------- 御主适应条 ---------- */
.master-bars { display: flex; flex-direction: column; gap: 7px; }
.mbar-hdr { display: flex; justify-content: space-between; font-size: 11px; color: rgba(255,255,255,0.6); margin-bottom: 2px; }
.mbar-track { height: 4px; background: rgba(0,0,0,0.2); border: 1px solid rgba(255,255,255,0.2); border-radius: 0; overflow: hidden; }
.mbar-fill { height: 100%; border-radius: 0; transition: width 1s ease; }

/* ---------- 数据流 — 终端日志 ---------- */
.datastream {
  margin-top: 4px;
  padding: 6px;
  background: rgba(0,0,0,0.15);
  border: 1px solid rgba(255,255,255,0.1);
  min-height: 54px;
}
.stream-line {
  font-size: 10px;
  color: rgb(255, 255, 255);
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  margin: 2px 0;
  opacity: 0.8;
  animation: fade-in-stream 0.4s ease;
}
.stream-line:nth-child(3) {
  opacity: 1;
  color: rgb(255, 255, 255);
  text-shadow: 0 0 6px rgba(255,255,255,0.3);
}

/* ---------- Blinking cursor effect on data stream ---------- */
.datastream::after {
  content: '█';
  font-family: 'JetBrains Mono', monospace;
  font-size: 10px;
  color: rgb(255, 255, 255);
  animation: blink-cursor 1s step-end infinite;
  opacity: 0.6;
  margin-left: 2px;
}
@keyframes blink-cursor {
  0%, 100% { opacity: 0.6; }
  50% { opacity: 0; }
}

/* ---------- 令呪 ---------- */
.seals-bar {
  position: absolute;
  left: 50%;
  bottom: 28px;
  z-index: 10;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 6px 16px;
  background: rgba(0,0,0,0.2);
  border: 1px solid rgba(255,255,255,0.2);
}
.seal {
  width: 36px;
  height: 36px;
  cursor: pointer;
  transition: transform 0.2s, filter 0.2s;
  filter: brightness(0.85);
}
.seal:hover { transform: scale(1.1); filter: brightness(1.1); }
.seal.dead { cursor: default; opacity: 0.25; filter: none; }
.seal.dead:hover { transform: none; }
.seal svg { width: 100%; height: 100%; }
.seals-label {
  font-size: 9px;
  color: rgba(255,255,255,0.5);
  letter-spacing: 2px;
  margin-left: 4px;
  text-transform: uppercase;
}

/* ---------- 灵子转移按钮 — 白底蓝字风格 ---------- */
.rayshift-btn {
  position: absolute;
  right: 24px;
  bottom: 28px;
  z-index: 10;
  padding: 8px 26px;
  border: 1px solid rgb(255, 255, 255);
  border-radius: 0;
  background: rgb(255, 255, 255);
  color: rgb(87, 150, 208);
  cursor: pointer;
  text-align: center;
  transition: all 0.2s ease;
  font-family: Arial, sans-serif;
  font-weight: 600;
}
.rayshift-btn:hover:not(:disabled) {
  color: rgb(255, 255, 255);
  background: rgb(87, 150, 208);
  border-color: rgb(255, 255, 255);
  box-shadow: 0 0 16px rgba(255,255,255,0.25);
}
.rayshift-btn:disabled { opacity: 0.3; cursor: not-allowed; background: rgba(255,255,255,0.3); color: rgba(87,150,208,0.3); }
.rb-main { display: block; font-size: 12px; letter-spacing: 1.5px; }
.rb-sub { display: block; font-size: 8px; color: inherit; letter-spacing: 1px; margin-top: 2px; opacity: 0.7; }

/* ---------- 动画 ---------- */
@keyframes spin { to { transform: rotate(360deg); } }
@keyframes spin-reverse { to { transform: rotate(-360deg); } }
@keyframes pulse-glow {
  0% { opacity: 0.5; text-shadow: 0 0 6px rgba(255,255,255,0.3); }
  100% { opacity: 1; text-shadow: 0 0 16px rgba(255,255,255,0.7); }
}
@keyframes fade-in-stream {
  from { opacity: 0; transform: translateY(-2px); }
  to { opacity: 1; transform: translateY(0); }
}

/* Transition */
.fade-enter-active, .fade-leave-active { transition: opacity 0.6s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }

/* ---------- 响应式 ---------- */
@media (max-width: 900px) {
  .panel { width: 240px; padding: 12px; }
  .left-panel { left: 10px; top: 60px; }
  .right-panel { right: 10px; top: 60px; }
  .top-bar { padding: 8px 14px; flex-wrap: wrap; gap: 4px; }
  .tb-title { font-size: 11px; }
  .tb-clock { font-size: 12px; }
  .rayshift-btn { right: 12px; bottom: 12px; padding: 8px 18px; }
}
@media (max-width: 600px) {
  .panel { width: calc(50vw - 20px); min-width: 160px; font-size: 10px; }
  .tb-right { gap: 10px; }
  .seal { width: 28px; height: 28px; }
  .seals-bar { gap: 10px; padding: 4px 12px; }
}
</style>

<style>
/* 特异点标签样式（非 scoped，CSS2DObject 动态创建） */
.singularity-label {
  color: #7fceff;
  font-family: 'Courier New', sans-serif;
  font-size: 11px;
  font-weight: bold;
  padding: 4px 8px;
  background: rgba(5, 16, 40, 0.8);
  border: 1px solid #2663b5;
  border-radius: 4px;
  white-space: nowrap;
  text-shadow: 0 0 5px #2663b5;
  box-shadow: 0 0 8px rgba(38, 99, 181, 0.4);
  pointer-events: none;
  user-select: none;
  transition: opacity 0.2s ease;
}

.singularity-label::before {
  content: '';
  display: inline-block;
  width: 6px;
  height: 6px;
  background-color: #ff3b30;
  box-shadow: 0 0 8px #ff3b30;
  border-radius: 50%;
  margin-right: 6px;
  vertical-align: middle;
}
</style>
