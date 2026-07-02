<template>
  <div class="staff-page">
    <!-- CRT scanline overlay -->
    <div class="scanline-overlay"></div>
    <NavBar theme="dark" />

    <div class="page-header">
      <div class="sys-activation">
        <p class="sys-line"><span class="sys-prompt">></span> 迦勒底员工档案 // CHALDEA PERSONNEL ARCHIVE</p>
        <p class="sys-line"><span class="sys-prompt">></span> 记录数：<span class="status-ok">{{ personnelStore.allPersonnel.length }}</span> | 部门数：<span class="status-ok">{{ personnelStore.departments.length }}</span> | 数据库状态：<span class="status-ok">ONLINE</span></p>
      </div>
    </div>

    <!-- Department Filters -->
    <div class="filter-bar">
      <button
        :class="['filter-btn', { active: !personnelStore.activeDepartment }]"
        @click="personnelStore.setDepartment(null)"
      >
        全体 / ALL
      </button>
      <button
        v-for="dept in personnelStore.departments"
        :key="dept"
        :class="['filter-btn', { active: personnelStore.activeDepartment === dept }]"
        @click="personnelStore.setDepartment(dept)"
      >
        {{ deptLabel(dept) }}
      </button>
    </div>

    <!-- Loading -->
    <div v-if="personnelStore.loading" class="loading-state">
      <div class="loading-spinner"></div>
      <p>LOADING PERSONNEL DATABASE...</p>
    </div>

    <!-- Staff Grid -->
    <div v-else class="staff-container">
      <div
        v-for="person in personnelStore.filteredPersonnel"
        :key="person.id"
        :class="['staff-card', { selected: personnelStore.selectedPersonnel?.id === person.id }]"
        @click="personnelStore.selectPersonnel(person)"
      >
        <div class="card-header">
          <div class="card-avatar">
            {{ person.name.charAt(0) }}
          </div>
          <div class="card-status" :class="statusClass(person.status)">
            {{ statusLabel(person.status) }}
          </div>
        </div>
        <div class="card-body">
          <div class="card-name">{{ person.name }}</div>
          <div class="card-role">{{ person.role }}</div>
          <div class="card-dept-tag">{{ deptLabel(person.department) }}</div>
        </div>
        <div class="card-footer">
          <span class="card-detail-hint">▼ 查看详情</span>
        </div>
      </div>

      <div v-if="personnelStore.filteredPersonnel.length === 0" class="empty-state">
        ── 没有匹配的记录 ──
      </div>
    </div>

    <!-- Detail Modal -->
    <Transition name="modal-fade">
      <div v-if="personnelStore.selectedPersonnel" class="modal-overlay" @click.self="closeDetail">
        <div class="modal-panel">
          <button class="modal-close" @click="closeDetail">✕</button>

          <div class="modal-top">
            <div class="modal-avatar-large">
              {{ personnelStore.selectedPersonnel.name.charAt(0) }}
            </div>
            <div class="modal-id-bar">
              <span class="modal-id-label">PERSONNEL ID</span>
              <span class="modal-id-value">#{{ String(personnelStore.selectedPersonnel.id).padStart(4, '0') }}</span>
            </div>
          </div>

          <div class="modal-name">{{ personnelStore.selectedPersonnel.name }}</div>
          <div class="modal-title">{{ personnelStore.selectedPersonnel.title }}</div>

          <div class="modal-meta">
            <div class="meta-item">
              <span class="meta-label">部门</span>
              <span class="meta-value">{{ deptLabel(personnelStore.selectedPersonnel.department) }}</span>
            </div>
            <div class="meta-item">
              <span class="meta-label">职务</span>
              <span class="meta-value">{{ personnelStore.selectedPersonnel.role }}</span>
            </div>
            <div class="meta-item">
              <span class="meta-label">状态</span>
              <span class="meta-value" :class="statusClass(personnelStore.selectedPersonnel.status)">
                {{ statusLabel(personnelStore.selectedPersonnel.status) }}
              </span>
            </div>
          </div>

          <div class="modal-section">
            <div class="modal-section-title">■ 简要档案 / PROFILE</div>
            <p class="modal-bio">{{ personnelStore.selectedPersonnel.bio || '暂无记录。' }}</p>
          </div>

          <div class="modal-section">
            <div class="modal-section-title">■ 详细记录 / DETAILED RECORD</div>
            <p class="modal-lore">{{ personnelStore.selectedPersonnel.loreText || '暂无详细档案。' }}</p>
          </div>

          <div class="modal-footer">
            <span class="modal-footer-note">迦勒底员工档案系统 v2.1 // 数据更新于 {{ new Date().toISOString().slice(0, 10) }}</span>
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import NavBar from '../components/NavBar.vue'
import { usePersonnelStore } from '../stores/personnel'

const personnelStore = usePersonnelStore()

const departmentLabels = {
  'MASTER': '御主',
  'COMBAT': '战斗',
  'RESEARCH': '研究',
  'MEDICAL': '医疗',
  'A-TEAM': 'A组·御主',
  'COMMAND': '管理',
  'ENGINEERING': '技术工程',
  'OPERATIONS': '运营',
}

