<template>
  <div class="chamber-wrapper">
    <!-- Chamber Header -->
    <div class="chamber-header">
      <div class="chamber-title-row">
        <span class="chamber-icon">◈</span>
        <h3 class="chamber-title">灵子转移舱室 / COFFIN · CRYO CHAMBER</h3>
        <span class="chamber-icon">◈</span>
      </div>
      <p class="chamber-subtitle">A-TEAM · MASTER CRYO PRESERVATION SYSTEM</p>
      <div class="chamber-status-bar">
        <span class="status-dot live" />
        <span class="status-text">生命维持系统 ACTIVE</span>
        <span class="status-sep">|</span>
        <span class="status-dot warn" />
        <span class="status-text">冷舱密封 CONTAINED</span>
        <span class="status-sep">|</span>
        <span class="status-dot" />
        <span class="status-text">灵子修复液循环 ONLINE</span>
      </div>
    </div>

    <!-- Cryo Pods Grid -->
    <div class="pods-grid">
      <div
        v-for="pod in pods"
        :key="pod.id"
        :class="['pod-unit', { selected: selectedPod?.id === pod.id }]"
        @click="selectPod(pod)"
      >
        <!-- Pod Number & ID Tag -->
        <div class="pod-tag">
          <span class="pod-number">#{{ pod.id }}</span>
          <span class="pod-label">冷冻舱 / COFFIN</span>
        </div>

        <!-- Pod Visual (tube/coffin abstract) -->
        <div class="pod-visual">
          <div class="pod-tube">
            <div class="tube-glow" :style="{ opacity: selectedPod?.id === pod.id ? 0.6 : 0.15 }"></div>
            <div class="tube-status-ring" :class="pod.statusClass"></div>
            <div class="master-silhouette">
              <span class="silhouette-initial">{{ pod.initial }}</span>
            </div>
          </div>
        </div>

        <!-- Master Name -->
        <div class="pod-master-name">
          <span class="name-cn">{{ pod.nameCn }}</span>
          <span class="name-en">{{ pod.nameEn }}</span>
        </div>

        <!-- Vital Signs -->
        <div class="pod-vitals">
          <div class="vital-row">
            <span class="vital-label">HR</span>
            <div class="vital-bar">
              <div class="vital-fill" :style="{ width: pod.vitals.hr + '%' }"></div>
            </div>
          </div>
          <div class="vital-row">
            <span class="vital-label">魔力</span>
            <div class="vital-bar">
              <div class="vital-fill magic" :style="{ width: pod.vitals.magic + '%' }"></div>
            </div>
          </div>
        </div>

        <!-- Selected indicator glow -->
        <div v-if="selectedPod?.id === pod.id" class="pod-selected-glow"></div>
      </div>
    </div>

    <!-- Chamber body: grid + info overlay / idle -->
    <div class="chamber-body">
      <div class="pods-grid">
        <div
          v-for="pod in pods"
          :key="pod.id"
          :class="['pod-unit', { selected: selectedPod?.id === pod.id }]"
          @click="selectPod(pod)"
        >
          <!-- Pod Number & ID Tag -->
          <div class="pod-tag">
            <span class="pod-number">#{{ pod.id }}</span>
            <span class="pod-label">冷冻舱 / COFFIN</span>
          </div>

          <!-- Pod Visual (tube/coffin abstract) -->
          <div class="pod-visual">
            <div class="pod-tube">
              <div class="tube-glow" :style="{ opacity: selectedPod?.id === pod.id ? 0.6 : 0.15 }"></div>
              <div class="tube-status-ring" :class="pod.statusClass"></div>
              <div class="master-silhouette">
                <span class="silhouette-initial">{{ pod.initial }}</span>
              </div>
            </div>
          </div>

          <!-- Master Name -->
          <div class="pod-master-name">
            <span class="name-cn">{{ pod.nameCn }}</span>
            <span class="name-en">{{ pod.nameEn }}</span>
          </div>

          <!-- Vital Signs -->
          <div class="pod-vitals">
            <div class="vital-row">
              <span class="vital-label">HR</span>
              <div class="vital-bar">
                <div class="vital-fill" :style="{ width: pod.vitals.hr + '%' }"></div>
              </div>
            </div>
            <div class="vital-row">
              <span class="vital-label">魔力</span>
              <div class="vital-bar">
                <div class="vital-fill magic" :style="{ width: pod.vitals.magic + '%' }"></div>
              </div>
            </div>
          </div>

          <!-- Selected indicator glow -->
          <div v-if="selectedPod?.id === pod.id" class="pod-selected-glow"></div>
        </div>
      </div>

      <!-- Info Panel — overlaid at the bottom of the chamber -->
      <transition name="info-fade">
        <div v-if="selectedPod" class="info-panel" @click.stop>
          <div class="info-panel-header">
            <div class="info-panel-title-row">
              <span class="info-pod-badge">#{{ selectedPod.id }}</span>
              <h4 class="info-master-name">{{ selectedPod.nameCn }}</h4>
              <span class="info-name-en">{{ selectedPod.nameEn }}</span>
            </div>
            <button class="info-close" @click="selectedPod = null">✕</button>
          </div>

          <div class="info-divider"></div>

          <div class="info-sections">
            <!-- 御主适性 -->
            <div class="info-section">
              <div class="section-label-row">
                <span class="section-icon">◆</span>
                <span class="section-label">御主适性 / MASTER COMPATIBILITY</span>
                <span class="section-rating">{{ selectedPod.suitability }}</span>
              </div>
              <p class="section-text">{{ selectedPod.suitabilityDesc }}</p>
            </div>

            <!-- 后勤记录 -->
            <div class="info-section">
              <div class="section-label-row">
                <span class="section-icon">◆</span>
                <span class="section-label">后勤记录 / LOGISTICS RECORD</span>
              </div>
              <p class="section-text">{{ selectedPod.logistics }}</p>
            </div>

            <!-- 当前冷冻监控 -->
            <div class="info-section">
              <div class="section-label-row">
                <span class="section-icon">◆</span>
                <span class="section-label">当前冷冻监控 / CRYO MONITOR</span>
              </div>
              <p class="section-text">{{ selectedPod.cryoMonitor }}</p>
            </div>
          </div>

          <!-- System footer -->
          <div class="info-footer">
            <span class="footer-line">CHALDEAS RECORD · A-TEAM PERSONNEL FILE</span>
            <span class="footer-line">LAST UPDATED: 2015-12-31 · SHELTER PROTOCOL</span>
          </div>
        </div>
      </transition>

      <!-- Idle hint (when nothing selected) -->
      <div v-if="!selectedPod" class="chamber-idle">
        <p class="idle-text">► SELECT A COFFIN TO VIEW MASTER RECORDS</p>
        <p class="idle-sub">点击冷冻舱查看御主详细档案</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const pods = [
  {
    id: 1,
    nameCn: '基尔什塔利亚·沃代姆',
    nameEn: 'Kirschtaria Wodime',
    initial: 'K',
    statusClass: 'stable',
    suitability: 'A+',
    suitabilityDesc: '拟定A组总队长。',
    logistics: '时钟塔十二科之首"天体科"的主席领主继承人。根据天体科的硬核魔术测算，他是全方位无死角的绝对天才，具备极高顺位的星空测算魔力回路。在模拟战中，他展现出了近乎恐怖的指挥艺术。',
    cryoMonitor: '生命体征微弱但极其稳定，其强大的魔术回路正在冷舱中以极低的频率产生拟似共鸣。他是雷夫教授炸弹袭击中最核心的针对目标。',
    vitals: { hr: 28, magic: 95 },
  },
  {
    id: 2,
    nameCn: '芥雏子',
    nameEn: 'Hinako Akuta',
    initial: 'H',
    statusClass: 'anomaly',
    suitability: 'A',
    suitabilityDesc: '植物科推荐。',
    logistics: '医疗部特招的灵子转移天才，自称是时钟塔植物科出身。不爱交际，历史测试成绩极其优秀，尤其精通中国古代史。但在常规体检中，她的多项生理指标（如血液活性、代谢周期）呈现出极其诡异的静止状态。',
    cryoMonitor: '极度异常。冷舱显示她的肉体几乎没有受到低温冻伤的负面影响，细胞活性在零下数十度依然维持在满额状态，医疗部甚至无法解析她肉体的深层结构。',
    vitals: { hr: 12, magic: 99 },
  },
  {
    id: 3,
    nameCn: '卡多克·泽姆露普斯',
    nameEn: 'Kadoc Zemlupus',
    initial: 'K',
    statusClass: 'critical',
    suitability: 'A-',
    suitabilityDesc: '技术同步率极高。',
    logistics: '边缘魔术家族出身的平民。虽然纯粹的魔术回路数量在A组垫底，但他对灵子转移的"间隙"有着异乎常人的敏感度，在英灵精神同步率测试中拿到了迦勒底最高分。擅长细微的"兽性魔术"和咒符拆解。',
    cryoMonitor: '肉体受损严重。由于回路较为脆弱，雷夫教授的爆炸对他的肉体造成了毁灭性打击，目前全靠冷舱的灵子修复液勉强维持心跳。',
    vitals: { hr: 15, magic: 40 },
  },
  {
    id: 4,
    nameCn: '奥菲莉娅·法姆索罗尼',
    nameEn: 'Ophelia Phamrsolone',
    initial: 'O',
    statusClass: 'sealed',
    suitability: 'A',
    suitabilityDesc: '降灵科极密培养。',
    logistics: '时钟塔降灵科引以为傲的精英。她生来便拥有一只被时钟塔高度保密的特异器官——宝石级的"迁延之魔眼"。在硬核设定中，这只魔眼能够通过视线强行干涉可能性的走向，将概率锁定。',
    cryoMonitor: '魔眼部分已被迦勒底技术部实施最高级别的术式封印，防止其在无意识的弥留状态下释放概率扭曲，干扰冷舱整体的电力平衡。',
    vitals: { hr: 32, magic: 88 },
  },
  {
    id: 5,
    nameCn: '斯堪的纳维亚·佩佩隆奇诺',
    nameEn: 'Peperoncino',
    initial: 'P',
    statusClass: 'active',
    suitability: 'A',
    suitabilityDesc: '海外特招自由人。',
    logistics: '没有任何传统魔术名门背景的自由魔术师。虽然平时举止轻浮、自称大姐头，但保密局档案显示他极度危险。他精通神智学、修验道以及佛教古老暗杀术，具有断绝因果和操纵局部空间缝隙的硬核实战技巧。',
    cryoMonitor: '意志监控显示极其活跃。即使肉体在冷舱中被完全冻结，他的精神波形依然保持着某种类似"禅定"的深层呼吸律动，正在自主抵抗死亡。',
    vitals: { hr: 22, magic: 75 },
  },
  {
    id: 6,
    nameCn: '贝利尔·古特',
    nameEn: 'Beryl Gut',
    initial: 'B',
    statusClass: 'danger',
    suitability: 'A',
    suitabilityDesc: '传授科特工。',
    logistics: '时钟塔传授科出身，外号"狼人"。在迦勒底的评测中，他是纯粹的战斗与诅咒术专家。他不具备宏大的魔术理论，但极其擅长利用非人混血的特异体质将魔力转化为致命的毒素与诅咒，是一个纯粹为了在绝境中撕裂敌人而准备的"脏活"执行者。',
    cryoMonitor: '脑波极其混乱，在冷冻状态下仍不时爆发出带有强烈攻击性的愉悦波形。技术部已对其冷舱加装了三重神经抑制剂。',
    vitals: { hr: 55, magic: 82 },
  },
  {
    id: 7,
    nameCn: '戴维·布鲁布克',
    nameEn: 'Daybit Sem Void',
    initial: 'D',
    statusClass: 'unknown',
    suitability: '无法测定',
    suitabilityDesc: '传承科异类。',
    logistics: '来自时钟塔最神秘的"传承科"。他是一个没有任何情感起伏的绝对理性怪物。此人在迦勒底的模拟测试中展现出了令人毛骨悚然的计算力——他能以极低阶的术式构筑，通过绝对精准的战术推演，在模拟战中完美反杀高位神秘。',
    cryoMonitor: '他的冷舱是整个管制室里最安静的一具。无论是心率、脑电波还是魔力逸散度，全部精准地卡在死亡线之上的1%处，就像一个精准闭合的高精密机械，静静地等待着复苏的指令。',
    vitals: { hr: 5, magic: 99 },
  },
]

