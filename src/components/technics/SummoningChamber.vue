<template>
  <div class="summoning-chamber">
    <FrostedPanel title="英灵召唤室 / SUMMONING CHAMBER" variant="terminal" class="room-panel">
      <div class="chamber-layout">
        <!-- Left: Summoning Area -->
        <div class="summon-area">
          <div class="catalyst-display">
            <div class="shield-icon">
              <div class="shield-hex"></div>
              <span class="shield-label">C•A•T•A•L•Y•S•T</span>
              <span class="shield-sub">Lord Chaldeas</span>
            </div>
          </div>

          <SummoningCircle ref="circleRef" :isActive="isSummoning" />

          <div class="summon-controls">
            <button
              class="summon-btn"
              :disabled="isSummoning"
              @click="startSummon"
            >
              {{ isSummoning ? '召唤进行中...' : '■ BEGIN SUMMONING / 灵基召唤' }}
            </button>
          </div>
        </div>

        <!-- Right: Summon Result -->
        <div class="result-area">
          <FrostedPanel title="召唤结果 / SUMMON RESULT" variant="terminal" class="result-panel">
            <!-- Result display -->
            <div v-if="summonedServant" class="summon-result">
              <div class="result-header">
                <h3 class="result-name">{{ summonedServant.name }}</h3>
                <span class="result-class">{{ summonedServant.className }}</span>
              </div>
              <p class="result-rarity">{{ '★'.repeat(summonedServant.rarity) }}</p>
              <p class="result-title">{{ summonedServant.title }}</p>
              <div class="stats-grid">
                <div v-for="stat in statList" :key="stat.key" class="stat-row">
                  <span class="stat-label">{{ stat.label }}</span>
                  <div class="stat-bar-track">
                    <div class="stat-bar-fill" :style="{ width: (getStat(stat.key) / 10 * 100) + '%' }"></div>
                  </div>
                  <span class="stat-value">{{ getStat(stat.key) }}/10</span>
                </div>
              </div>
              <p class="result-desc">{{ summonedServant.description }}</p>
            </div>
            <div v-else class="summon-hint">
              <p>█ 等待灵基召唤... SELECT A SERVANT TO SUMMON</p>
              <p class="hint-sub">点击按钮启动守护英灵召唤系统·FATE</p>
            </div>
          </FrostedPanel>
        </div>
      </div>
    </FrostedPanel>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import FrostedPanel from '../FrostedPanel.vue'
import SummoningCircle from './SummoningCircle.vue'
import { useServantStore } from '../../stores/servant'

const servantStore = useServantStore()
const isSummoning = ref(false)
const summonedServant = ref(null)

const statList = [
  { key: 'strength', label: 'STR 筋力' },
  { key: 'endurance', label: 'END 耐久' },
  { key: 'agility', label: 'AGI 敏捷' },
  { key: 'magic', label: 'MAG 魔力' },
  { key: 'luck', label: 'LUK 幸运' },
  { key: 'np', label: 'NP 宝具' },
]

function getStat(key) {
  return summonedServant.value?.[key] ?? 0
}

async function startSummon() {
  if (servantStore.servants.length === 0) {
    await servantStore.fetchServants()
  }
  if (servantStore.servants.length === 0) return

  isSummoning.value = true
  summonedServant.value = null

  // Simulate summoning animation delay
  await new Promise(resolve => setTimeout(resolve, 2500))

  const idx = Math.floor(Math.random() * servantStore.servants.length)
  summonedServant.value = servantStore.servants[idx]
  isSummoning.value = false
}

onMounted(() => {
  if (servantStore.servants.length === 0) servantStore.fetchServants()
})
</script>

<style scoped>
.summoning-chamber { height: 100%; }
.room-panel { height: 100%; }

.chamber-layout {
  display: flex;
  gap: 16px;
  height: 100%;
}

/* Left summon area */
.summon-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 14px;
}

