<template>
  <div class="technics-page">
    <!-- CRT scanline overlay -->
    <div class="scanline-overlay"></div>
    <NavBar theme="dark" />

    <div class="page-header">
      <div class="sys-activation">
        <p class="sys-line"><span class="sys-prompt">></span> 灵子转移与技术整备区 // TECHNICAL & SUMMONING FACILITIES</p>
        <p class="sys-line"><span class="sys-prompt">></span> 系统状态：<span class="status-ok">ALL SYSTEMS OPERATIONAL</span> | 管制室下层设施已解锁</p>
      </div>
    </div>

    <!-- Tab Navigation -->
    <div class="tab-bar">
      <button
        v-for="tab in tabs"
        :key="tab.id"
        :class="['tab-btn', { active: activeTab === tab.id }]"
        @click="switchTab(tab.id)"
      >
        <span class="tab-label">{{ tab.label }}</span>
        <span class="tab-sub">{{ tab.subtitle }}</span>
      </button>
    </div>

    <!-- Tab Content -->
    <div class="tab-content">
      <KeepAlive>
        <CoffinRoom v-if="activeTab === 'coffin'" />
        <SummoningChamber v-else-if="activeTab === 'summon'" />
        <DaVinciWorkshop v-else-if="activeTab === 'workshop'" />
        <SupercomputerRoom v-else-if="activeTab === 'computer'" />
      </KeepAlive>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import NavBar from '../components/NavBar.vue'
import CoffinRoom from '../components/technics/CoffinRoom.vue'
import SummoningChamber from '../components/technics/SummoningChamber.vue'
import DaVinciWorkshop from '../components/technics/DaVinciWorkshop.vue'
import SupercomputerRoom from '../components/technics/SupercomputerRoom.vue'

const route = useRoute()
const router = useRouter()

const tabs = [
  { id: 'coffin', label: 'COFFIN', subtitle: '灵子转移舱室' },
  { id: 'summon', label: 'SUMMON', subtitle: '英灵召唤室' },
  { id: 'workshop', label: 'WORKSHOP', subtitle: '达芬奇工房' },
  { id: 'computer', label: 'COMPUTER', subtitle: '超级计算机机房' },
]

const activeTab = ref('coffin')

// Sync from URL query param on mount
onMounted(() => {
  const tab = route.query.tab
  if (tab && tabs.find(t => t.id === tab)) {
    activeTab.value = tab
  }
})

function switchTab(id) {
  activeTab.value = id
  router.replace({ query: { tab: id } })
}

// Watch for external query changes (browser back/forward)
watch(() => route.query.tab, (newTab) => {
  if (newTab && tabs.find(t => t.id === newTab) && newTab !== activeTab.value) {
    activeTab.value = newTab
  }
})
</script>

<style scoped>
.technics-page {
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

/* Page Header */
.page-header {
  padding: 66px 32px 8px;
  text-align: left;
  max-width: 1100px;
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

/* Tab Bar */
.tab-bar {
  display: flex;
  gap: 2px;
  padding: 10px 32px 0;
  max-width: 1100px;
  margin: 0 auto;
}
.tab-btn {
  flex: 1;
  padding: 10px 14px;
  background: transparent;
  border: 1px solid rgba(255,255,255,0.08);
  border-bottom: none;
  color: rgba(255,255,255,0.4);
  cursor: pointer;
  transition: all 0.25s;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
  font-family: inherit;
}
.tab-btn:hover {
  color: rgba(255,255,255,0.7);
  border-color: rgba(255,255,255,0.15);
  background: rgba(255,255,255,0.02);
}
.tab-btn.active {
  color: rgb(255,255,255);
  border-color: rgba(255,255,255,0.2);
  background: rgba(255,255,255,0.04);
  border-bottom: 1px solid rgb(8, 24, 56);
  margin-bottom: -1px;
}
.tab-label {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 11px;
  letter-spacing: 2px;
}
.tab-sub {
  font-size: 9px;
  color: inherit;
  opacity: 0.6;
}

/* Tab Content */
.tab-content {
  padding: 14px 32px 24px;
  max-width: 1100px;
  margin: 0 auto;
  min-height: calc(100vh - 200px);
}

/* Responsive */
@media (max-width: 768px) {
  .page-header { padding: 60px 16px 8px; }
  .tab-bar { padding: 8px 16px 0; flex-wrap: wrap; }
  .tab-btn { flex: 0 0 calc(50% - 1px); }
  .tab-content { padding: 12px 16px 24px; }
}
</style>