const selectedPod = ref(null)

function selectPod(pod) {
  selectedPod.value = selectedPod.value?.id === pod.id ? null : pod
}
</script>

<style scoped>
/* =============================================
   Cryo Chamber — CHALDEAS Spiritron Transfer
   ============================================= */

.chamber-wrapper {
  background: rgb(6, 18, 42);
  border: 1px solid rgba(87, 150, 208, 0.2);
  position: relative;
  display: flex;
  flex-direction: column;
  height: 100%;
}

/* Chamber Header */
.chamber-header {
  padding: 14px 18px 10px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  flex-shrink: 0;
}
.chamber-title-row {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
}
.chamber-icon {
  color: rgba(87, 150, 208, 0.3);
  font-size: 12px;
}
.chamber-title {
  font-size: 12px;
  font-weight: 500;
  letter-spacing: 3px;
  margin: 0;
  color: rgb(255, 255, 255);
  text-align: center;
}
.chamber-subtitle {
  text-align: center;
  font-size: 9px;
  color: rgba(255, 255, 255, 0.2);
  margin: 4px 0 8px;
  letter-spacing: 2px;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}
.chamber-status-bar {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-size: 9px;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  color: rgba(255, 255, 255, 0.3);
}
.status-dot {
  width: 5px;
  height: 5px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.15);
}
.status-dot.live {
  background: #4ade80;
  box-shadow: 0 0 4px rgba(74, 222, 128, 0.5);
}
.status-dot.warn {
  background: #fbbf24;
  box-shadow: 0 0 4px rgba(251, 191, 36, 0.4);
}
.status-text {
  font-size: 8px;
  letter-spacing: 1px;
}
.status-sep {
  color: rgba(255, 255, 255, 0.1);
}

