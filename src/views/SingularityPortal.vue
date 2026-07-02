<template>
  <div class="chaldea-container">
    <!-- CRT scanline overlay -->
    <div class="scanline-overlay"></div>
    <NavBar theme="dark" />

    <header class="global-header">
      <div class="brand">
        <p>Chaldea Security Organization</p>
        <h1>人理保障核心数据控制台</h1>
      </div>
      <div class="system-time">
        ACCESS TERMINAL: TRISMEGISTUS II<br>
        SECURE LINK COMPLETED
      </div>
    </header>

    <div class="portal-wrapper">
      <div class="center-divider"></div>

      <router-link to="/singularities" class="sector part-one">
        <div class="scan-glow"></div>
        <div class="sector-tag">PART I // OBSERVATION</div>
        <h2 class="sector-title">冠位指定 · 特异点修正档案</h2>
        <p class="sector-desc">
          观测并记录人类史自 A.D. 1431 至 B.C. 2655 期间发生的七大时空扭曲节点。包含灵子转移作战复盘、魔神柱遭遇数据及人理定基值修复日志。
        </p>
        <div class="sector-footer">
          <span>ENTER SYSTEM</span>
          <div class="arrow-line"></div>
        </div>
      </router-link>

      <router-link to="/lostbelts" class="sector part-two">
        <div class="scan-glow"></div>
        <div class="sector-tag">PART II // Cosmos in the Lostbelt</div>
        <h2 class="sector-title">宇宙清空 · 异闻事象剪定全志</h2>
        <p class="sector-desc">
          记录在地球白纸化灾厄发生后，依托虚数潜航艇（Shadow Border）突入并切除的七大空想树异闻国度。包含隐匿者档案及异闻之王战略复盘。
        </p>
        <div class="sector-footer">
          <span>ENTER SYSTEM</span>
          <div class="arrow-line"></div>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import NavBar from '../components/NavBar.vue'
</script>

<style scoped>
/* --- 全局容器 --- */
.chaldea-container {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  padding-top: 52px;
  background: rgb(8, 24, 56);
  background-image:
    repeating-linear-gradient(
      0deg,
      transparent,
      transparent 2px,
      rgba(255,255,255,0.015) 2px,
      rgba(255,255,255,0.015) 4px
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
  color: rgb(255, 255, 255);
  -webkit-font-smoothing: antialiased;
}

/* --- CRT Scanline Overlay --- */
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
  animation: flicker 0.1s infinite;
  opacity: 0.5;
}

@keyframes flicker {
  0%   { opacity: 0.3; }
  50%  { opacity: 0.5; }
  100% { opacity: 0.3; }
}

/* --- 顶部全局状态栏 --- */
.global-header {
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem 3rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.03);
  position: relative;
  z-index: 10;
}

.brand p {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.7rem;
  letter-spacing: 0.4em;
  color: rgba(255,255,255,0.5);
  text-transform: uppercase;
  margin-bottom: 0.25rem;
}

.brand h1 {
  font-size: 1.25rem;
  font-weight: 200;
  letter-spacing: 0.1em;
  color: rgb(255, 255, 255);
}

.system-time {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.75rem;
  color: rgba(255,255,255,0.4);
  text-align: right;
  line-height: 1.5;
}

/* --- 双分切入面板核心容器 --- */
.portal-wrapper {
  flex: 1;
  display: flex;
  position: relative;
  z-index: 10;
  height: calc(100vh - 80px);
}

/* 分割线：中轴线 */
.center-divider {
  position: absolute;
  left: 50%;
  top: 10%;
  bottom: 10%;
  width: 1px;
  background: linear-gradient(to bottom, transparent, rgba(255,255,255,0.05), transparent);
  z-index: 5;
}

/* 单个大选择扇区 */
.sector {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 0 10%;
  text-decoration: none;
  color: inherit;
  position: relative;
  transition: background-color 0.6s cubic-bezier(0.16, 1, 0.3, 1);
  overflow: hidden;
}

.sector.part-one {
  border-right: 1px solid rgba(255, 255, 255, 0.02);
}

.sector.part-one:hover {
  background-color: rgba(87, 150, 208, 0.025);
}

.sector.part-two:hover {
  background-color: rgba(245, 158, 11, 0.025);
}

/* 扇区内部文本排版 */
.sector-tag {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.75rem;
  letter-spacing: 0.3em;
  margin-bottom: 1rem;
  transition: color 0.4s ease;
}

.part-one .sector-tag {
  color: rgba(87, 150, 208, 0.5);
}

.part-two .sector-tag {
  color: rgba(245, 158, 11, 0.5);
}

.sector:hover .sector-tag {
  color: rgb(255, 255, 255);
}

.sector-title {
  font-size: 2.2rem;
  font-weight: 200;
  letter-spacing: 0.05em;
  margin-bottom: 0.5rem;
  transition: transform 0.4s cubic-bezier(0.16, 1, 0.3, 1), color 0.4s ease;
}

.sector:hover .sector-title {
  transform: translateX(10px);
}

.part-one:hover .sector-title {
  color: #5796D0;
}

.part-two:hover .sector-title {
  color: #f59e0b;
}

.sector-desc {
  font-size: 0.9rem;
  color: rgba(255,255,255,0.5);
  max-width: 400px;
  line-height: 1.6;
  font-weight: 300;
}

/* 卡片底部的修饰线条与动态箭头 */
.sector-footer {
  margin-top: 3rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.75rem;
  color: rgba(255,255,255,0.3);
  transition: color 0.4s ease;
}

.sector:hover .sector-footer {
  color: rgb(255, 255, 255);
}

.arrow-line {
  height: 1px;
  width: 40px;
  background: rgba(255,255,255,0.3);
  position: relative;
  transition: width 0.4s ease, background-color 0.4s ease;
}

.sector:hover .arrow-line {
  width: 70px;
}

.part-one:hover .arrow-line {
  background-color: #5796D0;
}

.part-two:hover .arrow-line {
  background-color: #f59e0b;
}

/* --- 边缘渐变遮罩 --- */
.scan-glow {
  position: absolute;
  width: 200px;
  height: 100%;
  top: 0;
  pointer-events: none;
  opacity: 0;
  transition: opacity 0.6s ease;
}

.part-one .scan-glow {
  right: 0;
  background: linear-gradient(90deg, transparent, rgba(87,150,208,0.03));
}

.part-two .scan-glow {
  left: 0;
  background: linear-gradient(-90deg, transparent, rgba(245,158,11,0.03));
}

.sector:hover .scan-glow {
  opacity: 1;
}

/* 响应式 */
@media (max-width: 768px) {
  .portal-wrapper {
    flex-direction: column;
    height: auto;
  }

  .center-divider {
    display: none;
  }

  .sector {
    padding: 4rem 2rem;
    border-right: none !important;
    border-bottom: 1px solid rgba(255, 255, 255, 0.02);
  }

  .global-header {
    padding: 1rem 1.5rem;
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }

  .system-time {
    text-align: left;
  }
}
</style>
