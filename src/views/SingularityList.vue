<template>
  <div class="page">
    <div class="scanline-overlay"></div>
    <NavBar theme="dark" />

    <div class="container">
      <div class="top-nav-bar">
        <router-link to="/portal" class="back-link">← OBSERVATION PORTAL</router-link>
        <span class="top-nav-title">SINGULARITY ARCHIVE</span>
      </div>
      <header class="header">
        <div class="header-title">
          <p>Chaldea Grand Order Terminal</p>
          <h1>人理奠基档案 · 七大特异点修正全志</h1>
        </div>
        <div class="header-status">
          SYSTEM: TRISMEGISTUS I // CHALDEA BASE<br>
          人理定基状态: <span>SECURED // 稳定</span>
        </div>
      </header>

      <main class="report-list">
        <router-link to="/singularity-logs/1" class="row-card">
          <div class="card-header">
            <div class="meta-left">LEYSHIFT COORD: 47°02'N 2°23'E // 灵子转移成功</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>CORRECTED // 已修正</span></div>
          </div>
          <h2 class="card-main-title">邪龙百年战争 · 奥尔良</h2>
          <div class="card-date">时空节点：A.D. 1431 // 人理定基值：C++</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">特异点编号</div>
              <div class="param-value highlight">First Singularity</div>
            </div>
            <div class="param-group">
              <div class="param-label">主要威胁级别</div>
              <div class="param-value">Rank C</div>
            </div>
            <div class="param-group">
              <div class="param-label">核心异变源</div>
              <div class="param-value highlight" style="color: #5796D0;">龙之魔女 (吉尔·德·雷)</div>
            </div>
            <div class="param-group">
              <div class="param-label">圣杯持有者</div>
              <div class="param-value">贞德[Alter]</div>
            </div>
            <div class="param-group">
              <div class="param-label">关键协力英灵</div>
              <div class="param-value ellipsis">贞德、玛丽 · 安托瓦内特</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">【特异点观测报告 / CHALDEA MISSION LOG】</div>
            <p class="summary-text">
              百年战争时期的法兰西。因吉尔·德·雷利用圣杯对泛人类史进行报复，复活了虚构的"黑化贞德"，并召唤了大量双足飞龙对国土进行毁灭性屠杀。迦勒底遭遇了极其严酷的巨龙遭遇战。在正统圣女贞德、元帅吉尔（少年）以及法兰西本土英灵的协助下，成功突破邪龙军团。最终击败黑贞德，回收圣杯，法兰西人理定基回归正轨。
            </p>
          </div>
        </router-link>

        <router-link to="/singularity-logs/2" class="row-card">
          <div class="card-header">
            <div class="meta-left">LEYSHIFT COORD: 41°54'N 12°29'E // 灵子转移成功</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>CORRECTED // 已修正</span></div>
          </div>
          <h2 class="card-main-title">永续疯狂帝国 · 七丘之城</h2>
          <div class="card-date">时空节点：A.D. 0060 // 人理定基值：B+</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">特异点编号</div>
              <div class="param-value highlight">Second Singularity</div>
            </div>
            <div class="param-group">
              <div class="param-label">主要威胁级别</div>
              <div class="param-value">Rank B</div>
            </div>
            <div class="param-group">
              <div class="param-label">核心异变源</div>
              <div class="param-value highlight" style="color: #5796D0;">联合罗马帝国 (雷夫 · 化勒)</div>
            </div>
            <div class="param-group">
              <div class="param-label">圣杯持有者</div>
              <div class="param-value">阿蒂拉 (被雷夫强行召唤)</div>
            </div>
            <div class="param-group">
              <div class="param-label">关键协力英灵</div>
              <div class="param-value ellipsis">尼禄 · 克劳狄乌斯、布狄卡</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">【特异点观测报告 / CHALDEA MISSION LOG】</div>
            <p class="summary-text">
              罗马帝国鼎盛时期。魔神柱雷夫携圣杯介入，挑动已故的历代罗马先帝（凯撒、卡利古拉、罗穆路斯等）建立"联合帝国"，意图摧毁尼禄统治的现世罗马。迦勒底以客将身份加入尼禄治下的罗马正规军，御驾亲征。在清剿多方叛军、击溃魔神柱佛劳洛斯后，迎击被圣杯强行召唤的"文明毁灭者"阿蒂拉。最终顺利保障了罗马帝国的延续。
            </p>
          </div>
        </router-link>

        <router-link to="/singularity-logs/3" class="row-card">
          <div class="card-header">
            <div class="meta-left">LEYSHIFT COORD: 25°00'N 45°00'W // 灵子转移成功</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>CORRECTED // 已修正</span></div>
          </div>
          <h2 class="card-main-title">封锁终局四海 · 俄刻阿诺斯</h2>
          <div class="card-date">时空节点：A.D. 1573 // 人理定基值：A</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">特异点编号</div>
              <div class="param-value highlight">Third Singularity</div>
            </div>
            <div class="param-group">
              <div class="param-label">主要威胁级别</div>
              <div class="param-value">Rank A</div>
            </div>
            <div class="param-group">
              <div class="param-label">核心异变源</div>
              <div class="param-value highlight" style="color: #5796D0;">神代魔女的美食大计 (黑胡子/美狄亚[Lily])</div>
            </div>
            <div class="param-group">
              <div class="param-label">圣杯持有者</div>
              <div class="param-value">爱德华 · 蒂奇 → 赫克托耳</div>
            </div>
            <div class="param-group">
              <div class="param-label">关键协力英灵</div>
              <div class="param-value ellipsis">弗朗西斯 · 德雷克、阿斯托尔福</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">【特异点观测报告 / CHALDEA MISSION LOG】</div>
            <p class="summary-text">
              大航海时代的无尽汪洋。由于圣杯引发的时空扭曲，该海域演变成了神代神话与历史海盗杂糅的"断绝之海"。狂热的海盗王黑胡子夺取圣杯企图扩充后宫，实则背后受到美狄亚Lily与魔神柱的反向利用，意图献祭希腊神话中的巨神赫拉克勒斯。迦勒底登上了星之开拓者德雷克船长的"黄金鹿号"，展开了激烈的接舷海战。最终击破神代魔物，平定大洋。
            </p>
          </div>
        </router-link>

        <router-link to="/singularity-logs/4" class="row-card">
          <div class="card-header">
            <div class="meta-left">LEYSHIFT COORD: 51°30'N 0°07'W // 灵子转移成功</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>CORRECTED // 已修正</span></div>
          </div>
          <h2 class="card-main-title">死界魔雾都市 · 伦敦</h2>
          <div class="card-date">时空节点：A.D. 1888 // 人理定基值：A-</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">特异点编号</div>
              <div class="param-value highlight">Fourth Singularity</div>
            </div>
            <div class="param-group">
              <div class="param-label">主要威胁级别</div>
              <div class="param-value">Rank A-</div>
            </div>
            <div class="param-group">
              <div class="param-label">核心异变源</div>
              <div class="param-value highlight" style="color: #5796D0;">魔雾计划 (巴贝奇/P夫人/祖尔)</div>
            </div>
            <div class="param-group">
              <div class="param-label">圣杯持有者</div>
              <div class="param-value">特斯拉 (作为魔雾核心被唤醒)</div>
            </div>
            <div class="param-group">
              <div class="param-label">关键协力英灵</div>
              <div class="param-value ellipsis">莫德雷德、莫里亚蒂(幕后) / 莫扎特</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">【特异点观测报告 / CHALDEA MISSION LOG】</div>
            <p class="summary-text">
              工业革命时期的雾都伦敦。全城被一种能够隔绝阳光、腐蚀生物肉体并阻断魔术观测的"死界魔雾"所笼罩，其真面目是巨大的人工魔术触媒。迦勒底在叛逆骑士莫德雷德的带领下深入地底蒸汽迷宫，逐个击破了幕后操纵的苏格兰场与魔术师。战役末尾，魔术王盖提亚（以所罗门外貌）首次在人前现身，展示了令人绝望的压倒性神击。此役惊险胜出，人理防线在迷雾中勉强维系。
            </p>
          </div>
        </router-link>

        <router-link to="/singularity-logs/5" class="row-card">
          <div class="card-header">
            <div class="meta-left">LEYSHIFT COORD: 38°53'N 77°01'W // 灵子转移成功</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>CORRECTED // 已修正</span></div>
          </div>
          <h2 class="card-main-title">北美神话大战 · 合众国合战</h2>
          <div class="card-date">时空节点：A.D. 1783 // 人理定基值：A+</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">特异点编号</div>
              <div class="param-value highlight">Fifth Singularity</div>
            </div>
            <div class="param-group">
              <div class="param-label">主要威胁级别</div>
              <div class="param-value">Rank A+</div>
            </div>
            <div class="param-group">
              <div class="param-label">核心异变源</div>
              <div class="param-value highlight" style="color: #5796D0;">凯尔特狂暴军团 (梅芙/库·丘林[Alter])</div>
            </div>
            <div class="param-group">
              <div class="param-label">圣杯持有者</div>
              <div class="param-value">狂王库 · 丘林</div>
            </div>
            <div class="param-group">
              <div class="param-label">关键协力英灵</div>
              <div class="param-value ellipsis">南丁格尔、罗摩、迦尔纳</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">【特异点观测报告 / CHALDEA MISSION LOG】</div>
            <p class="summary-text">
              独立战争时期的北美大陆。因女王梅芙向圣杯许愿，召唤出了毫无理性的凯尔特大军，由绝对杀戮机器"狂王库丘林"统领，从东海岸一路血洗美利坚。泛人类史本土英灵（发明家、总统等）组成了西部合众国军拼死抵抗。迦勒底携手誓死救人的狂修士南丁格尔，跨越万里山河。经历了解除罗摩诅咒、迦尔纳决战阿周那等宏大史诗，最终粉碎狂王，将北美历史交还给人类自己。
            </p>
          </div>
        </router-link>

        <router-link to="/singularity-logs/6" class="row-card">
          <div class="card-header">
            <div class="meta-left">LEYSHIFT COORD: 31°47'N 35°13'E // 灵子转移成功</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>CORRECTED // 已修正</span></div>
          </div>
          <h2 class="card-main-title">神圣圆桌领域 · 卡美洛</h2>
          <div class="card-date">时空节点：A.D. 1273 // 人理定基值：EX</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">特异点编号</div>
              <div class="param-value highlight">Sixth Singularity</div>
            </div>
            <div class="param-group">
              <div class="param-label">主要威胁级别</div>
              <div class="param-value">Rank EX</div>
            </div>
            <div class="param-group">
              <div class="param-label">核心异变源</div>
              <div class="param-value highlight" style="color: #5796D0;">圣裁狮子王 (圆桌骑士团)</div>
            </div>
            <div class="param-group">
              <div class="param-label">圣杯持有者</div>
              <div class="param-value">狮子王 (手持圣枪伦戈米尼亚德)</div>
            </div>
            <div class="param-group">
              <div class="param-label">关键协力英灵</div>
              <div class="param-value ellipsis">贝德维尔、奥兹曼迪亚斯、山之翁</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">【特异点观测报告 / CHALDEA MISSION LOG】</div>
            <p class="summary-text">
              遭遇性质发生根本异变的极端特异点。原本应是十字军东征的沙场，却因手持圣枪、化身为神灵的"狮子王"降临而沦为焦土。她召唤出圆桌骑士团，建立纯白之城卡美洛，无情地屠杀难民，企图将人类的灵魂做成标本封存在圣枪中以躲避人理烧却。迦勒底联合山之翁、埃及法老王以及断臂的独臂骑士贝德维尔组成联军。在星之圣剑的真正因缘解开后，贝德维尔还剑于王，圣枪的制裁终于平息。
            </p>
          </div>
        </router-link>

        <router-link to="/singularity-logs/7" class="row-card">
          <div class="card-header">
            <div class="meta-left">LEYSHIFT COORD: 32°32'N 44°25'E // 灵子转移成功</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>CORRECTED // 已修正</span></div>
          </div>
          <h2 class="card-main-title">绝对魔兽战线 · 巴比伦尼亚</h2>
          <div class="card-date">时空节点：B.C. 2655 // 人理定基值：EX</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">特异点编号</div>
              <div class="param-value highlight">Seventh Singularity</div>
            </div>
            <div class="param-group">
              <div class="param-label">主要威胁级别</div>
              <div class="param-value">Rank EX</div>
            </div>
            <div class="param-group">
              <div class="param-label">核心异变源</div>
              <div class="param-value highlight" style="color: #5796D0;">三女神同盟 / 创世母神 (提亚玛特)</div>
            </div>
            <div class="param-group">
              <div class="param-label">圣杯持有者</div>
              <div class="param-value">金固 (伪恩奇都) // 盖提亚投入</div>
            </div>
            <div class="param-group">
              <div class="param-label">关键协力英灵</div>
              <div class="param-value ellipsis">吉尔伽美什[Caster]、梅林、艾蕾</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">【特异点观测报告 / CHALDEA MISSION LOG】</div>
            <p class="summary-text">
              人类最古老的苏美尔神代。魔术王盖提亚投下圣杯，试图从文明的源头彻底抹杀人类史。贤王吉尔伽美什放弃了不老药，率领乌鲁克全城军民修筑了"绝对魔兽战线"，苦撑数月。随着三女神同盟被迦勒底逐个击破，真正代表星之毁灭的原始母神"提亚玛特"黑泥化觉醒，拉赫穆大军淹没大地。最终，冥界之中，冠位暗杀者"山之翁"劈出赋予死亡概念的一击，吉尔伽美什以英雄王姿态全力解放王律键，神代自此彻底终结，通往终局特异点之门正式开启。
            </p>
          </div>
        </router-link>
      </main>
    </div>
  </div>