/* Catalyst shield */
.catalyst-display {
  display: flex;
  justify-content: center;
  margin-bottom: 4px;
}
.shield-icon {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}
.shield-hex {
  width: 48px;
  height: 48px;
  clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
  background: linear-gradient(135deg, rgba(87,150,208,0.2), rgba(255,255,255,0.05));
  border: 1px solid rgba(87,150,208,0.4);
  box-shadow: 0 0 12px rgba(87,150,208,0.2);
}
.shield-label {
  font-size: 7px;
  font-family: 'JetBrains Mono', monospace;
  letter-spacing: 3px;
  color: rgba(87,150,208,0.6);
}
.shield-sub {
  font-size: 7px;
  font-family: 'JetBrains Mono', monospace;
  color: rgba(255,255,255,0.3);
}

/* Summon button */
.summon-controls { margin-top: 4px; }
.summon-btn {
  padding: 10px 28px;
  font-size: 11px;
  font-family: 'JetBrains Mono', monospace;
  letter-spacing: 2px;
  background: rgba(87,150,208,0.12);
  border: 1px solid rgba(87,150,208,0.4);
  color: #5796D0;
  cursor: pointer;
  transition: all 0.3s;
  text-transform: uppercase;
}
.summon-btn:hover:not(:disabled) {
  background: rgba(87,150,208,0.25);
  border-color: #7bb8ff;
  box-shadow: 0 0 20px rgba(87,150,208,0.2);
}
.summon-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

/* Right: Summon result panel */
.result-area { flex: 0 0 260px;}
.result-panel { height: 430px !important; }

/* Summon result */
.summon-result {
  width: 100%;
}
.result-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 4px;
}
.result-name {
  font-size: 14px;
  font-weight: 600;
  margin: 0;
  color: rgb(255,255,255);
}
.result-class {
  font-size: 9px;
  padding: 2px 8px;
  border: 1px solid rgba(255,255,255,0.25);
  color: rgba(255,255,255,0.8);
  letter-spacing: 1px;
}
.result-rarity {
  font-size: 11px;
  color: rgba(255,215,0,0.7);
  margin: 4px 0;
  letter-spacing: 3px;
}
.result-title {
  font-size: 10px;
  color: rgba(255,255,255,0.5);
  margin: 4px 0;
}
.stats-grid { display: flex; flex-direction: column; gap: 5px; margin: 8px 0; }
.stat-row {
  display: flex;
  align-items: center;
  gap: 8px;
}
.stat-label {
  width: 72px;
  font-size: 9px;
  color: rgba(255,255,255,0.5);
  flex-shrink: 0;
  font-family: 'JetBrains Mono', monospace;
}
.stat-bar-track {
  flex: 1;
  height: 4px;
  background: rgba(255,255,255,0.06);
  border: 1px solid rgba(255,255,255,0.08);
  overflow: hidden;
}
.stat-bar-fill {
  height: 100%;
  background: #5796D0;
  transition: width 0.5s;
}
.stat-value {
  width: 30px;
  font-size: 9px;
  color: rgba(255,255,255,0.6);
  text-align: right;
  font-family: 'JetBrains Mono', monospace;
}
.result-desc {
  font-size: 10px;
  color: rgba(255,255,255,0.4);
  line-height: 1.6;
  margin: 6px 0 0;
  max-height: 180px;
  overflow-y: auto;
}

/* Summon hint */
.summon-hint {
  text-align: center;
  padding: 12px 0;
}
.summon-hint p {
  font-family: 'JetBrains Mono', monospace;
  font-size: 10px;
  color: rgba(255,255,255,0.3);
  margin: 2px 0;
}
.hint-sub {
  font-size: 9px !important;
  color: rgba(255,255,255,0.2) !important;
}

@keyframes fadeIn {
  0% { opacity: 0; transform: translateY(8px); }
  100% { opacity: 1; transform: translateY(0); }
}

@media (max-width: 900px) {
  .chamber-layout { flex-direction: column; }
  .result-area { flex: none; width: 100%; }
}
</style>
