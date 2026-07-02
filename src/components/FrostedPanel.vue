<template>
  <div
    :class="['frosted-panel', variant]"
    :style="{ width: width, height: height }"
  >
    <div class="panel-header">
      <span class="panel-dots">
        <span class="dot dot-red"></span>
        <span class="dot dot-yellow"></span>
        <span class="dot dot-green"></span>
      </span>
      <span class="panel-title">{{ title }}</span>
    </div>
    <div class="panel-body">
      <slot />
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: { type: String, default: 'Panel' },
  width: { type: String, default: '100%' },
  height: { type: String, default: 'auto' },
  variant: { type: String, default: 'light' },
})
</script>

<style scoped>
/* Light variant — Apple industrial design */
.frosted-panel.light {
  background: rgba(255, 255, 255, 0.78);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 0.5px solid rgba(100, 180, 255, 0.3);
  border-radius: 14px;
  box-shadow: 0 4px 24px rgba(100, 180, 255, 0.08);
  overflow: hidden;
  display: flex;
  flex-direction: column;
  transition: box-shadow 0.3s ease;
}
.frosted-panel.light:hover {
  box-shadow: 0 6px 32px rgba(100, 180, 255, 0.14);
}
.frosted-panel.light .panel-header {
  border-bottom: 0.5px solid rgba(100, 180, 255, 0.15);
}
.frosted-panel.light .panel-title {
  color: #1d1d1f;
}

/* Terminal variant — matching Chaldea CRT aesthetic */
.frosted-panel.terminal {
  background: rgba(0, 0, 0, 0.35);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 0;
  box-shadow: 0 0 16px rgba(255,255,255,0.05);
  overflow: hidden;
  display: flex;
  flex-direction: column;
  transition: border-color 0.25s ease, box-shadow 0.25s ease;
}
.frosted-panel.terminal:hover {
  border-color: rgba(255, 255, 255, 0.5);
  box-shadow: 0 0 24px rgba(255,255,255,0.08);
}
.frosted-panel.terminal .panel-header {
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}
.frosted-panel.terminal .panel-title {
  color: rgb(255, 255, 255);
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 10px;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  font-weight: 400;
}

/* Shared */
.frosted-panel {
  display: flex;
  flex-direction: column;
}
.panel-header {
  display: flex;
  align-items: center;
  padding: 10px 16px;
  gap: 10px;
}
.panel-dots {
  display: flex;
  gap: 6px;
}
.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  flex-shrink: 0;
}
.dot-red { background: #ff5f57; }
.dot-yellow { background: #ffbd2e; }
.dot-green { background: #28c840; }
.panel-title {
  font-size: 12px;
  font-weight: 500;
  letter-spacing: 0.5px;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
}
.panel-body {
  padding: 14px 16px;
  flex: 1;
  overflow-y: auto;
}
</style>