/* Pods Grid */
.pods-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
  padding: 12px 14px;
  flex: 1;
}

/* Individual Pod */
.pod-unit {
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid rgba(255, 255, 255, 0.06);
  padding: 10px 10px 12px;
  cursor: pointer;
  position: relative;
  transition: all 0.25s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
  overflow: hidden;
}
.pod-unit:hover {
  background: rgba(87, 150, 208, 0.06);
  border-color: rgba(87, 150, 208, 0.25);
}
.pod-unit.selected {
  background: rgba(87, 150, 208, 0.08);
  border-color: rgba(87, 150, 208, 0.4);
  box-shadow: inset 0 0 20px rgba(87, 150, 208, 0.05);
}

/* Pod Tag */
.pod-tag {
  display: flex;
  align-items: center;
  gap: 6px;
  width: 100%;
}
.pod-number {
  font-size: 10px;
  font-weight: 600;
  color: rgba(87, 150, 208, 0.7);
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}
.pod-label {
  font-size: 7px;
  color: rgba(255, 255, 255, 0.15);
  letter-spacing: 1px;
  font-family: 'JetBrains Mono', monospace;
  text-transform: uppercase;
}

/* Pod Visual */
.pod-visual {
  display: flex;
  justify-content: center;
  padding: 4px 0;
}
.pod-tube {
  width: 52px;
  height: 56px;
  border: 1px solid rgba(87, 150, 208, 0.2);
  background: rgba(0, 20, 60, 0.4);
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  border-radius: 2px;
}
.tube-glow {
  position: absolute;
  inset: 0;
  background: radial-gradient(ellipse at center, rgba(87, 150, 208, 0.15) 0%, transparent 70%);
  transition: opacity 0.4s ease;
}
.tube-status-ring {
  position: absolute;
  top: 3px;
  right: 3px;
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.15);
}
.tube-status-ring.stable {
  background: #4ade80;
  box-shadow: 0 0 6px rgba(74, 222, 128, 0.4);
  animation: pulse-dot 2s ease-in-out infinite;
}
.tube-status-ring.anomaly {
  background: #fbbf24;
  box-shadow: 0 0 8px rgba(251, 191, 36, 0.5);
  animation: pulse-dot 1.5s ease-in-out infinite;
}
.tube-status-ring.critical {
  background: #ef4444;
  box-shadow: 0 0 8px rgba(239, 68, 68, 0.5);
  animation: pulse-dot 0.8s ease-in-out infinite;
}
.tube-status-ring.sealed {
  background: #a78bfa;
  box-shadow: 0 0 6px rgba(167, 139, 250, 0.4);
}
.tube-status-ring.active {
  background: #60a5fa;
  box-shadow: 0 0 6px rgba(96, 165, 250, 0.4);
  animation: pulse-dot 1.2s ease-in-out infinite;
}
.tube-status-ring.danger {
  background: #f97316;
  box-shadow: 0 0 10px rgba(249, 115, 22, 0.5);
  animation: pulse-dot 0.6s ease-in-out infinite;
}
.tube-status-ring.unknown {
  background: rgba(255, 255, 255, 0.3);
}

