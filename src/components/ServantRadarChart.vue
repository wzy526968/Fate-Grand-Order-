<template>
  <div class="radar-wrapper" :style="{ width: width, height: height }">
    <div ref="chartRef" class="radar-canvas"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch, nextTick } from 'vue'
import * as echarts from 'echarts'

const props = defineProps({
  servant: { type: Object, default: null },
  width: { type: String, default: '100%' },
  height: { type: String, default: '380px' },
})

const chartRef = ref(null)
let chart = null

function initChart() {
  if (!chartRef.value) return

  if (chart) chart.dispose()
  chart = echarts.init(chartRef.value)

  window.addEventListener('resize', handleResize)
}

function handleResize() {
  chart?.resize()
}

function getOption(s) {
  if (!s) return null

  return {
    radar: {
      indicator: [
        { name: '筋力', max: 10 },
        { name: '耐久', max: 10 },
        { name: '敏捷', max: 10 },
        { name: '魔力', max: 10 },
        { name: '幸运', max: 10 },
        { name: '宝具', max: 10 },
      ],
      radius: '68%',
      shape: 'circle',
      splitNumber: 5,
      axisName: {
        color: 'rgba(255,255,255,0.6)',
        fontSize: 12,
        fontFamily: 'JetBrains Mono, monospace',
      },
      splitArea: {
        areaStyle: {
          color: ['rgba(87, 150, 208, 0.04)', 'rgba(87, 150, 208, 0.08)'],
        },
      },
      axisLine: {
        lineStyle: { color: 'rgba(255,255,255,0.12)' },
      },
      splitLine: {
        lineStyle: { color: 'rgba(255,255,255,0.08)' },
      },
    },
    series: [
      {
        type: 'radar',
        data: [
          {
            value: [s.strength, s.endurance, s.agility, s.magic, s.luck, s.np],
            name: s.name,
            areaStyle: {
              color: 'rgba(87, 150, 208, 0.18)',
            },
            lineStyle: {
              color: '#5796D0',
              width: 2,
            },
            itemStyle: {
              color: '#7bb3e0',
            },
          },
        ],
        animation: true,
      },
    ],
  }
}

function renderChart() {
  if (!chart) initChart()
  if (!chart) return

  const option = getOption(props.servant)
  if (option) {
    chart.setOption(option, true)
  } else {
    chart.clear()
  }
}

watch(() => props.servant, () => {
  nextTick(() => renderChart())
}, { deep: true })

onMounted(() => {
  nextTick(() => renderChart())
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  chart?.dispose()
})
</script>

<style scoped>
.radar-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
}
.radar-canvas {
  width: 100%;
  height: 100%;
  min-height: 300px;
}
</style>