const statusConfig = {
  'ACTIVE':   { label: 'ACTIVE / 在岗', cls: 'status-active' },
  'INJURED':  { label: 'INJURED / 负伤', cls: 'status-injured' },
  'FROZEN':   { label: 'FROZEN / 冻结', cls: 'status-frozen' },
  'DECEASED': { label: 'DECEASED / 故去', cls: 'status-deceased' },
  'MIA':      { label: 'MIA / 失踪', cls: 'status-mia' },
  'RETIRED':  { label: 'RETIRED / 引退', cls: 'status-retired' },
}

function deptLabel(dept) {
  return departmentLabels[dept] || dept
}

function statusLabel(status) {
  return statusConfig[status]?.label || status
}

function statusClass(status) {
  return statusConfig[status]?.cls || ''
}

function closeDetail() {
  personnelStore.selectedPersonnel = null
}

onMounted(() => {
  personnelStore.fetchPersonnel()
})
</script>

<style scoped>
/* =============================================
   CHALDEA PERSONNEL ARCHIVE — Terminal Aesthetic
   ============================================= */

.staff-page {
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

/* CRT scanline overlay */
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

/* Page Header */
.page-header {
  padding: 66px 32px 8px;
  text-align: left;
  max-width: 1200px;
  margin: 0 auto;
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
.status-ok { color: #4cd964; }

/* Filter Bar */
.filter-bar {
  display: flex;
  gap: 4px;
  padding: 12px 32px;
  max-width: 1200px;
  margin: 0 auto;
  flex-wrap: wrap;
}
.filter-btn {
  padding: 6px 14px;
  background: rgba(255,255,255,0.03);
  border: 1px solid rgba(255,255,255,0.1);
  color: rgba(255,255,255,0.5);
  font-size: 11px;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  letter-spacing: 1px;
  cursor: pointer;
  transition: all 0.2s;
}
.filter-btn:hover {
  background: rgba(255,255,255,0.06);
  color: rgba(255,255,255,0.8);
}
.filter-btn.active {
  background: rgba(87,150,208,0.15);
  border-color: rgba(87,150,208,0.4);
  color: #5796D0;
}

/* Loading */
.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 0;
  gap: 16px;
}
.loading-spinner {
  width: 32px;
  height: 32px;
  border: 2px solid rgba(87,150,208,0.2);
  border-top-color: #5796D0;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}
@keyframes spin {
  to { transform: rotate(360deg); }
}
.loading-state p {
  font-family: 'JetBrains Mono', monospace;
  font-size: 11px;
  color: rgba(255,255,255,0.35);
  letter-spacing: 2px;
}

/* Staff Grid */
.staff-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 12px;
  padding: 4px 32px 32px;
  max-width: 1200px;
  margin: 0 auto;
}

/* Staff Card */
.staff-card {
  background: rgba(0, 0, 0, 0.25);
  border: 1px solid rgba(255,255,255,0.08);
  padding: 16px;
  cursor: pointer;
  transition: all 0.25s ease;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.staff-card:hover {
  border-color: rgba(87,150,208,0.3);
  background: rgba(87,150,208,0.04);
  transform: translateY(-2px);
}
.staff-card.selected {
  border-color: rgba(87,150,208,0.5);
  background: rgba(87,150,208,0.06);
  box-shadow: 0 0 16px rgba(87,150,208,0.08);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.card-avatar {
  width: 36px;
  height: 36px;
  background: rgba(87,150,208,0.15);
  border: 1px solid rgba(87,150,208,0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: 'JetBrains Mono', monospace;
  font-size: 14px;
  color: #5796D0;
}
.card-status {
  font-family: 'JetBrains Mono', monospace;
  font-size: 9px;
  padding: 2px 8px;
  letter-spacing: 1px;
  border-radius: 2px;
}
.status-active   { background: rgba(76,217,100,0.12); color: #4cd964; border: 1px solid rgba(76,217,100,0.2); }
.status-injured  { background: rgba(255,204,0,0.12);  color: #ffcc00; border: 1px solid rgba(255,204,0,0.2); }
.status-frozen   { background: rgba(87,150,208,0.12); color: #5796D0; border: 1px solid rgba(87,150,208,0.2); }
.status-deceased { background: rgba(255,68,68,0.12);  color: #ff4444; border: 1px solid rgba(255,68,68,0.2); }
.status-mia      { background: rgba(170,130,255,0.12); color: #aa82ff; border: 1px solid rgba(170,130,255,0.2); }
.status-retired  { background: rgba(255,255,255,0.06); color: rgba(255,255,255,0.4); border: 1px solid rgba(255,255,255,0.1); }

.card-body {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.card-name {
  font-size: 14px;
  font-weight: 600;
  color: rgb(255,255,255);
  letter-spacing: 0.5px;
}
.card-role {
  font-size: 11px;
  color: rgba(255,255,255,0.5);
}
.card-dept-tag {
  display: inline-block;
  margin-top: 4px;
  font-family: 'JetBrains Mono', monospace;
  font-size: 9px;
  color: rgba(87,150,208,0.6);
  padding: 2px 8px;
  background: rgba(87,150,208,0.06);
  border: 1px solid rgba(87,150,208,0.1);
  align-self: flex-start;
}

.card-footer {
  border-top: 1px solid rgba(255,255,255,0.04);
  padding-top: 8px;
}
.card-detail-hint {
  font-family: 'JetBrains Mono', monospace;
  font-size: 9px;
  color: rgba(255,255,255,0.2);
  letter-spacing: 1px;
  transition: color 0.2s;
}
.staff-card:hover .card-detail-hint {
  color: rgba(87,150,208,0.6);
}

/* Empty state */
.empty-state {
  grid-column: 1 / -1;
  text-align: center;
  font-family: 'JetBrains Mono', monospace;
  font-size: 11px;
  color: rgba(255,255,255,0.2);
  padding: 60px 0;
  letter-spacing: 2px;
}

/* =============================================
   Detail Modal
   ============================================= */
.modal-overlay {
  position: fixed;
  inset: 0;
  z-index: 9999;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
}
.modal-panel {
  width: 640px;
  max-width: 100%;
  max-height: 85vh;
  overflow-y: auto;
  background: rgb(10, 28, 64);
  border: 1px solid rgba(255,255,255,0.15);
  padding: 32px;
  position: relative;
  box-shadow: 0 0 40px rgba(0,0,0,0.4);
}
.modal-close {
  position: absolute;
  top: 12px;
  right: 12px;
  background: none;
  border: none;
  color: rgba(255,255,255,0.3);
  font-size: 16px;
  cursor: pointer;
  transition: color 0.2s;
}
.modal-close:hover { color: rgb(255,255,255); }

.modal-top {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
}
.modal-avatar-large {
  width: 56px;
  height: 56px;
  background: rgba(87,150,208,0.15);
  border: 2px solid rgba(87,150,208,0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: 'JetBrains Mono', monospace;
  font-size: 22px;
  color: #5796D0;
}
.modal-id-bar {
  display: flex;
  flex-direction: column;
  gap: 2px;
}
.modal-id-label {
  font-family: 'JetBrains Mono', monospace;
  font-size: 9px;
  color: rgba(255,255,255,0.3);
  letter-spacing: 2px;
}
.modal-id-value {
  font-family: 'JetBrains Mono', monospace;
  font-size: 13px;
  color: rgba(255,255,255,0.6);
  letter-spacing: 1px;
}

.modal-name {
  font-size: 22px;
  font-weight: 600;
  color: rgb(255,255,255);
  margin-bottom: 4px;
  letter-spacing: 1px;
}
.modal-title {
  font-size: 12px;
  color: rgba(255,255,255,0.5);
  margin-bottom: 20px;
  line-height: 1.5;
}

.modal-meta {
  display: flex;
  gap: 20px;
  margin-bottom: 24px;
  padding: 14px;
  background: rgba(0,0,0,0.2);
  border: 1px solid rgba(255,255,255,0.06);
}
.meta-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.meta-label {
  font-family: 'JetBrains Mono', monospace;
  font-size: 9px;
  color: rgba(255,255,255,0.3);
  letter-spacing: 1px;
}
.meta-value {
  font-size: 12px;
  color: rgba(255,255,255,0.8);
}

.modal-section {
  margin-bottom: 20px;
}
.modal-section-title {
  font-family: 'JetBrains Mono', monospace;
  font-size: 10px;
  color: rgba(87,150,208,0.7);
  letter-spacing: 1px;
  margin-bottom: 8px;
}
.modal-bio,
.modal-lore {
  font-size: 13px;
  line-height: 1.8;
  color: rgba(255,255,255,0.7);
  margin: 0;
  text-align: justify;
}

.modal-footer {
  margin-top: 24px;
  padding-top: 12px;
  border-top: 1px solid rgba(255,255,255,0.06);
}
.modal-footer-note {
  font-family: 'JetBrains Mono', monospace;
  font-size: 9px;
  color: rgba(255,255,255,0.15);
  letter-spacing: 0.5px;
}

/* Modal transition */
.modal-fade-enter-active, .modal-fade-leave-active {
  transition: opacity 0.25s ease;
}
.modal-fade-enter-from, .modal-fade-leave-to {
  opacity: 0;
}

/* Scrollbar styling */
.modal-panel::-webkit-scrollbar { width: 4px; }
.modal-panel::-webkit-scrollbar-track { background: transparent; }
.modal-panel::-webkit-scrollbar-thumb { background: rgba(255,255,255,0.1); }

/* Responsive */
@media (max-width: 768px) {
  .page-header { padding: 60px 16px 8px; }
  .filter-bar { padding: 10px 16px; }
  .staff-container {
    grid-template-columns: 1fr;
    padding: 4px 16px 24px;
  }
  .modal-panel { padding: 24px 16px; }
  .modal-meta { flex-direction: column; gap: 10px; }
}
</style>