.master-silhouette {
  width: 28px;
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  background: rgba(87, 150, 208, 0.08);
  border: 1px solid rgba(87, 150, 208, 0.15);
}
.silhouette-initial {
  font-family: 'Georgia', serif;
  font-size: 16px;
  font-weight: 700;
  color: rgba(255, 255, 255, 0.25);
  letter-spacing: 0;
}

/* Master Name */
.pod-master-name {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1px;
  width: 100%;
}
.name-cn {
  font-size: 11px;
  color: rgb(255, 255, 255);
  text-align: center;
  line-height: 1.3;
  font-weight: 500;
}
.name-en {
  font-size: 8px;
  color: rgba(255, 255, 255, 0.3);
  font-family: 'Georgia', serif;
  text-align: center;
  letter-spacing: 0.5px;
}

/* Vital Signs */
.pod-vitals {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 2px;
  margin-top: 2px;
}
.vital-row {
  display: flex;
  align-items: center;
  gap: 4px;
}
.vital-label {
  font-size: 7px;
  color: rgba(255, 255, 255, 0.2);
  font-family: 'JetBrains Mono', monospace;
  width: 18px;
  text-align: right;
}
.vital-bar {
  flex: 1;
  height: 3px;
  background: rgba(255, 255, 255, 0.04);
  border: 1px solid rgba(255, 255, 255, 0.04);
  overflow: hidden;
}
.vital-fill {
  height: 100%;
  background: #4ade80;
  transition: width 0.6s ease;
  box-shadow: 0 0 3px rgba(74, 222, 128, 0.2);
}
.vital-fill.magic {
  background: #a78bfa;
  box-shadow: 0 0 3px rgba(167, 139, 250, 0.2);
}

