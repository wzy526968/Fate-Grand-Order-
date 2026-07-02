<template>
  <div class="resource-gauge">
    <div class="gauge-header">
      <span class="gauge-icon">{{ icon }}</span>
      <span class="gauge-name">{{ name }}</span>
      <span class="gauge-value">{{ formattedQuantity }}</span>
    </div>
    <div class="gauge-track">
      <div class="gauge-fill" :style="{ width: fillPercent + '%', background: fillColor }"></div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  name: { type: String, required: true },
  type: { type: String, required: true },
  quantity: { type: Number, required: true },
  icon: { type: String, default: '•' },
})

const MAX_VISIBLE = 999999

const fillPercent = computed(() => {
  return Math.min((props.quantity / MAX_VISIBLE) * 100, 100)
})

const fillColor = computed(() => {
  const t = props.type
  if (t === 'SAINT_QUARTZ') return '#5796D0'
  if (t === 'MANA_PRISM') return '#4cd964'
  if (t === 'QP') return '#ffcc00'
  if (t === 'RARE_PRISM') return '#ff9500'
  return '#5796D0'
})

const formattedQuantity = computed(() => {
  const n = props.quantity
  if (n >= 10000000) return (n / 10000000).toFixed(1) + 'kw'
  if (n >= 10000) return (n / 10000).toFixed(1) + 'w'
  return n.toLocaleString()
})
</script>

<style scoped>
.resource-gauge {
  padding: 8px 0;
}
.gauge-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 4px;
}
.gauge-icon {
  font-size: 16px;
  width: 24px;
  text-align: center;
}
.gauge-name {
  font-size: 11px;
  color: rgba(255,255,255,0.7);
  flex: 1;
  font-family: 'JetBrains Mono', monospace;
  letter-spacing: 0.5px;
}
.gauge-value {
  font-size: 11px;
  color: rgba(255,255,255,0.8);
  font-family: 'JetBrains Mono', monospace;
}
.gauge-track {
  height: 6px;
  background: rgba(255,255,255,0.06);
  border: 1px solid rgba(255,255,255,0.08);
  overflow: hidden;
}
.gauge-fill {
  height: 100%;
  transition: width 0.5s ease;
  box-shadow: 0 0 6px currentColor;
}
</style>
