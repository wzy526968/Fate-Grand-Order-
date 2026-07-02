<template>
  <div class="sandbox-page">
    <!-- CRT scanline overlay -->
    <div class="scanline-overlay"></div>
    <NavBar theme="dark" />
    <span class="page-en-subtitle">ATLASIA · TRISMEGISTUS SYSTEM</span>

    <div class="sandbox-header">
      <div class="sys-activation">
        <p class="sys-line"><span class="sys-prompt">></span> 系统激活：阿特拉斯院·拟似灵子计算机「特里斯墨吉斯托斯」</p>
        <p class="sys-line"><span class="sys-prompt">></span> 当前任务：检索常规七大职阶之英灵记录</p>
      </div>

      <!-- Admin Controls -->
      <div v-if="authStore.isAdmin" class="admin-bar">
        <span class="admin-badge">ADMIN</span>
        <button class="admin-btn" @click="openCreateModal">+ 灵基登录 / REGISTER</button>
      </div>
    </div>

    <div class="panels-container">
      <!-- Panel 1: Servant List -->
      <FrostedPanel title="灵基一览 / SERVANT ROSTER" variant="terminal" class="panel panel-left">
        <div class="class-filters">
          <button
            v-for="cls in classList"
            :key="cls.id"
            :class="['class-btn', { active: activeClassId === cls.id }]"
            @click="filterByClass(cls.id)"
          >
            <span class="class-name">{{ cls.className }}</span>
            <span class="class-count">{{ cls.count }}</span>
          </button>
          <button
            :class="['class-btn', { active: activeClassId === null }]"
            @click="filterByClass(null)"
          >
            <span class="class-name">ALL</span>
            <span class="class-count">{{ servantStore.servants.length }}</span>
          </button>
        </div>

        <div v-if="servantStore.loading" class="list-loading">Loading...</div>

        <div v-else class="servant-list">
          <div
            v-for="s in filteredServants"
            :key="s.id"
            :class="['servant-item', { selected: servantStore.selectedServant?.id === s.id }]"
            @click="selectServant(s)"
          >
            <span class="s-name">{{ s.name }}</span>
            <span class="s-rarity">{{ '★'.repeat(s.rarity) }}</span>
            <div v-if="authStore.isAdmin" class="admin-actions" @click.stop>
              <button class="icon-btn edit-btn" title="编辑" @click="openEditModal(s)">✎</button>
              <button class="icon-btn delete-btn" title="删除" @click="confirmDelete(s)">✕</button>
            </div>
          </div>

          <div v-if="filteredServants.length === 0" class="list-empty">
            No servants found.
          </div>
        </div>
      </FrostedPanel>

      <!-- Panel 2: Servant Detail -->
      <FrostedPanel title="灵基详情 / SERVANT DATA" variant="terminal" class="panel panel-center">
        <div v-if="!servantStore.selectedServant" class="placeholder-text">
          Select a servant from the list
        </div>

        <template v-else>
          <div class="detail-header">
            <h2 class="detail-name">{{ servantStore.selectedServant.name }}</h2>
            <span class="detail-class-badge">{{ servantStore.selectedServant.className }}</span>
          </div>

          <p class="detail-title">{{ servantStore.selectedServant.title }}</p>
          <p class="detail-rarity">{{ '★'.repeat(servantStore.selectedServant.rarity) }}</p>

          <div class="detail-divider"></div>

          <div class="stats-grid">
            <div v-for="stat in statList" :key="stat.key" class="stat-row">
              <span class="stat-label">{{ stat.label }}</span>
              <div class="stat-bar-track">
                <div
                  class="stat-bar-fill"
                  :style="{ width: (getStat(stat.key) / 10 * 100) + '%' }"
                ></div>
              </div>
              <span class="stat-value">{{ getStat(stat.key) }}/10</span>
            </div>
          </div>

          <div class="detail-divider"></div>

          <p class="detail-desc">{{ servantStore.selectedServant.description }}</p>
        </template>
      </FrostedPanel>

      <!-- Panel 3: Radar Chart -->
      <FrostedPanel title="能力雷达图 / PARAMETER CHART" variant="terminal" class="panel panel-right">
        <div v-if="!servantStore.selectedServant" class="placeholder-text">
          Select a servant to view radar chart
        </div>
        <ServantRadarChart
          v-else
          :servant="servantStore.selectedServant"
          height="400px"
        />
      </FrostedPanel>
    </div>

    <!-- Create/Edit Modal -->
    <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="modal-title">{{ isEditing ? '灵基编辑 / EDIT SERVANT' : '灵基登录 / CREATE SERVANT' }}</h3>
          <button class="modal-close" @click="closeModal">✕</button>
        </div>

        <div class="modal-body">
          <div class="form-row">
            <div class="form-group">
              <label>NAME / 真名</label>
              <input v-model="form.name" type="text" placeholder="例：阿尔托莉雅·潘德拉贡" />
            </div>
            <div class="form-group">
              <label>CLASS / 职阶</label>
              <select v-model.number="form.classId">
                <option v-for="c in availableClasses" :key="c.id" :value="c.id">
                  {{ c.className }}
                </option>
              </select>
            </div>
          </div>

          <div class="form-group">
            <label>TITLE / 称号</label>
            <input v-model="form.title" type="text" placeholder="例：亚瑟王" />
          </div>

          <div class="form-row">
            <div class="form-group">
              <label>RARITY / 稀有度</label>
              <select v-model.number="form.rarity">
                <option :value="1">★</option>
                <option :value="2">★★</option>
                <option :value="3">★★★</option>
                <option :value="4">★★★★</option>
                <option :value="5">★★★★★</option>
              </select>
            </div>
          </div>

          <div class="stats-section">
            <label class="section-label">PARAMETERS / 能力参数 (0–10)</label>
            <div class="stats-grid-form">
              <div v-for="stat in statList" :key="stat.key" class="stat-input-group">
                <span class="stat-input-label">{{ stat.label }}</span>
                <input v-model.number="form[stat.key]" type="number" min="0" max="10" />
              </div>
            </div>
          </div>

          <div class="form-group">
            <label>DESCRIPTION / 灵基描述</label>
            <textarea v-model="form.description" rows="5" placeholder="经由冬木事象记录提取的剑之骑士…"></textarea>
          </div>
        </div>

        <div class="modal-footer">
          <p v-if="formError" class="form-error">{{ formError }}</p>
          <button class="modal-btn cancel" @click="closeModal">CANCEL / 取消</button>
          <button class="modal-btn confirm" @click="handleSave" :disabled="saving">
            {{ saving ? 'SAVING...' : 'CONFIRM / 确认' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Delete Confirm Modal -->
    <div v-if="showDeleteConfirm" class="modal-overlay" @click.self="showDeleteConfirm = false">
      <div class="modal-content modal-sm">
        <div class="modal-header">
          <h3 class="modal-title">DELETE / 删除确认</h3>
          <button class="modal-close" @click="showDeleteConfirm = false">✕</button>
        </div>
        <div class="modal-body">
          <p class="delete-warning">
            确认要删除「<strong>{{ deleteTarget?.name }}</strong>」的灵基记录？此操作不可撤销。
          </p>
        </div>
        <div class="modal-footer">
          <button class="modal-btn cancel" @click="showDeleteConfirm = false">CANCEL / 取消</button>
          <button class="modal-btn confirm danger" @click="handleDelete" :disabled="saving">
            {{ saving ? 'DELETING...' : 'DELETE / 删除' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import NavBar from '../components/NavBar.vue'
import FrostedPanel from '../components/FrostedPanel.vue'
import ServantRadarChart from '../components/ServantRadarChart.vue'
import { useServantStore } from '../stores/servant'
import { useAuthStore } from '../stores/auth'
import api from '../api'

const servantStore = useServantStore()
const authStore = useAuthStore()
const activeClassId = ref(null)
const availableClasses = ref([])

// Modal state
const showModal = ref(false)
const isEditing = ref(false)
const editingId = ref(null)
const saving = ref(false)
const formError = ref('')

// Delete confirm state
const showDeleteConfirm = ref(false)
const deleteTarget = ref(null)

const defaultForm = () => ({
  name: '',
  classId: 1,
  title: '',
  rarity: 5,
  strength: 5,
  endurance: 5,
  agility: 5,
  magic: 5,
  luck: 5,
  np: 5,
  description: '',
})

const form = ref(defaultForm())

const statList = [
  { key: 'strength', label: 'STR 筋力' },
  { key: 'endurance', label: 'END 耐久' },
  { key: 'agility', label: 'AGI 敏捷' },
  { key: 'magic', label: 'MAG 魔力' },
  { key: 'luck', label: 'LUK 幸运' },
  { key: 'np', label: 'NP 宝具' },
]

const classList = computed(() => {
  const map = new Map()
  servantStore.servants.forEach(s => {
    if (s.className) {
      if (!map.has(s.className)) {
        map.set(s.className, { id: s.className, className: s.className, count: 0 })
      }
      map.get(s.className).count++
    }
  })
  const order = ['Saber', 'Archer', 'Lancer', 'Rider', 'Caster', 'Assassin', 'Berserker']
  return order.map(name => map.get(name)).filter(Boolean)
})

const filteredServants = computed(() => {
  if (!activeClassId.value) return servantStore.servants
  return servantStore.servants.filter(s => s.className === activeClassId.value)
})

function filterByClass(classId) {
  activeClassId.value = classId
}

function selectServant(s) {
  servantStore.selectServant(s)
}

function getStat(key) {
  return servantStore.selectedServant?.[key] ?? 0
}

// --- Admin CRUD ---

async function fetchClasses() {
  try {
    const res = await api.get('/api/classes')
    availableClasses.value = res.data
  } catch (e) {
    console.error('Failed to fetch classes from API, building from servant data', e)
    // Fallback: extract unique classes from existing servant data
    const classMap = new Map()
    servantStore.servants.forEach(s => {
      if (s.classId != null && s.className) {
        if (!classMap.has(s.classId)) {
          classMap.set(s.classId, { id: s.classId, className: s.className, icon: s.classIcon || '' })
        }
      }
    })
    availableClasses.value = Array.from(classMap.values())
    if (availableClasses.value.length === 0) {
      console.warn('No class data available from either API or servant data')
    }
  }
}

async function openCreateModal() {
  // 确保职阶列表已加载
  if (availableClasses.value.length === 0) {
    await fetchClasses()
  }
  isEditing.value = false
  editingId.value = null
  form.value = defaultForm()
  formError.value = ''
  showModal.value = true
}

async function openEditModal(servant) {
  // 确保职阶列表已加载
  if (availableClasses.value.length === 0) {
    await fetchClasses()
  }
  isEditing.value = true
  editingId.value = servant.id
  form.value = {
    name: servant.name || '',
    classId: servant.classId || availableClasses.value.find(c => c.className === servant.className)?.id || 1,
    title: servant.title || '',
    rarity: servant.rarity || 5,
    strength: servant.strength ?? 5,
    endurance: servant.endurance ?? 5,
    agility: servant.agility ?? 5,
    magic: servant.magic ?? 5,
    luck: servant.luck ?? 5,
    np: servant.np ?? 5,
    description: servant.description || '',
  }
  formError.value = ''
  showModal.value = true
}

function closeModal() {
  showModal.value = false
  formError.value = ''
}

async function handleSave() {
  if (!form.value.name.trim()) {
    formError.value = 'Name is required / 真名不能为空'
    return
  }
  saving.value = true
  formError.value = ''
  try {
    const payload = { ...form.value }
    if (isEditing.value) {
      await servantStore.updateServant(editingId.value, payload)
    } else {
      await servantStore.createServant(payload)
    }
    closeModal()
  } catch (e) {
    formError.value = 'ERROR: ' + (e.response?.data?.error || e.message)
  } finally {
    saving.value = false
  }
}

function confirmDelete(servant) {
  deleteTarget.value = servant
  showDeleteConfirm.value = true
}

async function handleDelete() {
  if (!deleteTarget.value) return
  saving.value = true
  try {
    await servantStore.deleteServant(deleteTarget.value.id)
    showDeleteConfirm.value = false
    deleteTarget.value = null
  } catch (e) {
    console.error('Failed to delete', e)
  } finally {
    saving.value = false
  }
}

onMounted(() => {
  servantStore.fetchServants()
  if (authStore.isAdmin) {
    fetchClasses()
  }
})
</script>

<style scoped>
/* =============================================
   Terminal CRT aesthetic — matching Chaldea theme
   ============================================= */

.sandbox-page {
  min-height: 100vh;
  background: rgb(8, 24, 56);
  background-image:
    repeating-linear-gradient(0deg, transparent, transparent 2px, rgba(255,255,255,0.015) 2px, rgba(255,255,255,0.015) 4px),
    radial-gradient(ellipse at 20% 20%, rgba(87,150,208,0.06) 0%, transparent 60%),
    radial-gradient(ellipse at 80% 80%, rgba(0,0,0,0.2) 0%, transparent 50%);
  font-family: Arial, 'Segoe UI', sans-serif;
  color: rgb(255, 255, 255);
  position: relative;
}

/* CRT scanline */
.scanline-overlay {
  position: fixed;
  inset: 0;
  z-index: 9998;
  pointer-events: none;
  background: repeating-linear-gradient(
    0deg, transparent, transparent 2px, rgba(0,0,0,0.04) 2px, rgba(0,0,0,0.04) 4px
  );
  animation: flicker 0.1s infinite;
  opacity: 0.3;
}
@keyframes flicker {
  0%   { opacity: 0.2; }
  50%  { opacity: 0.35; }
  100% { opacity: 0.2; }
}

/* System Activation Header */
.sandbox-header {
  padding: 66px 32px 12px;
  text-align: left;
  max-width: 900px;
}
.page-en-subtitle {
  position: fixed;
  top: 72px;
  right: 24px;
  z-index: 9999;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 15px;
  letter-spacing: 3px;
  color: rgba(255,255,255,0.2);
  white-space: nowrap;
  pointer-events: none;
}
.sys-activation {
  background: rgba(0, 0, 0, 0.25);
  border: 1px solid rgba(255,255,255,0.1);
  padding: 12px 18px;
}
.sys-line {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 11px;
  color: rgba(255,255,255,0.7);
  margin: 2px 0;
  line-height: 1.6;
  letter-spacing: 0.3px;
}
.sys-prompt {
  color: #5796D0;
  margin-right: 6px;
}

/* Admin Bar */
.admin-bar {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 8px;
  padding: 6px 12px;
  background: rgba(255, 200, 0, 0.06);
  border: 1px solid rgba(255, 200, 0, 0.2);
}
.admin-badge {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 9px;
  letter-spacing: 2px;
  color: rgba(255, 200, 0, 0.9);
  padding: 2px 6px;
  border: 1px solid rgba(255, 200, 0, 0.3);
}
.admin-btn {
  padding: 4px 10px;
  font-size: 10px;
  font-family: 'JetBrains Mono', monospace;
  letter-spacing: 1px;
  background: transparent;
  border: 1px solid rgba(255,200,0,0.3);
  color: rgba(255,200,0,0.8);
  cursor: pointer;
  transition: all 0.2s;
}
.admin-btn:hover {
  background: rgba(255,200,0,0.1);
  border-color: rgba(255,200,0,0.6);
  color: rgb(255,200,0);
}

/* Panels Layout */
.panels-container {
  display: flex;
  gap: 14px;
  padding: 16px 20px 24px;
  height: calc(100vh - 170px);
}
.panel {
  flex: 1;
  min-width: 0;
}
.panel-left {
  flex: 0 0 300px;
}
.panel-center {
  flex: 0 0 1;
}
.panel-right {
  flex: 0 0 420px;
}

/* Class Filter Buttons */
.class-filters {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
  margin-bottom: 10px;
}
.class-btn {
  display: flex;
  align-items: center;
  gap: 3px;
  padding: 3px 8px;
  font-size: 10px;
  border: 1px solid rgba(255,255,255,0.15);
  background: transparent;
  color: rgba(255,255,255,0.6);
  cursor: pointer;
  transition: all 0.2s;
  font-family: inherit;
}
.class-btn:hover {
  border-color: rgba(255,255,255,0.35);
  color: rgb(255,255,255);
}
.class-btn.active {
  background: rgba(255,255,255,0.08);
  border-color: rgba(255,255,255,0.4);
  color: rgb(255,255,255);
}
.class-name { font-size: 10px; letter-spacing: 1px; }
.class-count {
  font-size: 9px;
  font-family: 'JetBrains Mono', monospace;
  color: rgba(255,255,255,0.3);
  margin-left: 2px;
}

/* List States */
.list-loading,
.list-empty,
.placeholder-text {
  text-align: center;
  color: rgba(255,255,255,0.3);
  font-size: 11px;
  padding: 28px 0;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}

/* Servant List */
.servant-list {
  display: flex;
  flex-direction: column;
  gap: 1px;
}
.servant-item {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 6px 8px;
  cursor: pointer;
  transition: background 0.15s;
  border: 1px solid transparent;
}
.servant-item:hover {
  background: rgba(255,255,255,0.04);
}
.servant-item.selected {
  background: rgba(255,255,255,0.08);
  border-color: rgba(255,255,255,0.2);
}
.s-name {
  flex: 1;
  font-size: 12px;
  color: rgb(255,255,255);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.s-rarity {
  font-size: 9px;
  color: rgba(255,215,0,0.7);
  flex-shrink: 0;
  letter-spacing: 2px;
}

/* Admin action buttons on list items */
.admin-actions {
  display: flex;
  gap: 2px;
  flex-shrink: 0;
  opacity: 0;
  transition: opacity 0.15s;
}
.servant-item:hover .admin-actions {
  opacity: 1;
}
.icon-btn {
  background: none;
  border: 1px solid transparent;
  color: rgba(255,255,255,0.4);
  cursor: pointer;
  font-size: 11px;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.15s;
  padding: 0;
  line-height: 1;
}
.icon-btn:hover {
  border-color: rgba(255,255,255,0.2);
}
.edit-btn:hover {
  color: #5796D0;
  background: rgba(87,150,208,0.1);
}
.delete-btn:hover {
  color: #ff4444;
  background: rgba(255,68,68,0.1);
}

/* Detail Panel */
.detail-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 4px;
}
.detail-name {
  font-size: 15px;
  font-weight: 600;
  margin: 0;
  color: rgb(255, 255, 255);
}
.detail-class-badge {
  font-size: 9px;
  padding: 2px 8px;
  border: 1px solid rgba(255,255,255,0.25);
  color: rgba(255,255,255,0.8);
  font-weight: 500;
  letter-spacing: 1px;
}
.detail-title {
  font-size: 11px;
  color: rgba(255,255,255,0.5);
  margin: 4px 0;
}
.detail-rarity {
  font-size: 11px;
  color: rgba(255,215,0,0.6);
  margin: 4px 0;
  letter-spacing: 3px;
}
.detail-divider {
  height: 1px;
  background: rgba(255,255,255,0.08);
  margin: 10px 0;
}
.stats-grid {
  display: flex;
  flex-direction: column;
  gap: 7px;
}
.stat-row {
  display: flex;
  align-items: center;
  gap: 8px;
}
.stat-label {
  width: 72px;
  font-size: 10px;
  color: rgba(255,255,255,0.5);
  flex-shrink: 0;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}
.stat-bar-track {
  flex: 1;
  height: 5px;
  background: rgba(255,255,255,0.06);
  border: 1px solid rgba(255,255,255,0.08);
  overflow: hidden;
}
.stat-bar-fill {
  height: 100%;
  background: #5796D0;
  transition: width 0.5s ease;
  box-shadow: 0 0 6px rgba(87,150,208,0.3);
}
.stat-value {
  width: 30px;
  font-size: 10px;
  color: rgba(255,255,255,0.6);
  text-align: right;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}
.detail-desc {
  font-size: 11px;
  color: rgba(255,255,255,0.45);
  line-height: 1.7;
  margin: 0;
  font-family: 'Inter', sans-serif;
}

/* Blinking cursor effect on list */
.servant-list::after {
  content: '█';
  font-family: 'JetBrains Mono', monospace;
  font-size: 10px;
  color: rgba(255,255,255,0.4);
  animation: blink-cursor 1s step-end infinite;
  margin: 2px 0 0 4px;
}
@keyframes blink-cursor {
  0%, 100% { opacity: 0.4; }
  50% { opacity: 0; }
}

/* =============================================
   Modal Styles (CRT terminal aesthetic)
   ============================================= */

.modal-overlay {
  position: fixed;
  inset: 0;
  z-index: 9999;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(2px);
}
.modal-content {
  width: 560px;
  max-width: 92vw;
  max-height: 85vh;
  background: rgb(10, 28, 60);
  border: 1px solid rgba(255,255,255,0.2);
  display: flex;
  flex-direction: column;
  box-shadow: 0 0 40px rgba(0,0,0,0.5);
}
.modal-sm {
  width: 400px;
}
.modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 18px;
  border-bottom: 1px solid rgba(255,255,255,0.1);
}
.modal-title {
  font-size: 12px;
  font-weight: 500;
  letter-spacing: 2px;
  margin: 0;
  color: rgb(255,255,255);
}
.modal-close {
  background: none;
  border: 1px solid transparent;
  color: rgba(255,255,255,0.3);
  cursor: pointer;
  font-size: 14px;
  padding: 2px 6px;
  transition: all 0.15s;
}
.modal-close:hover {
  color: rgb(255,255,255);
  border-color: rgba(255,255,255,0.2);
}
.modal-body {
  padding: 18px;
  overflow-y: auto;
  flex: 1;
}
.modal-footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 8px;
  padding: 14px 18px;
  border-top: 1px solid rgba(255,255,255,0.1);
}
.form-error {
  margin: 0;
  font-size: 10px;
  color: #ff4444;
  font-family: 'JetBrains Mono', monospace;
  flex: 1;
}

/* Form fields */
.form-group {
  margin-bottom: 12px;
  display: flex;
  flex-direction: column;
  gap: 4px;
  flex: 1;
}
.form-group label {
  font-size: 10px;
  color: rgba(255,255,255,0.5);
  letter-spacing: 1px;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}
.form-group input,
.form-group select,
.form-group textarea {
  padding: 8px 10px;
  border: 1px solid rgba(255,255,255,0.15);
  font-size: 12px;
  font-family: 'Inter', sans-serif;
  color: rgb(255,255,255);
  background: rgba(0,0,0,0.25);
  outline: none;
  transition: border-color 0.2s;
}
.form-group input::placeholder,
.form-group textarea::placeholder {
  color: rgba(255,255,255,0.15);
}
.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
  border-color: #5796D0;
}
.form-group select option {
  background: rgb(10, 28, 60);
  color: rgb(255,255,255);
}
.form-group textarea {
  resize: vertical;
  min-height: 80px;
  line-height: 1.6;
}
.form-row {
  display: flex;
  gap: 12px;
}

/* Stats grid in form */
.stats-section {
  margin-bottom: 12px;
}
.section-label {
  font-size: 10px;
  color: rgba(255,255,255,0.5);
  letter-spacing: 1px;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  display: block;
  margin-bottom: 6px;
}
.stats-grid-form {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
}
.stat-input-group {
  display: flex;
  flex-direction: column;
  gap: 2px;
}
.stat-input-label {
  font-size: 9px;
  color: rgba(255,255,255,0.35);
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}
.stat-input-group input {
  padding: 6px 8px;
  border: 1px solid rgba(255,255,255,0.15);
  font-size: 12px;
  font-family: 'JetBrains Mono', monospace;
  color: rgb(255,255,255);
  background: rgba(0,0,0,0.25);
  outline: none;
  text-align: center;
  transition: border-color 0.2s;
  width: 100%;
  box-sizing: border-box;
}
.stat-input-group input:focus {
  border-color: #5796D0;
}
/* Remove number input spinners */
.stat-input-group input[type=number]::-webkit-inner-spin-button,
.stat-input-group input[type=number]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* Modal buttons */
.modal-btn {
  padding: 8px 16px;
  font-size: 10px;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  letter-spacing: 1px;
  cursor: pointer;
  transition: all 0.2s;
  border: 1px solid rgba(255,255,255,0.2);
  background: transparent;
  color: rgba(255,255,255,0.7);
}
.modal-btn:hover:not(:disabled) {
  border-color: rgba(255,255,255,0.5);
  color: rgb(255,255,255);
}
.modal-btn.confirm {
  background: rgba(255,255,255,0.08);
  border-color: rgba(255,255,255,0.3);
  color: rgb(255,255,255);
}
.modal-btn.confirm:hover:not(:disabled) {
  background: rgba(87,150,208,0.2);
  border-color: #5796D0;
}
.modal-btn.danger:hover:not(:disabled) {
  background: rgba(255,68,68,0.2);
  border-color: #ff4444;
  color: #ff4444;
}
.modal-btn:disabled {
  opacity: 0.3;
  cursor: not-allowed;
}

.delete-warning {
  font-size: 12px;
  color: rgba(255,255,255,0.7);
  line-height: 1.6;
  margin: 8px 0;
}
.delete-warning strong {
  color: rgb(255,255,255);
}

/* Responsive */
@media (max-width: 1100px) {
  .panels-container {
    flex-direction: column;
    height: auto;
  }
  .panel-left,
  .panel-center,
  .panel-right {
    flex: none;
    width: 100%;
  }
}
</style>

<style scoped>
/* =============================================
   Terminal CRT aesthetic — matching Chaldea theme
   ============================================= */

.sandbox-page {
  min-height: 100vh;
  background: rgb(8, 24, 56);
  background-image:
    repeating-linear-gradient(0deg, transparent, transparent 2px, rgba(255,255,255,0.015) 2px, rgba(255,255,255,0.015) 4px),
    radial-gradient(ellipse at 20% 20%, rgba(87,150,208,0.06) 0%, transparent 60%),
    radial-gradient(ellipse at 80% 80%, rgba(0,0,0,0.2) 0%, transparent 50%);
  font-family: Arial, 'Segoe UI', sans-serif;
  color: rgb(255, 255, 255);
  position: relative;
}

/* CRT scanline */
.scanline-overlay {
  position: fixed;
  inset: 0;
  z-index: 9998;
  pointer-events: none;
  background: repeating-linear-gradient(
    0deg, transparent, transparent 2px, rgba(0,0,0,0.04) 2px, rgba(0,0,0,0.04) 4px
  );
  animation: flicker 0.1s infinite;
  opacity: 0.3;
}
@keyframes flicker {
  0%   { opacity: 0.2; }
  50%  { opacity: 0.35; }
  100% { opacity: 0.2; }
}

/* System Activation Header */
.sandbox-header {
  padding: 66px 32px 12px;
  text-align: left;
  max-width: 900px;
}
.page-en-subtitle {
  position: fixed;
  top: 72px;
  right: 24px;
  z-index: 9999;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 9px;
  letter-spacing: 3px;
  color: rgba(255,255,255,0.2);
  white-space: nowrap;
  pointer-events: none;
}
.sys-activation {
  background: rgba(0, 0, 0, 0.25);
  border: 1px solid rgba(255,255,255,0.1);
  padding: 12px 18px;
}
.sys-line {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 11px;
  color: rgba(255,255,255,0.7);
  margin: 2px 0;
  line-height: 1.6;
  letter-spacing: 0.3px;
}
.sys-prompt {
  color: #5796D0;
  margin-right: 6px;
}
.sys-note {
  color: rgba(255,255,255,0.35);
  font-size: 10px;
}

/* Panels Layout */
.panels-container {
  display: flex;
  gap: 14px;
  padding: 16px 20px 24px;
  height: calc(100vh - 170px);
}
.panel {
  flex: 1;
  min-width: 0;
}
.panel-left {
  flex: 0 0 300px;
}
.panel-center {
  flex: 0 0 1;
}
.panel-right {
  flex: 0 0 420px;
}

/* Class Filter Buttons */
.class-filters {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
  margin-bottom: 10px;
}
.class-btn {
  display: flex;
  align-items: center;
  gap: 3px;
  padding: 3px 8px;
  font-size: 10px;
  border: 1px solid rgba(255,255,255,0.15);
  background: transparent;
  color: rgba(255,255,255,0.6);
  cursor: pointer;
  transition: all 0.2s;
  font-family: inherit;
}
.class-btn:hover {
  border-color: rgba(255,255,255,0.35);
  color: rgb(255,255,255);
}
.class-btn.active {
  background: rgba(255,255,255,0.08);
  border-color: rgba(255,255,255,0.4);
  color: rgb(255,255,255);
}
.class-name { font-size: 10px; letter-spacing: 1px; }
.class-count {
  font-size: 9px;
  font-family: 'JetBrains Mono', monospace;
  color: rgba(255,255,255,0.3);
  margin-left: 2px;
}

/* List States */
.list-loading,
.list-empty,
.placeholder-text {
  text-align: center;
  color: rgba(255,255,255,0.3);
  font-size: 11px;
  padding: 28px 0;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}

/* Servant List */
.servant-list {
  display: flex;
  flex-direction: column;
  gap: 1px;
}
.servant-item {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 6px 8px;
  cursor: pointer;
  transition: background 0.15s;
  border: 1px solid transparent;
}
.servant-item:hover {
  background: rgba(255,255,255,0.04);
}
.servant-item.selected {
  background: rgba(255,255,255,0.08);
  border-color: rgba(255,255,255,0.2);
}
.s-name {
  flex: 1;
  font-size: 12px;
  color: rgb(255,255,255);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.s-rarity {
  font-size: 9px;
  color: rgba(255,215,0,0.7);
  flex-shrink: 0;
  letter-spacing: 2px;
}

/* Detail Panel */
.detail-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 4px;
}
.detail-name {
  font-size: 15px;
  font-weight: 600;
  margin: 0;
  color: rgb(255, 255, 255);
}
.detail-class-badge {
  font-size: 9px;
  padding: 2px 8px;
  border: 1px solid rgba(255,255,255,0.25);
  color: rgba(255,255,255,0.8);
  font-weight: 500;
  letter-spacing: 1px;
}
.detail-title {
  font-size: 11px;
  color: rgba(255,255,255,0.5);
  margin: 4px 0;
}
.detail-rarity {
  font-size: 11px;
  color: rgba(255,215,0,0.6);
  margin: 4px 0;
  letter-spacing: 3px;
}
.detail-divider {
  height: 1px;
  background: rgba(255,255,255,0.08);
  margin: 10px 0;
}
.stats-grid {
  display: flex;
  flex-direction: column;
  gap: 7px;
}
.stat-row {
  display: flex;
  align-items: center;
  gap: 8px;
}
.stat-label {
  width: 72px;
  font-size: 10px;
  color: rgba(255,255,255,0.5);
  flex-shrink: 0;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}
.stat-bar-track {
  flex: 1;
  height: 5px;
  background: rgba(255,255,255,0.06);
  border: 1px solid rgba(255,255,255,0.08);
  overflow: hidden;
}
.stat-bar-fill {
  height: 100%;
  background: #5796D0;
  transition: width 0.5s ease;
  box-shadow: 0 0 6px rgba(87,150,208,0.3);
}
.stat-value {
  width: 30px;
  font-size: 10px;
  color: rgba(255,255,255,0.6);
  text-align: right;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}
.detail-desc {
  font-size: 11px;
  color: rgba(255,255,255,0.45);
  line-height: 1.7;
  margin: 0;
  font-family: 'Inter', sans-serif;
}

/* Blinking cursor effect on list */
.servant-list::after {
  content: '█';
  font-family: 'JetBrains Mono', monospace;
  font-size: 10px;
  color: rgba(255,255,255,0.4);
  animation: blink-cursor 1s step-end infinite;
  margin: 2px 0 0 4px;
}
@keyframes blink-cursor {
  0%, 100% { opacity: 0.4; }
  50% { opacity: 0; }
}

/* Responsive */
@media (max-width: 1100px) {
  .panels-container {
    flex-direction: column;
    height: auto;
  }
  .panel-left,
  .panel-center,
  .panel-right {
    flex: none;
    width: 100%;
  }
}
</style>
