<template>
  <div class="page">
    <div class="scanline-overlay"></div>
    <NavBar theme="dark" />

    <div class="app-layout">
      <!-- 顶部导航 -->
      <div class="top-bar">
        <router-link :to="backPath" class="back-link">&larr; {{ backText }}</router-link>
        <span class="top-title">{{ pageTitle }}</span>
      </div>

      <div class="main-layout">
        <!-- 内容区域 -->
        <main class="content-area">
          <!-- 列表视图：当没有选中文章时显示 -->
          <div v-if="!activeId" class="entry-list">
            <div class="entry-intro">
              <h2>{{ pageTitle }}</h2>
              <p>请选择一篇报告进行查阅</p>
            </div>
            <div class="entry-cards">
              <router-link
                v-for="a in articles"
                :key="a.id"
                :to="`${routePrefix}/${a.id}`"
                class="entry-card"
              >
                <span class="entry-num">No.{{ String(a.id).padStart(2, '0') }}</span>
                <span class="entry-name">{{ a.title }}</span>
                <span class="entry-arrow">&rarr;</span>
              </router-link>
            </div>
          </div>

          <!-- 文章视图 -->
          <div v-else-if="content" class="log-article-wrapper" v-html="content"></div>

          <div v-else class="not-found">
            <p>未找到该日志</p>
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import NavBar from '../components/NavBar.vue'

const route = useRoute()

const activeId = computed(() => route.params.id ? Number(route.params.id) : null)

const isSingularity = computed(() =>
  route.name === 'SingularityLogArticle' || route.name === 'SingularityLogIndex'
)

const routePrefix = computed(() => isSingularity.value ? '/singularity-logs' : '/logs')
const backPath = computed(() => isSingularity.value ? '/singularities' : '/lostbelts')
const backText = computed(() => isSingularity.value ? '返回特异点列表' : '返回异闻列表')
// const pageTitle = computed(() => isSingularity.value
//   ? '特异点修复日志 · 全七章'
//   : '异闻带剪定日志 · 全七章'
// )

const articles = computed(() => {
  if (isSingularity.value) {
    return [
      { id: 1, title: '邪龙百年战争 · 奥尔良', date: 'A.D. 1431' },
      { id: 2, title: '永续疯狂帝国 · 七丘之城', date: 'A.D. 0060' },
      { id: 3, title: '封锁终局四海 · 俄刻阿诺斯', date: 'A.D. 1573' },
      { id: 4, title: '死界魔雾都市 · 伦敦', date: 'A.D. 1888' },
      { id: 5, title: '北美神话大战 · 合众国合战', date: 'A.D. 1783' },
      { id: 6, title: '神圣圆桌领域 · 卡美洛', date: 'A.D. 1273' },
      { id: 7, title: '绝对魔兽战线 · 巴比伦尼亚', date: 'B.C. 2655' },
    ]
  }
  return [
    { id: 1, title: '永久冻土帝国 · 阿纳斯塔西娅', date: '2067-02-16' },
    { id: 2, title: '无间冰焰世纪 · 诸神黄昏', date: '2067-04-12' },
    { id: 3, title: '人智统合真国 · SIN', date: '2067-07-08' },
    { id: 4, title: '创世灭亡轮回 · 由伽·刹多', date: '2067-10-17' },
    { id: 5, title: '神代巨神海洋 · 亚特兰蒂斯', date: '2068-01-30' },
    { id: 6, title: '妖精圆桌领域 · 阿瓦隆·勒·菲', date: '2068-04-15' },
    { id: 7, title: '黄金树海纪行 · 米克特兰', date: '2068-07-23' },
  ]
})

const modules = import.meta.glob('./log/log[a-z][0-9].html', {
  query: '?raw',
  import: 'default',
  eager: true,
})

function extractBody(html) {
  if (!html) return ''
  const match = html.match(/<body[^>]*>([\s\S]*?)<\/body>/i)
  return match ? match[1].trim() : html
}

