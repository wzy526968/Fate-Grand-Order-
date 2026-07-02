<template>
  <div :class="['summoning-circle', { active: isActive }]">
    <!-- Ring 1 — outermost, slow clockwise -->
    <div class="ring ring-1"></div>
    <!-- Ring 2 — counter-clockwise -->
    <div class="ring ring-2"></div>
    <!-- Ring 3 — inner, fast clockwise -->
    <div class="ring ring-3"></div>
    <!-- Center glow -->
    <div class="center-glow"></div>
    <!-- Rune symbols positioned around the outer ring -->
    <div class="runes" v-for="i in 12" :key="i"
      :style="{ transform: `rotate(${i * 30}deg) translateY(-130px)` }">
      <span class="rune" :style="{ transform: `rotate(${-i * 30}deg)` }">{{ runes[i - 1] }}</span>
    </div>
  </div>
</template>

<script setup>
defineProps({
  isActive: { type: Boolean, default: false },
})

const runes = ['ᚠ', 'ᚢ', 'ᚦ', 'ᚨ', 'ᚱ', 'ᚲ', 'ᚷ', 'ᚹ', 'ᚺ', 'ᚾ', 'ᛁ', 'ᛃ']
</script>

<style scoped>
.summoning-circle {
  position: relative;
  width: 280px;
  height: 280px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: center;
}

.ring {
  position: absolute;
  border-radius: 50%;
  border-style: dashed;
  border-color: #5796D0;
  animation: summon-spin 8s linear infinite;
  box-shadow: 0 0 20px rgba(87, 150, 208, 0.15);
}

.ring-1 {
  width: 280px;
  height: 280px;
  border-width: 2px;
  opacity: 0.6;
}
.ring-2 {
  width: 200px;
  height: 200px;
  border-width: 1.5px;
  opacity: 0.5;
  animation-direction: reverse;
  animation-duration: 6s;
}
.ring-3 {
  width: 120px;
  height: 120px;
  border-width: 1px;
  opacity: 0.7;
  animation-duration: 4s;
}

/* Active (summoning) state */
.active .ring-1 {
  animation-duration: 2s;
  border-color: #7bb8ff;
  box-shadow: 0 0 40px rgba(87, 150, 208, 0.4);
}
.active .ring-2 {
  animation-duration: 1.5s;
  border-color: #7bb8ff;
  box-shadow: 0 0 30px rgba(87, 150, 208, 0.3);
}
.active .ring-3 {
  animation-duration: 1s;
  border-color: #a8d4ff;
  box-shadow: 0 0 50px rgba(87, 150, 208, 0.5);
}

.center-glow {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(87,150,208,0.4) 0%, transparent 70%);
  animation: pulse 3s ease-in-out infinite;
}
.active .center-glow {
  background: radial-gradient(circle, rgba(135,200,255,0.7) 0%, rgba(87,150,208,0.3) 50%, transparent 70%);
  animation: pulse 0.8s ease-in-out infinite;
  width: 60px;
  height: 60px;
}

.runes {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
}
.rune {
  position: absolute;
  font-size: 14px;
  color: rgba(87, 150, 208, 0.5);
  font-family: 'Segoe UI Symbol', monospace;
  transition: all 0.3s;
}
.active .rune {
  color: rgba(135, 200, 255, 0.9);
  text-shadow: 0 0 8px rgba(87, 150, 208, 0.8);
}

@keyframes summon-spin {
  0%   { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@keyframes pulse {
  0%, 100% { opacity: 0.5; transform: scale(1); }
  50%      { opacity: 1;   transform: scale(1.15); }
}
</style>
