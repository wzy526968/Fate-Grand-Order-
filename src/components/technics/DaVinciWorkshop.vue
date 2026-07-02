<template>
  <div class="workshop">
    <FrostedPanel title="达芬奇工房 / DA VINCI'S WORKSHOP" variant="terminal" class="room-panel">
      <div class="workshop-layout">
        <!-- Left: Mystic Code Directory -->
        <div class="workshop-left">
          <div class="section-title">■ 概念魔术礼装 / MYSTIC CODES</div>
          <div class="toc-container">
            <div v-for="group in groupedCodes" :key="group.group" class="toc-group">
              <div class="toc-group-title">{{ group.group }}</div>
              <div
                v-for="code in group.items"
                :key="code.id"
                :class="['toc-entry', { active: selectedCode?.id === code.id }]"
                @click="selectCode(code)"
              >
                <span class="toc-icon">{{ code.icon }}</span>
                <span class="toc-name">{{ code.name }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Right: Detail Display -->
        <div class="workshop-right">
          <div v-if="selectedCode" class="detail-container">
            <div class="detail-header">
              <span class="detail-icon">{{ selectedCode.icon }}</span>
              <div class="detail-title-group">
                <h3 class="detail-name">{{ selectedCode.name }}</h3>
                <span class="detail-group-badge">{{ selectedCode.group }}</span>
              </div>
            </div>

            <div class="detail-section">
              <div class="detail-section-title">■ 设定 / SETTING</div>
              <p class="detail-text">{{ selectedCode.lore }}</p>
            </div>

            <div class="detail-section">
              <div class="detail-section-title">■ 技能 / SKILLS</div>
              <div v-for="skill in selectedCode.skills" :key="skill.name" class="skill-card">
                <span class="skill-name">{{ skill.name }}</span>
                <span class="skill-effect">{{ skill.effect }}</span>
              </div>
            </div>

            <div v-if="selectedCode.description" class="detail-section">
              <div class="detail-section-title">■ 解说 / COMMENTARY</div>
              <p class="detail-text detail-commentary">{{ selectedCode.description }}</p>
            </div>
          </div>

          <!-- Empty state -->
          <div v-else class="detail-empty">
            <div class="empty-icon">◈</div>
            <p>选择左侧的魔术礼装以查看详细设定</p>
            <p class="empty-sub">SELECT A MYSTIC CODE TO VIEW ITS DETAILS</p>
          </div>
        </div>
      </div>
    </FrostedPanel>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import FrostedPanel from '../FrostedPanel.vue'

const selectedCode = ref(null)

const mysticCodes = [
  // ── 周年纪念 ──
  {
    id: 'anniversary-scarlet',
    name: '周年纪念·绯红',
    group: '周年纪念',
    icon: '✦',
    skills: [
      { name: '高速咏唱', effect: '自身的NP以高概率增加' },
      { name: '守护者', effect: '赋予自身无敌状态（1回合）' },
      { name: '月之记忆', effect: '赋予自身回避状态（1回合）＆NP获得量提升' },
    ],
    lore: '为纪念人理续存保障机构·迦勒底持续运营而特别设计的魔术礼装。以象征迦勒底标识的深红为主色调，兼具正式场合的礼仪性与实战中的功能性。礼装上绣有以所罗门王指环为原型的纹章，承载着延续人理的意志与记忆。',
    description: '迦勒底周年纪念之际，由达芬奇亲主持设计的特别礼装。在迦勒底制服的基础上融入了魔术协会的古典元素，袖口与领口的金色刺绣采用了真正的魔术刻印技术，能够在紧急情况下启动简易的防御结界。',
  },
  {
    id: 'bright-ancient',
    name: '明亮远古',
    group: '周年纪念',
    icon: '◆',
    skills: [
      { name: '充能', effect: '自身的NP增加' },
      { name: '堡垒', effect: '赋予自身无敌状态（1回合）＆防御力提升' },
      { name: '再生', effect: '赋予自身每回合HP回复状态' },
    ],
    lore: '以古老魔术文明的设计理念为基础开发的魔术礼装。采用了已被遗忘的魔术回路配列方式，在礼装上再现了古代魔术师的战斗风格。金色的纹饰象征着"光之魔法"的传承。',
    description: '与迦勒底的标准魔术礼装截然不同的设计，采用了更为古老的魔术理论。据说在设计蓝图中混入了来自"某个时代"的魔术王的设想，礼装整体的魔力传导效率远超现代魔术礼装的标准规格。',
  },

  // ── 迦勒底制式 ──
  {
    id: 'chaldea-mystic-code',
    name: '迦勒底魔术礼装',
    group: '迦勒底制式',
    icon: '⊙',
    skills: [
      { name: '应急魔力供给', effect: '大幅提升自身NP获得量（3回合）' },
      { name: '环境防护强化', effect: '赋予自身对异常状态的耐性提升（3回合）＆防御力提升' },
    ],
    lore: '迦勒底技术部为御主量身定制的防护服。内置了小型魔力精炼炉，能将微弱的魔力成倍放大，是让毫无魔术天赋的普通人也能在极端特异点或异闻带中强行支撑英灵物质化与宝具解放的生命保障系统。该礼装的设计浓缩了迦勒底最尖端的人理保障技术。',
    description: '作为迦勒底御主的标准装备之一，这套防护服融合了魔术协会与近代科学技术的精粹。胸前的魔力精炼炉可以自动抽取大气中的微弱魔力进行压缩增幅，确保御主在任何异常环境下都能稳定维持与从者的灵基连接。其纤维结构采用了拟似神经回路编织，能够实时感知穿着者的身体状况并进行环境适应调整。',
  },

  // ── 传说宝具 ──
  {
    id: 'avalon',
    name: '阿瓦隆',
    group: '传说宝具',
    icon: '✦',
    skills: [
      { name: '绝对隔绝', effect: '赋予自身无敌状态（持续不可解除）' },
      { name: '无限自愈', effect: '赋予自身每回合大量HP回复状态' },
    ],
    lore: '亚瑟王圣剑的剑鞘，属于型月世界最顶级的概念防卫武装。剑鞘本身即为"妖精乡"的具象化，被埋入宿主肉体后，能赋予持有者超越人类极限的自愈能力。一旦完全解放，阿瓦隆会将使用者放逐到连五大魔法都无法触及的妖精乡，从宇宙中隔绝世间的一切物理与魔术干涉。',
    description: '传说中的圣剑之鞘，自亚瑟王传说中的星之内海流出的神秘结晶。它所携带的概念并非"防御"，而是将持有者从世界中"隔离"开来——只要处于解放状态，持有者所处的空间坐标便被移动到世界之外。理论上没有任何攻击能够触及处于阿瓦隆守护下的存在。在迦勒底的记录中，这是被誉为"最强守护"的终极概念礼装。',
  },
  {
    id: 'enkidu',
    name: '天之锁',
    group: '传说宝具',
    icon: '≡',
    skills: [
      { name: '对神专用律法禁锢', effect: '赋予敌方单体束缚状态（神性越高成功率越高）' },
    ],
    lore: '吉尔伽美什王之财宝中最引以为傲的王牌兵装。天之锁本身并不具备物理破坏能力，但其禁锢效果与目标的"神性"等级形成正比——被捕获的目标神性越高，锁链的硬度和规则压制力就会呈几何级数暴涨。面对拥有至高神性的对象时，天之锁的束缚力将上升至连神明也无法挣脱的绝对规则领域。',
    description: '恩奇都的遗骸所化作的对神束缚链，在吉尔伽美什的王之财宝中占有最特殊的席位。根据迦勒底的魔术分析，天之锁并非单纯的物理拘束具，而是一段被具象化的"律法"——当锁链缠绕上神性目标时，它会自动读取并复制其神性数据，以此为燃料不断增强束缚力。这一机制使得天之锁在对神作战中展现出近乎概念级的压制力。',
  },
  {
    id: 'rho-aias',
    name: '炽天覆七重圆环',
    group: '传说宝具',
    icon: '◎',
    skills: [
      { name: '投掷类兵器绝对防御', effect: '赋予自身对投掷攻击的绝对防御状态（1回合）' },
      { name: '七重防护', effect: '赋予自身防御力大幅提升（1次·不可解除）' },
    ],
    lore: '特洛伊战争中埃阿斯所持青铜盾的概念具象化。在被无名英灵和卫宫士郎以投影魔术再现之后，表现为七重樱花般的魔力光盾，每一片花瓣都代表一道坚不可摧的防线。在概念层面上，炽天覆七重圆环对所有远程投掷类兵器拥有绝对克制——它是"跨越一切投掷攻击"这一传说的结晶体。',
    description: '特洛伊英雄埃阿斯的七层牛革青铜盾升华而来的概念武装。每一层花瓣都是一面独立的结界盾牌，拥有独立的魔力基底与耐久判定。值得一提的是，这面盾牌并非完全透明的魔力障壁——每一层都继承了原典中"盾牌"的本质，是确凿存在的物理屏障。据迦勒底推测，其理论极限能够承受对城级宝具的正面冲击，只是对穿刺类攻击的耐久相对较低。',
  },

  // ── 神代礼装 ──
  {
    id: 'fragarach',
    name: '逆光剑',
    group: '神代礼装',
    icon: '⇚',
    skills: [
      { name: '因果律后发先至', effect: '在敌方释放宝具时发动，先于对手造成伤害＆解除敌方强化状态' },
    ],
    lore: '巴泽特家族世代传承的凯尔特神代迎击礼装。其运作原理完全违背了常理的因果序列——当敌人释放出必杀招式或宝具的刹那，这把剑会以"后发先至"的方式强行扭曲时间因果，在对手出招之前先一步贯穿其核心，并直接从概念层面上抹消对手已然释放的攻击。',
    description: '凯尔特神话中光之神的遗产，由巴泽特家族世代守护的迎击型礼装。在最古老的传承中，它是神族用来对抗巨人的兵器。其真正恐怖之处在于它不遵循"先攻先中"的物理法则，而是将"后出手者必定先命中"这一逆反因果律规则强行写入现实。对以强大宝具为决胜手段的敌人而言，逆光剑是如天敌般的存在。',
  },
  {
    id: 'primordial-darkness',
    name: '原初之漆黑',
    group: '神代礼装',
    icon: '❖',
    skills: [
      { name: '魔境隔绝', effect: '赋予自身对诅咒·毒害的无效状态（3回合）' },
      { name: '因果阻断', effect: '赋予自身对因果律攻击的耐性提升（3回合）' },
    ],
    lore: '影之国女王斯卡哈穿戴的贴身防具，由影之国深渊的影咒与神代魔术纤维编织而成。除了提供高额的伤害豁免之外，它还附带了"世界边缘"的隔绝概念，能够彻底隔绝诅咒与毒害，并在一定程度上阻断因果律魔术对肉体的追踪与锁定。',
    description: '来自影之国尽头的漆黑防具，浸润了斯卡哈弑神千年的领域之力。根据迦勒底的分析，该礼装的织物结构实际上是一种微型的固有结界，每根纤维都连接着影之国的深渊边界。当佩戴者承受伤害或诅咒时，伤害被转移至影之国虚空中消化，而非由肉体承受。这正是"与影之国相连"这一概念的防具化体现。',
  },

  // ── 迦勒底标准 ──
  {
    id: 'chaldea-travelsuit',
    name: '迦勒底旅行装束',
    group: '迦勒底标准',
    icon: '●',
    skills: [
      { name: '瞬足', effect: '己方单体的Quick卡性能提升' },
      { name: '永恒装甲', effect: '赋予己方单体防御力提升状态（3回合）' },
      { name: '替换指令', effect: '与替补从者进行交换' },
    ],
    lore: '迦勒底为御主在灵子转移及野外任务中配备的标准制式魔术礼装。兼顾机动性与防护性，采用了能够适应各个时代气象环境的特殊纤维材料。袖口内侧缝有紧急医疗用的简易魔术刻印。',
    description: '迦勒底标准装备之一，以"在任何时代都能行动"为设计理念。与迦勒底统一制服相比，减少了正式礼仪性设计，更强调穿着者的活动便利性。胸口的内袋中收纳有便携式灵子通讯终端。',
  },

  // ── 阿特拉斯院 ──
  {
    id: 'atlas-uniform',
    name: '阿特拉斯制服',
    group: '阿特拉斯院',
    icon: '⊞',
    skills: [
      { name: '局部时间倒流', effect: '减少己方单体技能冷却（3回合）' },
      { name: '精神分割算力', effect: '提升己方单体NP获得量＆暴击威力（3回合）' },
    ],
    lore: '由极度崇尚炼金术与末日炼狱哲学的阿特拉斯院所制作的顶级炼金礼装。与传统的魔术礼装截然不同，其功能纯粹基于硬核的炼金科学与天文台式计算理论，能够实现局部时间逆转或强行分割并提高思维算力，是专门为了应对世界末日而构筑的概念规则道具。',
    description: '阿特拉斯院引以为傲的技术结晶。不同于迦勒底装备的实用主义设计，阿特拉斯制服有着浓厚的炼金哲学色彩——其制作者秉持着"唯有极致的技术才能在末日中拯救世界"的信条。该礼装的核心是利用虚数空间理论对时间轴进行微调，尽管影响范围极为有限，但在生死一线的魔术战斗中，这毫厘之差足以改写战局。',
  },
  {
    id: 'atlas-install',
    name: '阿特拉斯院·安装',
    group: '阿特拉斯院',
    icon: '⊡',
    skills: [
      { name: '驱散', effect: '解除己方单体的弱化状态' },
      { name: '召唤', effect: '增加己方单体的NP' },
      { name: '革新', effect: '减少己方单体技能冷却（1回合）' },
    ],
    lore: '仿制阿特拉斯院制式礼装的迦勒底改良版本。继承了阿特拉斯院"炼金术与兵器开发"的技术传统，在礼装中嵌入了疑似以太结晶的小型化版本，能够实现一定程度的魔力操作。',
    description: '以与迦勒底有深厚渊源的魔术组织——阿特拉斯院的技术为基础的魔术礼装。与迦勒底标准礼装不同，该礼装更侧重于战术性魔术支援。礼装配有阿特拉斯院特色的银色金属装饰，具有较高的魔力耐性。',
  },
  {
    id: 'black-gun',
    name: '黑之枪',
    group: '阿特拉斯院',
    icon: '✙',
    skills: [
      { name: '逆天命寿命赋予', effect: '对敌方单体造成无视防御的极大伤害（神性越高伤害越大幅提升）' },
    ],
    lore: '由阿特拉斯院基于末日炼金理论，在迦勒底第二部探索期间仿制出来的究极决战兵装。这把枪能够发射出彻底否定"第五真说"的子弹，其本质是将"死亡"概念强行赋予目标的概念装置——目标拥有的神性或寿命越趋近于无限，它造成的概念破坏就越呈毁灭性的指数级叠加。',
    description: '阿特拉斯院七大兵器之一的仿制版。原典是被称为"将死赋予活物"的天文学兵器，专门用于对近乎不朽的对象进行概念抹消。迦勒底版本的运作原理基于对"灵基寿命"的干涉——子弹命中的瞬间，枪械强行计算目标的理论寿命并直接压缩到零。越是古老、拥有漫长寿命的存在，受到的损伤就越为致命。对普通人类反而几乎没有效果。',
  },

  // ── 概念礼装 ──
  {
    id: 'rule-breaker',
    name: '万戒必破之符',
    group: '概念礼装',
    icon: '⊜',
    skills: [
      { name: '契约强制解除', effect: '解除敌方单体强化状态＆解除敌方单体防御强化状态' },
      { name: '术式初始化', effect: '解除敌方单体所附带的增益结界·契约效果' },
    ],
    lore: '背叛魔女美狄亚所持有的特殊短刀。此刃不具备物理杀伤力，但带有最高顺位的"破戒"概念——它能够将一切以魔力契约、魔术强化或精神控制构筑的术式强制瞬间初始化，使其回归到未被施加前的原始状态。连绝对且不可逆的御主-从者契约，在这柄短刀面前也如同一纸空文。',
    description: '传说中背叛的魔女锻造的破戒之刃，其概念优先级在迦勒底登记的概念礼装中位居最上位。这把短刀的攻击对象不是肉体，而是"规则"本身——无论是魔术师的术式结界、从者的契约锁链，还是施加于地脉的诅咒，只要是以魔力构筑的"联系"或"契约"，都会被其强制切断。在迦勒底的模拟演算中，万戒必破之符理论上甚至能够干涉圣杯系统的契约架构。',
  },
  {
    id: 'volumen-hydrargyrum',
    name: '月灵髓液',
    group: '概念礼装',
    icon: '◈',
    skills: [
      { name: '充魔液态水银', effect: '赋予自身对物理攻击的防御力大幅提升（3回合）＆反击状态' },
      { name: '自动迎击', effect: '概率赋予自身回避状态（3次·3回合）' },
    ],
    lore: '时钟塔十二君主之一肯尼斯家族的传家至宝。将多达140公斤的充魔水银作为礼装，能够完全根据使用者的意志在瞬间解析并做出反应——既可化为防御力堪比坦克装甲的护盾，亦可化为薄如蝉翼却足以切断钢筋的凶刃。水银的流体会自动感知攻击并主动拦截，堪称自动迎击型的万能礼装。',
    description: '君主·艾尔梅洛伊引以为傲的月灵髓液，是时钟塔最顶尖的魔术礼装杰作之一。这团充魔水银内部刻印着数千层简化魔术回路，使其拥有了近乎生物般的自主反应能力。它最令人忌惮的特性在于"全体同时防御"——水银没有固定的形态，因此可以从所有角度同时进行防御与反击，不存在传统盾牌的盲区。唯一的弱点是需要持续注入魔力维持活性化状态。',
  },
  {
    id: 'heavens-dress',
    name: '天之衣',
    group: '概念礼装',
    icon: '⊕',
    skills: [
      { name: '灵魂干涉', effect: '赋予自身对从者·灵体的支配力提升（3回合）' },
      { name: '拟似第三魔法', effect: '赋予自身战续状态（1次·5回合）＆NP增加' },
    ],
    lore: '爱因兹贝伦家族以纯金线编织而成的魔术礼装，专为圣杯战争中的"大圣杯控制者"量身打造。此衣拥有干涉并控制灵魂的绝对权限，能够强行吸收并操纵物质化后的从者灵魂，是部分实现了型月世界中第三魔法的拟似造物。穿上天之衣的那一刻，穿着者便不再是人类，而是大圣杯本身。',
    description: '爱因兹贝伦家族耗费千年追寻第三魔法——灵魂物质化的结晶。天之衣本身即为"通往根源的仪式"的具象化，穿戴着它的人将获得对大圣杯系统的一定控制权限，能够干涉从灵基到灵魂的全范畴信息。迦勒底的记录指出，天之衣本质上是将冬木大圣杯的控制单元压缩为一件礼装的尝试，其灵魂干涉优先级在所有已知概念礼装中名列前茅。',
  },
  {
    id: 'kaleid-ruby',
    name: '万华镜红宝石 / Kaleid Ruby',
    group: '概念礼装',
    icon: '◇',
    skills: [],
    lore: '以"万华镜"为设计概念的高能量概念礼装。相传是由宝石翁传承下来的魔术理论为基础铸造的礼装。能够将外界的魔力进行多重折射与增幅，实现超越常规魔术礼装的魔力供给。',
    description: '被归类为"概念礼装"的特殊魔术礼装，并非迦勒底的标准装备。据达芬奇所述，其设计灵感来源于某个平行世界中的魔法少女概念。礼装中蕴含着极为强大的魔力炉心，理论上可以实现近乎无限的魔力供给。',
  },
  {
    id: 'spiritron-tale',
    name: '灵子谭 / Spiritron Tale',
    group: '概念礼装',
    icon: '□',
    skills: [],
    lore: '记录着灵子转移技术开发历程的魔术礼装型记录装置。内部封印着初期灵子转移实验的详细数据与御主的灵子档案。礼装本身即为一段被魔术化的记录——"故事"。',
    description: '迦勒底灵子转移技术早期阶段的珍贵记录，以概念礼装的形式被保存下来。礼装内部以魔术刻印的方式刻录了大量实验数据，既是礼装也是数据库。在魔力激活状态下，能够读取到实验者的残留思念。',
  },
  {
    id: 'moonlight-festival',
    name: '月光祝祭 / Moonlight Festival',
    group: '概念礼装',
    icon: '○',
    skills: [],
    lore: '以"月之胜利"为主题铸造的庆典纪念礼装。月光被认为是连接"星之世界"与"人之世界"的媒介，此礼装以月光之力为媒介，为持有者带来祝福。月夜的魔力增幅效果尤为显著。',
    description: '在某个特殊时间点铸造的纪念礼装。礼装上描绘着月光照耀下的祭典景象，其中暗含了多个与月亮相关的魔术符号。据说在满月之夜，礼装上的纹样会浮现出微弱的光芒。',
  },
  {
    id: 'heroic-spirit-travel',
    name: '英灵纪行 / Heroic Spirit Travel',
    group: '概念礼装',
    icon: '▣',
    skills: [],
    lore: '记录着英灵们在人类史上留下足迹的旅程之概念礼装。每一位英灵的传说都是一段旅途，而这件礼装则将无数旅途编织为一。持有者能够从中感受到跨越时空的英灵们的意志与信念。',
    description: '迦勒底记录的各项特异点与人类史事件的概念化礼装。礼装本身呈现为一幅古老的旅行地图，上面标注着各个特异点的位置与对应的英灵之名。越是强大的英灵，其对应的光点就越发闪耀。',
  },

  // ── 死徒祖礼装 ──
  {
    id: 'white-wing-banner',
    name: '白翼公的城旗',
    group: '死徒祖礼装',
    icon: '▣',
    skills: [
      { name: '死徒法则覆盖', effect: '降低敌方全体的攻击力·防御力（3回合）' },
      { name: '抑止力压制', effect: '降低敌方全体的NP获得量＆暴击率（3回合）' },
    ],
    lore: '死徒二十七祖之一白翼公持有的固有结界型礼装。作为吸血鬼高层的权威象征，这面城旗一旦展开，便在物理世界中强行覆盖一片猩红的鲜血领域。在这片领域中，死徒的物理法则被优先映射，人类的抑止力被全面压制，访客的魔术回路将彻底失效，沦为只能任人宰割的羔羊。',
    description: '死徒之王们争夺的权柄象征之一，据传是白翼公从月之世界带来的遗产。这面旗帜并非用布料织成，而是以凝固的鲜血与影子纺制。它展开的不是结界，而是"死徒的物理法则被写入现实"的领域——在这一范围内，只有被白翼公认可的存在才能行使魔力，一切不被承认的外来术式都将被世界本身拒绝。迦勒底将这种效果归类为"拟似世界改写"型概念礼装。',
  },
]

const groupedCodes = computed(() => {
  const groups = {}
  for (const code of mysticCodes) {
    if (!groups[code.group]) {
      groups[code.group] = { group: code.group, items: [] }
    }
    groups[code.group].items.push(code)
  }
  return Object.values(groups)
})

function selectCode(code) {
  selectedCode.value = code
}
</script>

<style scoped>
.workshop {
  height: calc(100vh - 230px);
  overflow: hidden;
  display: flex;
  flex-direction: column;
}
.room-panel {
  flex: 1;
  min-height: 0;
}

/* Override panel-body — disable its built-in scrolling and padding */
.room-panel :deep(.panel-body) {
  overflow: hidden !important;
  padding: 0 !important;
  flex: 1;
  min-height: 0;
  display: flex;
  flex-direction: column;
}

.workshop-layout {
  display: flex;
  gap: 16px;
  flex: 1;
  min-height: 0;
  overflow: hidden;
  padding: 14px 16px;
}

/* ===== Left: Directory ===== */
.workshop-left {
  flex: 0 0 300px;
  overflow-y: auto;
  min-height: 0;
  border-right: 1px solid rgba(255,255,255,0.06);
  padding-right: 12px;
}
.workshop-left::-webkit-scrollbar { width: 2px; }
.workshop-left::-webkit-scrollbar-thumb { background: rgba(87,150,208,0.3); }

.section-title {
  font-family: 'JetBrains Mono', monospace;
  font-size: 10px;
  color: rgba(255,255,255,0.6);
  margin-bottom: 12px;
  letter-spacing: 1px;
  flex-shrink: 0;
}

.toc-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.toc-group {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.toc-group-title {
  font-family: 'JetBrains Mono', monospace;
  font-size: 8px;
  color: rgba(255,255,255,0.2);
  letter-spacing: 2px;
  padding: 4px 0 2px;
  border-bottom: 1px solid rgba(255,255,255,0.04);
  margin-bottom: 2px;
}

.toc-entry {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 8px;
  cursor: pointer;
  transition: all 0.2s;
  border: 1px solid transparent;
  border-radius: 3px;
}
.toc-entry:hover {
  background: rgba(87,150,208,0.06);
  border-color: rgba(87,150,208,0.12);
}
.toc-entry.active {
  background: rgba(87,150,208,0.1);
  border-color: rgba(87,150,208,0.25);
}
.toc-icon { font-size: 16px; flex-shrink: 0; }
.toc-name {
  font-family: 'JetBrains Mono', monospace;
  font-size: 9px;
  color: rgba(255,255,255,0.65);
  transition: color 0.2s;
}
.toc-entry.active .toc-name { color: #5796D0; }
.toc-entry:hover .toc-name { color: rgba(255,255,255,0.85); }

/* ===== Right: Detail ===== */
.workshop-right {
  flex: 1;
  overflow-y: auto;
  padding-left: 4px;
  min-height: 0;
}
.workshop-right::-webkit-scrollbar { width: 2px; }
.workshop-right::-webkit-scrollbar-thumb { background: rgba(87,150,208,0.3); }

.detail-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
  animation: fadeIn 0.35s ease;
}

/* Detail header */
.detail-header {
  display: flex;
  align-items: center;
  gap: 14px;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(87,150,208,0.15);
}
.detail-icon {
  font-size: 36px;
  flex-shrink: 0;
}
.detail-title-group {
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.detail-name {
  font-size: 16px;
  font-weight: 600;
  margin: 0;
  color: rgb(255,255,255);
  font-family: 'JetBrains Mono', monospace;
}
.detail-group-badge {
  font-size: 8px;
  font-family: 'JetBrains Mono', monospace;
  letter-spacing: 1px;
  color: rgba(87,150,208,0.7);
  padding: 2px 8px;
  border: 1px solid rgba(87,150,208,0.2);
  background: rgba(87,150,208,0.06);
  align-self: flex-start;
}

/* Detail sections */
.detail-section {
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.detail-section-title {
  font-family: 'JetBrains Mono', monospace;
  font-size: 9px;
  color: rgba(255,255,255,0.4);
  letter-spacing: 1px;
}
.detail-text {
  font-size: 11px;
  color: rgba(255,255,255,0.55);
  line-height: 1.8;
  margin: 0;
}
.detail-commentary {
  color: rgba(255,255,255,0.45);
  font-size: 10px;
  border-left: 2px solid rgba(87,150,208,0.15);
  padding-left: 12px;
}

/* Skill cards */
.skill-card {
  display: flex;
  flex-direction: column;
  gap: 3px;
  padding: 8px 10px;
  border: 1px solid rgba(255,255,255,0.06);
  background: rgba(0,0,0,0.12);
  transition: border-color 0.2s;
}
.skill-card:hover {
  border-color: rgba(87,150,208,0.2);
}
.skill-name {
  font-family: 'JetBrains Mono', monospace;
  font-size: 10px;
  color: #5796D0;
  letter-spacing: 0.5px;
}
.skill-effect {
  font-size: 9px;
  color: rgba(255,255,255,0.4);
  line-height: 1.5;
}

/* Empty state */
.detail-empty {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  min-height: 300px;
  gap: 8px;
}
.empty-icon {
  font-size: 36px;
  color: rgba(87,150,208,0.15);
  margin-bottom: 8px;
}
.detail-empty p {
  font-family: 'JetBrains Mono', monospace;
  font-size: 10px;
  color: rgba(255,255,255,0.2);
  margin: 0;
  letter-spacing: 1px;
}
.empty-sub {
  font-size: 8px !important;
  color: rgba(255,255,255,0.12) !important;
}

/* Animations */
@keyframes fadeIn {
  0% { opacity: 0; transform: translateY(6px); }
  100% { opacity: 1; transform: translateY(0); }
}

/* Responsive */
@media (max-width: 900px) {
  .workshop-layout { flex-direction: column; }
  .workshop-left { flex: none; width: 100%; border-right: none; border-bottom: 1px solid rgba(255,255,255,0.06); padding-bottom: 12px; max-height: 200px; }
}
</style>