const content = computed(() => {
  if (!activeId.value) return null
  const prefix = isSingularity.value ? 'logb' : 'loga'
  const keys = Object.keys(modules)
  const key = keys.find(k => k.includes(`/${prefix}${activeId.value}.`))
  if (!key) return ''
  return extractBody(modules[key])
})
</script>

<style>
.log-article-wrapper .report-container {
  max-width: 100%;
  background: linear-gradient(160deg, #111a2e 0%, #0a1120 100%);
  border: 1px solid #2a3a6a;
  border-radius: 14px;
  padding: 22px 28px 22px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.9), 0 0 0 1px rgba(60, 120, 255, 0.1) inset;
  position: relative;
}
.log-article-wrapper .report-container::before {
  content: '';
  position: absolute;
  top: 0; left: 0; right: 0;
  height: 3px;
  background: linear-gradient(90deg, #1a5cff, #8ab7ff, #1a5cff);
  border-radius: 20px 20px 0 0;
}
.log-article-wrapper .report-header {
  border-bottom: 1px solid #1a2a5a;
  padding-bottom: 12px;
  margin-bottom: 18px;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  flex-wrap: wrap;
}
.log-article-wrapper .report-header .title-group h1 {
  font-size: 18px;
  font-weight: 400;
  letter-spacing: 2px;
  color: #e0ecff;
  text-shadow: 0 0 20px rgba(26, 92, 255, 0.2);
  margin: 0;
}
.log-article-wrapper .report-header .title-group .sub {
  font-size: 11px;
  color: #6a8aaa;
  letter-spacing: 1px;
  margin-top: 4px;
}
.log-article-wrapper .report-meta {
  font-size: 11px;
  color: #6a8aaa;
  text-align: right;
  line-height: 1.8;
}
.log-article-wrapper .report-meta .label { color: #5a7a9a; }
.log-article-wrapper .report-meta .value { color: #aac8ff; }
.log-article-wrapper .report-body {
  font-size: 13px;
  line-height: 1.8;
  color: #d0dcec;
}
.log-article-wrapper .report-body h2 {
  font-size: 14px;
  font-weight: 500;
  color: #8ab7ff;
  letter-spacing: 1px;
  margin: 18px 0 8px 0;
  border-bottom: 1px solid #1a2a4a;
  padding-bottom: 4px;
}
.log-article-wrapper .report-body p {
  margin-bottom: 1em;
  text-indent: 2em;
}
.log-article-wrapper .report-body .highlight { color: #7ab7ff; }
.log-article-wrapper .report-body .highlight-warn { color: #ffb347; }
.log-article-wrapper .report-body .highlight-danger { color: #ff6b6b; }
.log-article-wrapper .data-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 12px;
  margin: 8px 0 14px;
  background: rgba(10, 18, 36, 0.5);
  border-radius: 8px;
  overflow: hidden;
}
.log-article-wrapper .data-table th {
  background: #1a2a4a;
  color: #8ab7ff;
  font-weight: 400;
  text-align: left;
  padding: 6px 12px;
  letter-spacing: 0.5px;
}
.log-article-wrapper .data-table td {
  padding: 6px 12px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.04);
  color: #c8d4e8;
}
.log-article-wrapper .data-table tr:last-child td { border-bottom: none; }
.log-article-wrapper .data-table .warn { color: #ffb347; }
.log-article-wrapper .data-table .danger { color: #ff6b6b; }
.log-article-wrapper .data-table .good { color: #5ddf8a; }
.log-article-wrapper .signature-block {
  margin-top: 24px;
  padding-top: 14px;
  border-top: 1px solid #1a2a4a;
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #7a8aaa;
  flex-wrap: wrap;
}
.log-article-wrapper .signature-block .stamp { color: #1a5cff; letter-spacing: 2px; }
.log-article-wrapper .signature-block .right { text-align: right; line-height: 1.7; }
.log-article-wrapper .blink { animation: blink 1.2s step-start infinite; }
@keyframes blink { 50% { opacity: 0.2; } }
.log-article-wrapper .tag {
  display: inline-block;
  font-size: 11px;
  padding: 2px 14px;
  border-radius: 20px;
  background: rgba(26, 92, 255, 0.1);
  color: #8ab7ff;
  border: 1px solid rgba(26, 92, 255, 0.15);
  margin-right: 6px;
}
.log-article-wrapper .tag.danger {
  background: rgba(255, 60, 60, 0.1);
  color: #ff7a7a;
  border-color: rgba(255, 60, 60, 0.2);
}
.log-article-wrapper .tag.good {
  background: rgba(60, 220, 120, 0.1);
  color: #6ae09a;
  border-color: rgba(60, 220, 120, 0.15);
}
.log-article-wrapper .attachment-list {
  list-style: none;
  padding-left: 0;
  margin: 6px 0 12px;
}
.log-article-wrapper .attachment-list li {
  padding: 3px 0 3px 24px;
  background: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="%236a8aaa" stroke-width="2"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"/><polyline points="13 2 13 9 20 9"/></svg>') left center no-repeat;
  background-size: 14px;
  color: #8a9ab0;
  font-size: 13px;
}
.log-article-wrapper .report-body ul {
  list-style: none;
  padding-left: 0;
  margin: 8px 0 16px;
}
.log-article-wrapper .report-body ul li {
  padding: 4px 0 4px 24px;
  background: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="%236a8aaa" stroke-width="2"><polyline points="20 6 9 17 4 12"/></svg>') left center no-repeat;
  background-size: 14px;
}
</style>

<style scoped>
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

.app-layout {
  max-width: 1300px;
  margin: 0 auto;
  padding: 4rem 1.5rem 3rem;
  position: relative;
  z-index: 10;
}

.top-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.back-link {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.8rem;
  color: rgba(87, 150, 208, 0.7);
  text-decoration: none;
  transition: color 0.3s ease;
}
.back-link:hover { color: #5796D0; }

.top-title {
  height: 40px;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 0.75rem;
  color: rgba(255, 255, 255, 0.3);
  letter-spacing: 0.15em;
}

.main-layout {
  display: block;
}
.content-area {
  flex: 1;
  min-width: 0;
}

.entry-list {
  background: rgba(8, 20, 48, 0.55);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.06);
  border-radius: 16px;
  padding: 2.5rem;
}

.entry-intro h2 {
  font-size: 1.3rem;
  font-weight: 200;
  letter-spacing: 0.05em;
  margin: 0 0 0.5rem 0;
}

.entry-intro p {
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.35);
  margin-bottom: 1.5rem;
}

.entry-cards {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.entry-card {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem 1.25rem;
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid rgba(255, 255, 255, 0.04);
  text-decoration: none;
  color: inherit;
  transition: all 0.3s cubic-bezier(0.16, 1, 0.3, 1);
}
.entry-card:hover {
  background: rgba(87, 150, 208, 0.06);
  border-color: rgba(87, 150, 208, 0.25);
  transform: translateX(4px);
}

.entry-num {
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  font-size: 1rem;
  font-weight: 600;
  color: rgba(87, 150, 208, 0.3);
  min-width: 54px;
}
.entry-card:hover .entry-num { color: #5796D0; }

.entry-name {
  flex: 1;
  font-size: 0.9rem;
  transition: color 0.3s ease;
}
.entry-card:hover .entry-name { color: #5796D0; }

.entry-arrow {
  font-size: 1.1rem;
  color: rgba(255, 255, 255, 0.15);
  transition: all 0.3s ease;
}
.entry-card:hover .entry-arrow {
  color: #5796D0;
  transform: translateX(4px);
}

.not-found {
  text-align: center;
  padding: 4rem 1.5rem;
  color: rgba(255, 255, 255, 0.4);
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}

@media (max-width: 860px) {
  .entry-list { padding: 1.5rem; }
}
</style>