/* Selected glow */
.pod-selected-glow {
  position: absolute;
  inset: -1px;
  border: 1px solid rgba(87, 150, 208, 0.3);
  pointer-events: none;
  animation: glow-pulse 1.5s ease-in-out infinite;
}

/* Info Panel — contained within the chamber */
.info-panel {
  margin: 0 14px 14px;
  border: 1px solid rgba(87, 150, 208, 0.2);
  background: rgba(6, 18, 42, 0.95);
  flex-shrink: 0;
  position: relative;
  z-index: 2;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.3);
}
.info-panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 14px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
}
.info-panel-title-row {
  display: flex;
  align-items: center;
  gap: 8px;
}
.info-pod-badge {
  font-size: 9px;
  font-family: 'JetBrains Mono', monospace;
  color: rgba(87, 150, 208, 0.7);
  border: 1px solid rgba(87, 150, 208, 0.2);
  padding: 1px 5px;
}
.info-master-name {
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
  margin: 0;
}

/* Info Sections */
.info-sections {
  padding: 10px 14px 8px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-height: 220px;
  overflow-y: auto;
}
.info-section { }
.section-label-row {
  display: flex;
  align-items: center;
  gap: 5px;
  margin-bottom: 3px;
}
.section-icon {
  font-size: 7px;
  color: rgba(87, 150, 208, 0.5);
}
.section-label {
  font-size: 9px;
  color: rgba(87, 150, 208, 0.7);
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  letter-spacing: 1px;
  font-weight: 500;
}
.section-rating {
  margin-left: auto;
  font-size: 10px;
  font-weight: 600;
  color: #fbbf24;
  font-family: 'JetBrains Mono', monospace;
  border: 1px solid rgba(251, 191, 36, 0.2);
  padding: 0 6px;
}
.section-text {
  font-size: 10.5px;
  color: rgba(255, 255, 255, 0.5);
  line-height: 1.7;
  margin: 0;
  letter-spacing: 0.2px;
}

/* Info Footer */
.info-footer {
  display: flex;
  justify-content: space-between;
  padding: 6px 14px;
  border-top: 1px solid rgba(255, 255, 255, 0.04);
}
.footer-line {
  font-size: 7px;
  color: rgba(255, 255, 255, 0.1);
  font-family: 'JetBrains Mono', monospace;
  letter-spacing: 1px;
}

/* Idle state */
.chamber-idle {
  padding: 14px;
  text-align: center;
  flex-shrink: 0;
  border-top: 1px solid rgba(255, 255, 255, 0.03);
}
.idle-text {
  font-size: 9px;
  color: rgba(255, 255, 255, 0.15);
  font-family: 'JetBrains Mono', monospace;
  letter-spacing: 2px;
  margin: 0;
  animation: blink-text 2s step-end infinite;
}
.idle-sub {
  font-size: 9px;
  color: rgba(255, 255, 255, 0.08);
  margin: 4px 0 0;
  letter-spacing: 1px;
}

/* Transitions */
.info-fade-enter-active,
.info-fade-leave-active {
  transition: all 0.25s ease;
}
.info-fade-enter-from,
.info-fade-leave-to {
  opacity: 0;
  transform: translateY(6px);
}

/* Animations */
@keyframes pulse-dot {
  0%, 100% { opacity: 0.6; }
  50% { opacity: 1; }
}
@keyframes glow-pulse {
  0%, 100% { opacity: 0.3; }
  50% { opacity: 1; }
}
@keyframes blink-text {
  0%, 100% { opacity: 0.15; }
  50% { opacity: 0.05; }
}
</style>
