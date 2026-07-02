<template>
  <div class="page">
    <div class="scanline-overlay"></div>
    <NavBar theme="dark" />

    <div class="container">
      <div class="top-nav-bar">
        <router-link to="/portal" class="back-link">← OBSERVATION PORTAL</router-link>
        <span class="top-nav-title">LOSTBELT ARCHIVE</span>
      </div>
      <header class="header">
        <div class="header-title">
          <p>Chaldea Intelligence Terminal</p>
          <h1>人理保障档案 · 异闻带事象剪定全志</h1>
        </div>
        <div class="header-status">
          SYSTEM: TRISMEGISTUS II // SCANNING<br>
          人理白纸化状态: <span>100.00%</span>
        </div>
      </header>

      <main class="report-list">
        <!-- Lostbelt 1 -->
        <router-link to="/logs/1" class="row-card">
          <div class="card-header">
            <div class="meta-left">DETECTION COORD: 62°24'N 41°10'E // 虚数路径锁定</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>PRUNED // 已剪定</span></div>
          </div>
          <h2 class="card-main-title">永久冻土帝国 · 阿纳斯塔西娅</h2>
          <div class="card-date">人理异闻区划：俄罗斯异闻带 // 历史分歧节点：A.D. 1570</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">异闻编号</div>
              <div class="param-value highlight">Lostbelt No.1</div>
            </div>
            <div class="param-group">
              <div class="param-label">人类史崩溃深度</div>
              <div class="param-value">D</div>
            </div>
            <div class="param-group">
              <div class="param-label">空想树 (真名)</div>
              <div class="param-value highlight" style="color: #5796D0;">Mayall (麦亚尔)</div>
            </div>
            <div class="param-group">
              <div class="param-label">隐匿者 (Crypter)</div>
              <div class="param-value">卡多克 · 泽姆露普斯</div>
            </div>
            <div class="param-group">
              <div class="param-label">异闻带之王</div>
              <div class="param-value ellipsis">雷帝 · 伊凡</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">【战术切除战果报告 / MISSION LOG】</div>
            <p class="summary-text">
              遭遇因泛人类史极寒冻结而诞生走向分歧的"雅嘎"世界。由于极端低温，旧人类肉体被彻底颠覆改造成魔兽。战术核心在于解除对异闻带之王"雷帝"的绝对防御。在叛逆军同盟与萨列里、阿维斯布隆等英灵的协助下，成功唤醒并击破暴走状态的雷帝。最终，使用影子边境跨越冰原，全面切除空想树麦亚尔，俄罗斯异闻带事象归无。
            </p>
          </div>
        </router-link>

        <!-- Lostbelt 2 -->
        <router-link to="/logs/2" class="row-card">
          <div class="card-header">
            <div class="meta-left">DETECTION COORD: 64°50'N 18°20'W // 虚数路径锁定</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>PRUNED // 已剪定</span></div>
          </div>
          <h2 class="card-main-title">无间冰焰世纪 · 诸神黄昏</h2>
          <div class="card-date">人理异闻区划：北欧异闻带 // 历史分歧节点：B.C. 1000</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">异闻编号</div>
              <div class="param-value highlight">Lostbelt No.2</div>
            </div>
            <div class="param-group">
              <div class="param-label">人类史崩溃深度</div>
              <div class="param-value">B+</div>
            </div>
            <div class="param-group">
              <div class="param-label">空想树 (真名)</div>
              <div class="param-value highlight" style="color: #5796D0;">Sombrero (宽边帽)</div>
            </div>
            <div class="param-group">
              <div class="param-label">隐匿者 (Crypter)</div>
              <div class="param-value">奥菲莉娅 · 法姆索罗涅</div>
            </div>
            <div class="param-group">
              <div class="param-label">异闻带之王</div>
              <div class="param-value ellipsis">斯卡哈 · 斯卡蒂</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">【战术切除战果报告 / MISSION LOG】</div>
            <p class="summary-text">
              遭遇北欧诸神黄昏战役（Ragnarok）中因主神奥丁战败分歧而形成的停滞世界。全人类生命体被锁死在极小圈层内，在巨人族的威胁与北欧女王斯卡蒂慈悲且残酷的法则下苟延残喘。迦勒底在拿破仑、布伦希尔德等英灵的决死突袭下，击破毁灭化身苏鲁特（Surtr）。在与北欧之王迎来理念交锋的终局之战后，挥泪砍伐空想树宽边帽，宣告北欧历史闭幕。
            </p>
          </div>
        </router-link>

        <!-- Lostbelt 3 -->
        <router-link to="/logs/3" class="row-card">
          <div class="card-header">
            <div class="meta-left">DETECTION COORD: 34°12'N 108°54'E // 虚数路径锁定</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>PRUNED // 已剪定</span></div>
          </div>
          <h2 class="card-main-title">人智统合真国 · SIN</h2>
          <div class="card-date">人理异闻区划：中国异闻带 // 历史分歧节点：B.C. 210</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">异闻编号</div>
              <div class="param-value highlight">Lostbelt No.3</div>
            </div>
            <div class="param-group">
              <div class="param-label">人类史崩溃深度</div>
              <div class="param-value">E</div>
            </div>
            <div class="param-group">
              <div class="param-label">空想树 (真名)</div>
              <div class="param-value highlight" style="color: #5796D0;">Mayall (原为苗木/已被始皇帝活体解析)</div>
            </div>
            <div class="param-group">
              <div class="param-label">隐匿者 (Crypter)</div>
              <div class="param-value">芥雏子 (虞美人)</div>
            </div>
            <div class="param-group">
              <div class="param-label">异闻带之王</div>
              <div class="param-value ellipsis">始皇帝 · 嬴政</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">解密报告 / DETAILED ANALYSIS</div>
            <p class="summary-text">
              遭遇秦始皇寻得长生不老药、实现天下一统并高度机械化的世界。由于绝对的绝对统治与中央集权，真国消除了战争与饥荒，但也抹杀了平民求知、文学与文明的迭代可能。始皇帝以全真机械圣躯坐镇轨道阿房宫，并对空想树进行了反向解析与压制。面临芥雏子的叛变暴走，迦勒底在荆轲、韩信等英灵的因缘交织下突入咸阳。最终在战术决斗中赢得始皇帝器量认同，共同迎击空想树异变，剪定真国。
            </p>
          </div>
        </router-link>

        <!-- Lostbelt 4 -->
        <router-link to="/logs/4" class="row-card">
          <div class="card-header">
            <div class="meta-left">DETECTION COORD: 22°34'N 88°22'E // 虚数路径锁定</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>PRUNED // 已剪定</span></div>
          </div>
          <h2 class="card-main-title">创世灭界轮回 · 由伽 · 刹多罗</h2>
          <div class="card-date">人理异闻区划：印度异闻带 // 历史分歧节点：A.D. 1190?</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">异闻编号</div>
              <div class="param-value highlight">Lostbelt No.4</div>
            </div>
            <div class="param-group">
              <div class="param-label">人类史崩溃深度</div>
              <div class="param-value">A</div>
            </div>
            <div class="param-group">
              <div class="param-label">空想树 (真名)</div>
              <div class="param-value highlight" style="color: #5796D0;">Spiral (螺旋)</div>
            </div>
            <div class="param-group">
              <div class="param-label">隐匿者 (Crypter)</div>
              <div class="param-value">斯堪的纳维亚 · 佩佩隆奇诺</div>
            </div>
            <div class="param-group">
              <div class="param-label">异闻带之王</div>
              <div class="param-value ellipsis">阿周那 · Alter</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">解密报告 / DETAILED ANALYSIS</div>
            <p class="summary-text">
              遭遇吞噬了印度神话全神性、沦为纯粹"不改恶神"的狂周那所统领的创世世界。世界在极短周期内不断陷入"毁灭与再生"的死循环，任何带有微小瑕疵的生命体在轮回的末尾都会被粗暴抹消，导致文明严重萎缩。迦勒底联合隐匿者佩佩，在神之神王迦尔纳、迦内什等英灵的共同协助下，于创世祭坛上剥离狂周那的神性锚点。最终将其击落，伐倒空想树螺旋，解放无休止的轮回痛苦。
            </p>
          </div>
        </router-link>

        <!-- Lostbelt 5 -->
        <router-link to="/logs/5" class="row-card">
          <div class="card-header">
            <div class="meta-left">DETECTION COORD: 37°58'N 23°43'E // 虚数路径锁定</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>PRUNED // 已剪定</span></div>
          </div>
          <h2 class="card-main-title">神代巨神海洋 · 亚特兰蒂斯 / 奥林匹斯</h2>
          <div class="card-date">人理异闻区划：希腊异闻带 // 历史分歧节点：B.C. 12000</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">异闻编号</div>
              <div class="param-value highlight">Lostbelt No.5</div>
            </div>
            <div class="param-group">
              <div class="param-label">人类史崩溃深度</div>
              <div class="param-value">A+</div>
            </div>
            <div class="param-group">
              <div class="param-label">空想树 (真名)</div>
              <div class="param-value highlight" style="color: #5796D0;">Magellan (麦哲伦)</div>
            </div>
            <div class="param-group">
              <div class="param-label">隐匿者 (Crypter)</div>
              <div class="param-value">基尔什塔利亚 · 沃戴姆</div>
            </div>
            <div class="param-group">
              <div class="param-label">异闻带之王</div>
              <div class="param-value ellipsis">全能神 · 宙斯</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">解密报告 / DETAILED ANALYSIS</div>
            <p class="summary-text">
              遭遇一万二千年前大迁徙中未曾损毁、完全由宇宙机械机神统领的希腊神代世界。上层奥林匹斯都市拥有不可思议的超高度永生科技。隐匿者领袖沃戴姆以此为大本营，企图利用空想树将全人类提升为神。迦勒底经历了极度惨烈的海战与空降奇袭。依靠破神同盟（俄里翁、超人熊等英灵）付出决死代价，使用仿制黑枪（Black Barrel）击碎波塞冬、神祖奎里努斯协助跨越神门，最终击败全能宙斯。沃戴姆在遭受异星神背叛后，燃尽最后生命掩护迦勒底，希腊神界崩落。
            </p>
          </div>
        </router-link>

        <!-- Lostbelt 6 -->
        <router-link to="/logs/6" class="row-card">
          <div class="card-header">
            <div class="meta-left">DETECTION COORD: 54°00'N 2°00'W // 虚数路径锁定</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>PRUNED // 已剪定</span></div>
          </div>
          <h2 class="card-main-title">妖精圆桌领域 · 阿瓦隆 · 勒·菲</h2>
          <div class="card-date">人理异闻区划：不列颠妖精国 // 历史分歧节点：B.C. 14000</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">异闻编号</div>
              <div class="param-value highlight">Lostbelt No.6</div>
            </div>
            <div class="param-group">
              <div class="param-label">人类史崩溃深度</div>
              <div class="param-value">EX</div>
            </div>
            <div class="param-group">
              <div class="param-label">空想树 (真名)</div>
              <div class="param-value highlight" style="color: #5796D0;">Seyfert (施洗 / 已被摩根吸干转化为魔力源)</div>
            </div>
            <div class="param-group">
              <div class="param-label">隐匿者 (Crypter)</div>
              <div class="param-value">贝里尔 · 伽特</div>
            </div>
            <div class="param-group">
              <div class="param-label">异闻带之王</div>
              <div class="param-value ellipsis">女王 · 摩根</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">解密报告 / DETAILED ANALYSIS</div>
            <p class="summary-text">
              遭遇因六罪妖精怠惰、未曾锻造圣剑导致世界白纸化分歧的极端异事象。异闻带本身并不依赖空想树，而是靠女王摩根以极度冷酷的"水镜"魔术吸干树之魔力维持的妖精国度。内政充斥着欺骗、恶意、宿命与周期性的"大灾厄"。迦勒底与预言之子阿尔托莉雅·卡斯特结伴，历经数个氏族的背叛与绝望。虽女王摩根惨遭算计身亡，但毁灭级的大灾厄"奈落之虫"在妖精国内地深处彻底爆发。最终，预言之子燃尽自身锻造真正的圣剑击穿星之终焉，在最悲壮的泪水中将妖精不列颠彻底剪定。
            </p>
          </div>
        </router-link>

        <!-- Lostbelt 7 -->
        <router-link to="/logs/7" class="row-card">
          <div class="card-header">
            <div class="meta-left">DETECTION COORD: 0°00'N 60°00'W // 虚数路径锁定</div>
            <div class="meta-right"><span class="badge"><span class="badge-dot"></span>PRUNED // 已剪定</span></div>
          </div>
          <h2 class="card-main-title">黄金树海纪行 · 纳维 · 灵林</h2>
          <div class="card-date">人理异闻区划：南美异闻带 // 历史分歧节点：B.C. 6000000</div>
          <div class="parameter-panel">
            <div class="param-group">
              <div class="param-label">异闻编号</div>
              <div class="param-value highlight">Lostbelt No.7</div>
            </div>
            <div class="param-group">
              <div class="param-label">人类史崩溃深度</div>
              <div class="param-value">ORTE (无法测定)</div>
            </div>
            <div class="param-group">
              <div class="param-label">空想树 (真名)</div>
              <div class="param-value highlight" style="color: #5796D0;">Quasar (类星体)</div>
            </div>
            <div class="param-group">
              <div class="param-label">隐匿者 (Crypter)</div>
              <div class="param-value">戴比特 · 塞姆 · 沃伊德</div>
            </div>
            <div class="param-group">
              <div class="param-label">异闻带之王</div>
              <div class="param-value ellipsis">库库尔坎 / 烟雾镜</div>
            </div>
          </div>
          <div class="summary-section">
            <div class="summary-title">解密报告 / DETAILED ANALYSIS</div>
            <p class="summary-text">
              遭遇陨石未曾撞击地球、恐龙（迪诺斯）演化出高度哲学文明、并与古老迈雅神性共存的地下太阳世界（埃尔多拉多）。此处的终极威胁并非异闻之王，而是沉睡在最深处的宇宙终结生物——大蜘蛛 ORT。隐匿者戴比特意图唤醒 ORT 毁灭全宇宙以纠正泛人类史的残忍，迦勒底在冥界裁判与各种神性博弈中前行。最终爆发了惨烈至极的"ORT 连战"，英灵召唤系统超负荷运转。在击破星之终端库库尔坎与大蜘蛛的究极构体后，全面拔除空想树类星体，完成了第一部人理收束的终局反攻。
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