</template>

<script setup>
import NavBar from '../components/NavBar.vue'
</script>

<style scoped>
/* --- 核心变量 --- */
.page {
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

.container {
  max-width: 1100px;
  margin: 0 auto;
  padding: 5rem 1.5rem 3rem;
  position: relative;
  z-index: 10;
}

/* Top navigation bar with back link */
.top-nav-bar {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 8px;
  padding-bottom: 8px;
  border-bottom: 1px solid rgba(255,255,255,0.05);
}
.back-link {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 11px;
  color: rgba(87,150,208,0.6);
  text-decoration: none;
  letter-spacing: 1px;
  transition: color 0.2s;
}
.back-link:hover { color: #5796D0; }
.top-nav-title {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 9px;
  color: rgba(255,255,255,0.2);
  letter-spacing: 2px;
}

/* --- 顶部控制台大标题 --- */
.header {
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  padding-bottom: 1.5rem;
  margin-bottom: 2.5rem;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.header-title p {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.75rem;
  letter-spacing: 0.3em;
  color: #5796D0;
  text-transform: uppercase;
  margin-bottom: 0.5rem;
}

.header-title h1 {
  font-size: 1.8rem;
  font-weight: 200;
  letter-spacing: 0.05em;
  margin: 0;
}

.header-status {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.75rem;
  color: rgba(255,255,255,0.4);
  text-align: right;
  line-height: 1.6;
}

.header-status span {
  color: #4cd964;
  animation: pulse 2s infinite;
}

/* --- 垂直列表布局 --- */
.report-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* --- 宽卡片主体 --- */
.row-card {
  display: block;
  text-decoration: none;
  color: inherit;
  background: rgba(8, 20, 48, 0.55);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 12px;
  padding: 1.5rem;
  position: relative;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.4s cubic-bezier(0.16, 1, 0.3, 1),
    border-color 0.4s ease,
    box-shadow 0.4s ease;
}

.row-card:hover {
  transform: translateY(-2px);
  border-color: rgba(87, 150, 208, 0.35);
  box-shadow: 0 15px 40px rgba(87, 150, 208, 0.03);
}

/* Apple 悬停流光线 */
.row-card::after {
  content: "";
  position: absolute;
  inset: 0;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.02), transparent);
  transform: translateX(-100%);
  z-index: 2;
}

.row-card:hover::after {
  animation: scan 1.5s cubic-bezier(0.4, 0, 0.2, 1) infinite;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 1rem;
}

.meta-left {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.7rem;
  color: #5796D0;
  letter-spacing: 0.1em;
}

.badge {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.65rem;
  padding: 2px 8px;
  border-radius: 4px;
  display: flex;
  align-items: center;
  gap: 4px;
  border: 1px solid rgba(76, 217, 100, 0.2);
  background: rgba(76, 217, 100, 0.04);
  color: #4cd964;
}

.badge-dot {
  width: 4px;
  height: 4px;
  background-color: currentColor;
  border-radius: 50%;
}

.card-main-title {
  font-size: 1.35rem;
  font-weight: 400;
  letter-spacing: 0.02em;
  margin-bottom: 0.25rem;
  transition: color 0.3s ease;
}

.row-card:hover .card-main-title {
  color: #5796D0;
}

.card-date {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.75rem;
  color: rgba(255,255,255,0.4);
  margin-bottom: 1.25rem;
}

/* 五列参数面板 */
.parameter-panel {
  display: grid;
  grid-template-columns: 1fr 1.2fr 1.5fr 1.2fr 2fr;
  gap: 1.25rem;
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid rgba(255, 255, 255, 0.03);
  border-radius: 8px;
  padding: 0.875rem 1.25rem;
  margin-bottom: 1.25rem;
}

.param-group {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}

.param-label {
  font-size: 0.6rem;
  color: rgba(255,255,255,0.4);
  text-transform: uppercase;
  margin-bottom: 0.25rem;
  letter-spacing: 0.05em;
}

.param-value {
  font-size: 0.8rem;
  color: rgba(255,255,255,0.55);
}

.param-value.highlight {
  color: rgb(255, 255, 255);
  font-weight: 500;
}

.ellipsis {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: block;
}

.summary-section {
  border-top: 1px dashed rgba(255, 255, 255, 0.06);
  padding-top: 1rem;
}

.summary-title {
  font-size: 0.7rem;
  color: rgba(255,255,255,0.4);
  margin-bottom: 0.5rem;
  font-weight: 500;
  letter-spacing: 0.05em;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}

.summary-text {
  font-size: 0.85rem;
  color: rgba(255,255,255,0.5);
  line-height: 1.6;
  text-align: justify;
}

@keyframes scan {
  0%   { transform: translateX(-100%); }
  100% { transform: translateX(100%); }
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50%      { opacity: 0.3; }
}

@media (max-width: 850px) {
  .parameter-panel {
    grid-template-columns: repeat(2, 1fr);
  }

  .header {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }

  .header-status {
    text-align: left;
  }
}
</style>
