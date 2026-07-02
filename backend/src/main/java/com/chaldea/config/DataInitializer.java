package com.chaldea.config;

import com.chaldea.model.*;
import com.chaldea.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final ServantClassRepository classRepository;
    private final ServantRepository servantRepository;
    private final UserProfileRepository profileRepository;
    private final CoffinRepository coffinRepository;
    private final ResourceRepository resourceRepository;
    private final SystemMetricsRepository systemMetricsRepository;
    private final AlertRepository alertRepository;
    private final EventLogRepository eventLogRepository;
    private final PersonnelRepository personnelRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           ServantClassRepository classRepository,
                           ServantRepository servantRepository,
                           UserProfileRepository profileRepository,
                           CoffinRepository coffinRepository,
                           ResourceRepository resourceRepository,
                           SystemMetricsRepository systemMetricsRepository,
                           AlertRepository alertRepository,
                           EventLogRepository eventLogRepository,
                           PersonnelRepository personnelRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.classRepository = classRepository;
        this.servantRepository = servantRepository;
        this.profileRepository = profileRepository;
        this.coffinRepository = coffinRepository;
        this.resourceRepository = resourceRepository;
        this.systemMetricsRepository = systemMetricsRepository;
        this.alertRepository = alertRepository;
        this.eventLogRepository = eventLogRepository;
        this.personnelRepository = personnelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Personnel seeding runs independently — always fills if empty
        if (personnelRepository.count() == 0) {
            seedPersonnel();
            log.info("Created personnel records");
        } else {
            log.info("Personnel already seeded, skipping.");
        }

        if (userRepository.count() > 0) {
            log.info("Database already initialized, skipping seed.");
            return;
        }

        log.info("Seeding database for first time...");

        String defaultPassword = passwordEncoder.encode("chaldea123");

        User ritsuka = createUser("藤丸立香", defaultPassword, "藤丸立香");
        User mash = createUser("玛修·基列莱特", defaultPassword, "玛修·基列莱特");
        User davinci = createUser("达·芬奇", defaultPassword, "达·芬奇");
        User romani = createUser("罗马尼·阿基曼", defaultPassword, "罗马尼·阿基曼");
        log.info("Created 4 admin accounts (password: chaldea123)");

        ServantClass saber = createClass("Saber", "");
        ServantClass archer = createClass("Archer", "");
        ServantClass lancer = createClass("Lancer", "");
        ServantClass rider = createClass("Rider", "");
        ServantClass caster = createClass("Caster", "");
        ServantClass assassin = createClass("Assassin", "");
        ServantClass berserker = createClass("Berserker", "");
        log.info("Created 7 servant classes");

        Servant sSaber1 = createServant(saber, "阿尔托莉雅·潘德拉贡", "亚瑟王", 5,
                8, 7, 7, 8, 6, 10,
                "经由冬木事象记录提取的剑之骑士。该灵体之泛人类史真名为阿尔托莉雅·潘德拉贡，系古代不列颠事象分支之核心统治者亚瑟王。其灵基构造展现出极高等级的神秘度壁垒，具备跨越复合维度的魔力防御架构（即高阶对魔力防壁），其基础物理参数、敏捷度以及灵力转化率均处于最优化均衡输出状态。该个体体内铭刻有特殊神代因子，为其提供几近无限的拟似大源魔力供给。其武装系统中搭载的最高序列输出宝具为「誓约胜利之剑」，该兵器并非由人类技术铸造，而是行星地表将星体光能量进行超高度压缩凝练后具现化的神造兵器。在真名解放状态下，其内部蓄积的灵子将转化为定向高能指向性热线，以光粒子洪流的形式对目标区域执行大范围的物质湮灭与结构崩解，在能量测算分级中属于绝对的对城级灵力破坏兵器。其战斗逻辑高度推崇正面遭遇战与阵地突防，综合灵基评定为该职阶之典范。");

        Servant sSaber2 = createServant(saber, "冲田总司", "新选组一番队队长", 5,
                6, 5, 10, 4, 5, 8,
                "十九世纪幕末时期新选组一番队队长。该个体在灵子测算中表现出极端的不稳定性，由于其泛人类史生前事象的生物学局限，其灵体内部存在因果层面的固有缺陷，在长期、高强度的灵力输出或高速移动状态下，会导致灵核出现间歇性解体风险（即概念层面的病弱呈现）。然而，该对象凭借其在近现代白刃战领域所达到的极致纯粹的技术高度，成功在缺乏高位神秘度加持的前提下，跨越了英灵殿的神秘度考核壁垒。其特化型战术动作为概念级机动术式「缩地」，可实现瞬间缩短空间相对距离的绝对先攻。其衍生出的核心剑技「三段突刺」，在刺出瞬间可导致局部的时空轴扭曲，使水平方向的三次突刺在同一空间坐标、同一时间视轴上达成三重重叠。这一物理反常现象属于超越三维物理极限的概念级局部降维打击，无法进行常规的几何轨迹闪避或物理防御，系技术升华为神秘的典型范例。");

        Servant sSaber3 = createServant(saber, "尼禄·克劳狄乌斯", "罗马皇帝", 5,
                7, 6, 6, 7, 6, 8,
                "罗马帝国第五代皇帝，朱里亚·克劳狄王朝的终结者。该英灵在基础灵基匹配测算中，原本不具备常规剑之骑士职阶的适格参数与战技判定，但其凭借极度高涨、甚至扭曲因果的强烈自我意识与精神辐射，强行运用灵体附带的「皇帝特权」概念权限，在圣杯系统的底层逻辑中实施了非法的数据篡改与改写，进而得以介入该职阶。她持有由自身审美意志干涉现实物质而铸造的深红大剑「原初之火」，其刃部可随其情绪魔力的波动释放高热灵子辐射。其最为核心的战术手段，是跨越常规魔术的阵地建造限制，以自身心象风景直接干涉大世界物理法则，强行具现化名为「黄金剧场」的固有结界变体。在此结界维持期间，周遭数公里内的空间构造将被重塑为由其绝对统治的封闭主场领域。结界内部的物质结构、甚至敌对目标的底层灵子流动效率都将受到其艺术意志的压制与重构，属于通过精神强度强行达成绝对主场支配的异质个体。");

        Servant sSaber4 = createServant(saber, "两仪式", "根源连接者", 5,
                6, 4, 7, 10, 7, 9,
                "源自特定事象记录《空之境界》的特异个体。该个体之特殊性在于其灵体深处直接连通着万物之始源、一切因果与可能性的交汇点——「根源之涡」。在此特定架构下，她以手持日本刀的冷兵器格斗姿态作为剑之骑士现界。其灵基感知系统被赋予了最高序列的肉体透视机能「直死之魔眼」。该机能非属于常规的魔术体系，而是由于其直连根源，从而能够将世间万物在诞生之初便被世界赋予的、不可避免的「死亡概念」进行视觉化捕捉。在她的视轴内，所有有机体、无机物甚至魔术结界、抽象概念的底层因果逻辑，均会呈现为具象化的死线与死点。该个体一旦通过物理刃物对这些死线执行物理层面的轨迹切割，或者以利器贯穿死点，便会跨越一切肉体防御、神秘防护以及不灭因果，直接从世界线的最底层宣告该目标「自古以来就不复存在」的绝对事象湮灭，属于对因果律实施直接裁断的极高危特异英灵。");

        Servant sArcher1 = createServant(archer, "卫宫士郎", "无名守护者", 5,
                5, 5, 7, 8, 5, 7,
                "该个体并非源自泛人类史的古代神话或历史记载，而是由名为卫宫的近代个体，在特定时空坐标下与人类无意识集合体（抑止力·阿赖耶识）签订契约后演化而成的无名守护者。其灵体内部不具备常规英灵所拥有的神话底蕴，亦未继承任何天然的神造兵器或古老圣物。然而，其灵子回路中高纯度铭刻了被其磨炼至极致的特化型高阶复制魔术，其系统被命名为「投影」。该术式允许该个体在目视任何冷兵器的瞬间，对其材料、结构、锻造技术、历史积淀以及附带的神秘度进行全方位的扫描解析与降级重现。在常规战斗中，他多采用双持投影产物「干将·莫邪」执行近身防御，其真正的王牌手段则是展开其内在的独立心象风景——固有结界「无限剑制」。该结界内储存着其一生中所解析记录的数千件名刀与宝具之伪作，在结界展开状态下，这些投影兵器可转化为高密度的自动化飞行弹幕，以饱和式轰炸的方式对目标实施物理与灵力的双重清洗。");

        Servant sArcher2 = createServant(archer, "吉尔伽美什", "英雄王", 5,
                6, 5, 7, 8, 5, 10,
                "人类最古老的苏美尔文明乌鲁克之王。该英灵本身即为人类历史上「神秘度」的最高化身之一，其灵基构成中神性参数占比极高。作为弓之骑士现界时，他并不依赖常规的弓箭执行远程打击，其核心作战模式是通过持有名为「王律之键」的开锁概念，大范围打开连接其庞大虚数空间存储库的金色涟漪，该储存库即为「王之财宝」。根据其概念设定，由于其作为人类最古之王，该财宝内近乎完美地收纳了人类历史演化、派生出的所有文明兵器、魔术原典以及各类圣物的最初原型。在交战状态下，该个体通过空间折叠技术，将无数的高等级宝具原型作为具备极大动能的投掷物执行大范围、无差别的暴雨式覆盖打击。此外，其空间深处还封存着不属于人类技术演化序列的、具备切裂空间轴功能的顶级神造对界兵器「乖离剑」。在真名解放时，该兵器释放的三重空间风暴足以导致周遭时空发生断裂与虚无化，在阿特拉斯院的测算中属于可对空间结构造成不可逆损伤的极高危武装。");

        Servant sArcher3 = createServant(archer, "阿拉什", "波斯传奇弓手", 5,
                6, 7, 8, 5, 4, 10,
                "古代波斯神话中终结了波斯与图兰长年领土战争的传奇弓手。该英灵具备高强度的灵体韧性与无与伦比的远程狙击精度，能够在无光学辅助、无魔力锁定的极端环境下，精准命中公里级别外的目标。其核心术式、亦为其唯一解放的对军级宝具「流星一条」，是一种将自身灵基内部的所有电荷、生命魔力、灵子储备以及精神意志一次性全部压缩并外放的超极限打击手段。在射出此箭的瞬间，其释放的物理与概念冲击力所能达到的能量输出峰值，足以在地理层面上对地表地貌执行永久性的修改（割裂国土疆界）。然而，基于能量守恒与灵体材质承受上限的底层铁律，由于该术式所产生的灵子过载程度远远超越了人类灵体所能维系的极限，该终极打击一旦执行完毕，其自身的灵核亦会伴随着箭矢的命中而同步发生不可逆的自适应彻底崩解，属于杀伤系数与自毁系数均达到峰值的单次战略级术式。");

        Servant sArcher4 = createServant(archer, "织田信长", "第六天魔王", 5,
                6, 5, 7, 6, 6, 8,
                "日本战国时代打破旧有秩序的天下人，自称第六天魔王。该个体的英灵化表现出一种高度特化的概念干涉能力，其灵基之核心机制被定义为「革新」。这一概念对一切带有神代遗留属性、高神秘度底蕴、神性因子以及高等级骑乘技能的古代英灵具有绝对的特化反转克制作用。在战斗状态下，她能够解放对军级宝具「三千世界」，凭空构筑出近乎无休止的火绳枪火器阵列，并以超越时代的现代化战术理念执行不间断的多重齐射。该宝具所激发的弹药并非纯粹的物理铅弹，而是由高浓度革新概念凝聚而成的灵力崩解体。由于其特殊的消灭神秘之法则，目标个体的神性阶位越高、历史累积的神秘度越深，该弹幕所引发的因果连锁破坏力便会呈现几何级数的指数型递增，属于专门针对神代神话体系英灵进行概念层降与降维打击的特殊战略兵器。");

        Servant sLancer1 = createServant(lancer, "库·丘林", "光之子", 5,
                7, 8, 9, 5, 4, 6,
                "凯尔特神话中的半神英雄，系阿尔斯特赤枝骑士团的顶级战士「光之子」。该个体在灵子化后的状态评估中，表现出极高的敏捷参数、常人无法企及的白刃战突进速率以及被命名为「避矢之加护」的远程物理轨迹偏转防御技术。其常驻主武器为由海兽骨骼制成的对人宝具「穿刺死棘之枪」，该兵器在真名解放的瞬间，会强行向世界线投射一条蕴含因果逆转性质的恶性诅咒。该术式会将「目标的心脏已被贯穿」这一既定事实（结果）提前在时空轴上固定，随后引发「魔枪向前刺出」的物理运动（原因）。因此，在术式启动的瞬间，敌方单位即处于因果层面的死寂状态，任何物理方向的闪避、偏转或是常规魔力屏障均无法干涉该既定事实的发展。此技术属于不可逆、不可阻断的低耗能即死突击术式，赋予了该个体在近身白刃战中极高的战术威胁。");

        Servant sLancer2 = createServant(lancer, "迪尔姆德·奥迪那", "费奥纳骑士团首席战士", 5,
                8, 7, 9, 6, 5, 5,
                "凯尔特神话费奥纳骑士团首席战士，人称「光辉之貌」。其灵体外部在诞生时便被施加了微弱的精神概念污染源，表现为右眼下方的黑痣，能对缺乏魔力抗性的女性目标施加强制性的认知修改与精神吸引。在战术执行层面，该个体放弃了传统的盾牌防护，采用双持不同概念倾向的魔枪作为常规白刃战输出路径。其长枪「破魔的红蔷薇」属于对魔力特化专精兵器，在锋刃接触到魔力构筑物的瞬间，可直接破除、切断并消解一切持续性的魔术强化效果、防御性结界以及由魔力供给的拟似装甲。其短枪「必灭的黄蔷薇」则具备常驻性的因果切断属性，被该枪所造成的物理创伤将被施加「不可被魔力治愈、不可被物理愈合」的绝对上限锁死诅咒，导致受创个体的最大生命上限发生永久性滑落。双枪的复合交替应用，使其成为针对魔术强化型与持久战型英灵的特效清除单元。");

        Servant sLancer3 = createServant(lancer, "迦尔纳", "太阳神之子", 5,
                8, 6, 7, 10, 6, 10,
                "印度古代史诗《摩诃婆罗多》所述的太阳神苏利耶之子。在灵基强度的技术评定中，该个体处于英灵殿整体序列的绝对顶点。其灵体表面常驻着与血肉骨骼融为一体的对界级概念防御武装「日轮呀，顺从死亡」。此黄金之甲具备极高纯度的太阳辐射能，能将来自外界的一切概念干涉、魔术轰炸以及物理物理打击强制削减至原本强度的十分之一。该个体在常规状态下展现出极度低调、沉默的逻辑运行模式，一旦战局进入极限状态，他可通过永久性剥离自身黄金甲作为代价，解放其持有的弑神之枪「日轮呀，具现灭绝」。该兵器在真名解放后，其核心灵子反应堆将以100%功率运转，释放出媲美恒星内部演化阶段的超高热量与光辐射。其能量波及范围内的物质形态与空间结构均会被瞬间重构、气化，属于对神、对界层面的毁灭性重武器。");

        Servant sLancer4 = createServant(lancer, "斯卡哈", "影之国女王", 5,
                8, 7, 9, 8, 5, 9,
                "支配处于泛人类史世界轴外延的异界——「影之国」的女王。由于该个体在久远的神代便已将其武艺与精神锻炼至跨越人类极限的领域，导致其存在本身已被纳入世界常理之外，从而获得了超越时间轴停滞与灵子退化的绝对不死性。基于这一特征，她无法通过常规死后的灵魂提炼进入英灵殿，而是以肉身横跨时空的方式作为枪之骑士现界。她完美掌握着神代原初卢恩文字的系统应用，能够通过在虚空中铭刻古老字符来执行包括空间移动、概念强化、绝对冻结等一系列高阶术式。作为不列颠神话中众多英雄的枪术指导者，其战术动作与对魔枪的运用已达到物理法则所能容纳的最高峰，配合其对人对军复合宝具「贯穿死翔之枪」，能在战场上构筑出兼具空间封锁、因果锁定与大范围物理破坏的绝对压制网，属于具备高级魔境智慧的非规格个体。");

        Servant sRider1 = createServant(rider, "美杜莎", "戈耳工", 5,
                5, 5, 10, 6, 5, 8,
                "希腊神话中著名的戈耳工三姐妹之幼妹。作为骑兵职阶现界时，其灵基状态保持在未被完全怪物化、异化前的理智人形阶段。该个体表现出极端突出的敏捷与空间穿梭参数。其常驻战斗手段为使用带有重锚的链刃进行多维度的轨迹袭杀。在骑乘资格评定中，她具备最高级别的「骑乘」权限，能通过鲜血构筑的魔法阵召唤神代幻想种「纯白飞马（天马）」，并在冲刺阶段将其速度推进至超越音速的超高机动状态，形成带有极高动能的物理突防。此外，该个体的眼部常驻封印着被归类为最高阶位特权的「石化魔眼（库柏勒）」。在平时，她通过特定眼罩阻断辐射，一旦解开封印，凡直视其双眸且魔力抗性低于特定阈值的目标个体，其体内的灵子循环将发生瞬间停滞，肉体组成成分将被直接改写为不具备生命特征的石质结构。");

        Servant sRider2 = createServant(rider, "伊斯坎达尔", "征服王", 5,
                9, 8, 6, 6, 7, 9,
                "马其顿帝国的亚历山大大帝，史称征服王。其灵体外形表现为具备极高物理防御与质量参数的宏伟人型结构。在遭遇战中，他通常驾驭由神牛拉扯、缠绕着雷电灵子的战车「神威车轮」对敌方阵线实施大动能的践踏与切割。该英灵最具战略价值的王牌宝具，被测定为EX级的独立心象景观具现化结界「王之军势」。此术式在真名解放时，会将结界内的所有人强行拖入一片荒芜的沙漠战场。该结界的核心运行法则，是将生前与该个体建立过深厚精神链接、共同执行过远征的数万名将士，以独立的、具备各自武装的英灵姿态临时召唤至结界内。这群英灵大军虽然缺乏各自的独立宝具，但集合起来的物理冲锋量能、灵力波动以及集体压制力，足以在一瞬间吞噬任何常规数量级的敌对目标，系军团级联合压制的极限手段。");

        Servant sRider3 = createServant(rider, "阿斯托尔福", "查理曼十二勇士", 5,
                4, 5, 9, 4, 8, 5,
                "查理曼十二勇士之一。在灵基结构扫描中，该个体的精神逻辑模块长期处于被称为「理性蒸发」的无序、混乱运行状态。这一精神特异性导致常规的、基于逻辑链条或精神干扰的魔术术式无法对其行为轨迹做出有效的预测、解析或反制。虽然从基础物理参数来看，该个体的刚性输出与防御能力在英灵序列中仅处于中下水平，但其灵基内奇迹般地绑定并继承了多件具备破坏物理规则性质的次级概念宝具。其中包括：能够强行剥夺目标站立平衡、使其发生概念性强制倒地的「唤起恐慌之魔笛」；能够直接切断并免疫A级以下魔术现象的「宣告破却之宣言」；以及可跨越三维空间限制、执行短距离多维空间跳跃的幻兽坐骑「骏鹰」。这些赖皮道具的复合使用，使其具备了极高的战术容错率与不可控性。");

        Servant sRider4 = createServant(rider, "阿尔托莉雅·潘德拉贡〔Alter〕", "暗黑骑士王", 5,
                9, 8, 6, 7, 5, 9,
                "亚瑟王在特定事象分支中，其灵子结构受到高浓度怨念、诅咒粒子及圣杯黑泥严重侵蚀反转后的冷酷姿态。在当前特定坐标的记录中，该英灵对自身的武装架构进行了现代化改造，以现代高马力内燃机机械载具（漆黑摩托车）作为其执行骑乘指令的物理媒介。虽然其泛人类史原本的高洁精神被重组、修正为冷酷、高效且不带有任何同理心的绝对铁腕统治逻辑，但其体内的核心灵力源——即作为赤龙因子的魔力炉心，其整体输出功率并未出现任何滑落，反而因黑泥的加持表现出更具侵略性的短时爆发力。她将誓约胜利之剑转换为漆黑形态，在高速重型载具的机动配合下，将庞大的魔力以物理冲击与黑雷辐射的形式倾泻于战场，属于将高速机动性与大负荷破坏力进行高强度融合的反转特化从者。");

        Servant sCaster1 = createServant(caster, "美狄亚", "神代魔女", 5,
                3, 3, 5, 10, 5, 6,
                "神代科尔基斯王国的公主，在神秘学分类中被定义为神代顶级魔女。该个体本身不具备任何物理层面的白刃战适应性，但其体内保留了现代魔术师完全无法企及的、诞生于神代环境的「高速神言」高阶音频序列。该序列允许其直接跨越现代魔术所必需的魔力回路构筑、繁琐术式吟唱以及地理阵地限制，仅凭单音节的发声即可在瞬间凝聚并释放出饱和度极高、威力等同于小型对军轰炸的高浓度灵子光雨。其随身配有的特化型短刀宝具「万符必将破戒」，完全不具备任何物理层面的杀伤属性，但其刃部被赋予了绝对的因果终止概念。只要该短刀接触到由魔力维系的契约、召唤物、强化现象或是结界，便能强行对该神秘现象的底层代码执行强制终止、剥离或改写。甚至能够切断其他英灵与御主之间的主从契约，并将其控制权转移给自身。");

        Servant sCaster2 = createServant(caster, "诸葛孔明〔埃尔梅罗二世〕", "拟似从者", 5,
                4, 5, 5, 9, 8, 7,
                "将中国古代三国时期的顶级战术家诸葛孔明的历史灵基，与现代时钟塔魔术师埃尔梅罗二世的肉体进行高融合度融合后诞生的「拟似从者」。由于孔明自身的理性逻辑判定圣杯战争具备过高的无谓内耗，故在现界后选择将主导权交由现代魔术师的意识维持。因此其外在行为、疲惫性格主要由二世体现，但其深层的灵子运算、战术推演以及局势观测完全由孔明的顶级智谋回路提供算力支撑。在遭遇战或攻坚战中，该个体不参与直接的物理对抗，而是通过精准观测战场上的神秘流动，解放诸如「出师表」、「石兵八阵」等带有绝对秩序的概念术式。该术式能够将敌方的魔力流转执行卡顿、延时与弱化处理，同时为己方作战单元提供涵盖攻击修正、防御强化以及灵力快速充能的绝对参数增幅，属于功能性极强的顶级军师级辅助中枢。");

        Servant sCaster3 = createServant(caster, "梅林", "花之魔术师", 5,
                3, 3, 4, 10, 7, 10,
                "不列颠传说中引导并辅佐亚瑟王登基的伟大宫廷魔术师。该个体在生理机制上属于人类与梦魔的混血半异类个体，从而获得了在精神世界与物质世界自由穿梭的特殊权限。其灵基内常驻着被称为最高阶位魔术师特权的「千里眼」，能够实时跨越空间障碍，观测当前时间轴上世界线的所有现状。基于其冷酷的梦魔底层逻辑，该个体对人类历史的演进缺乏实质性的同理心，但在表现层维持着轻浮、开朗的伪装。在实战演练中，由于其认为冗长的魔术咒语在高速吟唱时极易发生咬舌等物理失误，他更倾向于直接抽取圣剑，通过物理锋刃对目标进行高效的肉体切割。其自身持有高阶幻术具现化宝具，释放时会在大范围内重组空间的物质呈现，引发大规模带有生命力吸取与精神致幻效果的拟似花海，属于无法以常理测度的非规格式魔术个体。");

        Servant sCaster4 = createServant(caster, "汉斯·克里斯蒂安·安徒生", "童话作家", 5,
                2, 2, 3, 7, 6, 5,
                "十九世纪著名的丹麦童话作家，创作者英灵之典型。该个体由于自身历史事象的局限，完全不具备任何物理层面的战斗参数，其肌肉强度、敏捷度以及灵体耐久力均处于英灵殿的最低阈值。然而，其灵基之核心运行机制被定义为「观察者与重塑者」。在切入战场后，他通过对特定个体（己方或敌方）的人生轨迹、性格缺陷、战斗逻辑进行深度的精神观测与文本记录。其持有的核心对人宝具「为你撰写的故事」，能够在文本撰写完成的瞬间，将处于虚构维度的、完美的结局或进化概念，作为绝对的现实法则强加于目标对象身上。只要对方的意志能够承受该文本的概念重量，便能跨越原有的灵基限制，获得近乎作弊般的肉体修复、参数跃升以及临时的概念级能力附加，属于纯粹以文学叙事对现实事象实施强行修正的规则干涉型个体。");

        Servant sAssassin = createServant(assassin, "山中老人", "初代哈桑", 5,
                9, 9, 8, 7, 5, 9,
                "暗杀教团「阿萨辛」的首任领袖，被冠以「山中老人」之名的初代哈桑。该个体在灵子世界线中并非普通的英灵，而是曾短暂涉足冠位（Grand）资格的特殊存在。其灵体常驻着高等级的「气息遮断」技术，能够将自身的灵力波动、物理存在感、甚至因果杀意完全稀释至虚无状态，使其化为不可视、不可感、不可预测的幽灵。其手中的巨剑不具备常规暗杀者的轻巧特征，但其每一次物理挥砍均被附加了世界底层的「死亡概念」。即便仅对目标造成微弱的物理擦伤，亦有极高概率直接引发对方灵核的连带式崩溃。其最终宝具「死告天使」，更是能在目标完全不具备防备的瞬间，作为世界的抑止力宣告死期的降临。在阿特拉斯院的威胁评定中，该英灵不具备任何情感波动的妥协可能性，其存在本身即等同于死神概念的物理具现。");

        Servant sBerserker = createServant(berserker, "赫拉克勒斯", "希腊大英雄", 5,
                10, 10, 8, 5, 5, 8,
                "希腊神话中最伟大的半神英雄。该个体在召唤阶段由于被强行注入了高浓度的狂化咒文，其原本具备的高级理智、言语沟通能力以及精妙的枪剑武艺大部分被封印，转化为全基础物理参数（力量、耐久）的大幅度刚性暴涨。即使处于丧失理智、仅凭本能运转的狂暴状态，他依然能凭借被肌肉记忆刻盘的恐怖战技对任何敌对单位实施压倒性的力量碾压。其持有的常驻概念宝具「十二试炼」，将其生前完成的十二项伟业升华为绝对的肉体复活法则。该术式不仅赋予了该灵体额外十一次的灵核自修复与重组机会，更使其肉体具备了极高强度的常驻概念免疫壁垒——凡不具备B级以上神秘度或攻击强度的手段，均无法对其肉体造成实质性损伤。且在被某种手段击杀一次并复活后，其灵体会自动针对该攻击手段产生极高的概念抗性，属于纯粹的防御与力量物理要塞。");
        log.info("Created 20 servants with stats");

        createProfile(ritsuka, null, "人理拯救的Master，勇敢坚韧的少年/少女。", sSaber1, "A+");
        createProfile(mash, null, "Shielder的适格者，亚从者。", sArcher1, "A++");
        createProfile(davinci, null, "万能的天才，文艺复兴时期的人物。", sCaster3, "EX");
        createProfile(romani, null, "迦勒底医疗部门负责人。", sCaster4, "B");
        log.info("Created 4 user profiles");

        // ===== Seed Coffins (48 units) =====
        seedCoffins();
        log.info("Created 48 coffins");

        // ===== Seed Resources =====
        seedResources();
        log.info("Created resources");

        // ===== Seed System Metrics =====
        seedSystemMetrics();
        log.info("Created system metrics");

        // ===== Seed Alerts =====
        seedAlerts();
        log.info("Created alerts");

        // ===== Seed Event Logs =====
        seedEventLogs();
        log.info("Created event logs");

        log.info("Database seeding completed!");
    }

    private User createUser(String username, String password, String displayName) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDisplayName(displayName);
        return userRepository.save(user);
    }

    private ServantClass createClass(String className, String icon) {
        ServantClass sc = new ServantClass();
        sc.setClassName(className);
        sc.setIcon(icon);
        return classRepository.save(sc);
    }

    private Servant createServant(ServantClass servantClass, String name, String title,
                                int rarity, int strength, int endurance, int agility,
                                int magic, int luck, int np, String description) {
        Servant s = new Servant();
        s.setServantClass(servantClass);
        s.setName(name);
        s.setTitle(title);
        s.setRarity(rarity);
        s.setStrength(strength);
        s.setEndurance(endurance);
        s.setAgility(agility);
        s.setMagic(magic);
        s.setLuck(luck);
        s.setNp(np);
        s.setDescription(description);
        return servantRepository.save(s);
    }

    private void createProfile(User user, String avatarUrl, String bio,
                                Servant favoriteServant, String magicQuality) {
        UserProfile p = new UserProfile();
        p.setUser(user);
        p.setAvatarUrl(avatarUrl);
        p.setBio(bio);
        p.setFavoriteServant(favoriteServant);
        p.setMagicCircuitQuality(magicQuality);
        profileRepository.save(p);
    }

    private void seedCoffins() {
        // A-team occupants (frozen after prologue explosion)
        String[][] aTeam = {
            {"玛修·基列莱特",   "Shielder"},
            {"达·芬奇",         "Caster"},
            {"罗马尼·阿基曼",   "Caster"},
            {"基尔什塔利亚·沃戴姆", "Caster"},
            {"卡多克·泽姆露普斯",   "Caster"},
            {"奥菲莉娅·法姆索罗尼", "Caster"},
            {"斯堪的纳维亚·佩佩隆奇诺", "Caster"},
            {"贝里尔·伽特",     "Caster"},
            {"戴比特·泽姆·沃伊德", "Caster"},
            {"芥雏子",           "Lancer"},
        };

        String[] aTeamLore = {
            "亚从者·Shielder适格者。在序章爆炸中主动展开宝具「假想宝具·拟似展开/人理之础」保护御主，灵核受损严重，当前处于深度冻结休眠状态。其体内寄宿着英灵加拉哈德的部分灵基。",
            "万能的天才，文艺复兴时期的巨匠。序章时为保护管制室核心数据而延迟撤退，受到爆炸冲击波直接伤害。以Caster职阶被冻结保存，灵基稳定性中等。",
            "迦勒底医疗部门前负责人，所罗门王遗留的肉身躯壳。序章后主动选择进入冷冻休眠以维持迦勒底医疗系统的最低限度运转。其真实身份尚属机密。",
            "A队队长，天体科首席魔术师。序章时正在执行拟似灵子转移试验，被爆炸引发的灵子乱流严重侵蚀。冻结状态下灵基仍持续发出异常波动，需持续监控。",
            "A队成员，来自俄罗斯的魔术师。爆炸发生时处于舱室边缘位置，右半身受碎片冲击造成中度冻伤。在冻结前留下了一组意义不明的观测数据。",
            "A队成员，北欧魔术名家出身。序章后主动提议冻结自身以让出资源给其他幸存者。其持有的「视腐」魔眼在冻结状态下仍保持微弱的活性化。",
            "A队成员，南美系咒术师。以其独特的精神构造成功抵御了爆炸后灵子污染的部分影响，是A队中灵基完整性最高的冻结个体。",
            "A队成员，原时钟塔降灵科主席。在爆炸前察觉到异常并启动了舱室的独立防护系统，但因此暴露于第一波冲击中，损伤度较高。",
            "A队成员，隐秘的考古学专家。在爆炸后的混乱中协助完成了受损框体的紧急冷封，之后才进入自己的框体。其遗留的外界通讯设备已被封存。",
            "A队成员，自称时钟塔植物科出身的灵子转移适格者，隐匿者No.7。其真实身份为公元前210年起便存在的真祖级存在，冻结状态下仍维持着异常的细胞活性。",
        };

        java.time.LocalDate today = java.time.LocalDate.now();

        for (int i = 0; i < 48; i++) {
            Coffin c = new Coffin();
            c.setDesignation(String.format("COFFIN-%02d", i));
            c.setIsProtagonist(i == 0);

            if (i == 0) {
                c.setStatus("ACTIVE");
                c.setOccupantName("藤丸立香");
                c.setOccupantClass("Master");
                c.setDamageLevel(5);
                c.setTemperature(4.5);
                c.setHumidity(38.0);
                c.setCryoStability(92);
                c.setCoolantPressure(45.0);
                c.setLastMaintenance(today.minusDays(3));
                c.setMaintenanceDue(today.plusDays(27));
            } else if (i >= 1 && i <= 10) {
                int idx = i - 1;
                c.setStatus("FROZEN");
                c.setOccupantName(aTeam[idx][0]);
                c.setOccupantClass(aTeam[idx][1]);
                c.setDamageLevel(10 + idx * 5);
                c.setTemperature(-180.0 - (idx * 1.5));
                c.setHumidity(0.5 + (idx * 0.1));
                c.setCryoStability(85 - idx * 3);
                c.setCoolantPressure(50.0 - idx * 2.5);
                c.setLastMaintenance(today.minusDays(15 + idx * 3));
                c.setMaintenanceDue(today.plusDays(20 + idx * 5));
                c.setLoreText(aTeamLore[idx]);
            } else if (i >= 11 && i <= 23) {
                c.setStatus("DAMAGED");
                c.setDamageLevel(40 + (i - 11) * 4);
                c.setTemperature(-80.0 + (i - 11) * 5.0);
                c.setHumidity(65.0 + (i - 11) * 1.5);
                c.setCryoStability(45 - (i - 11) * 3);
                c.setCoolantPressure(30.0 - (i - 11) * 1.8);
                c.setLastMaintenance(today.minusDays(60 + (i - 11) * 5));
                c.setMaintenanceDue(today.minusDays(5 + (i - 11) * 2));
                c.setMaintenanceNotes("Cryo seal compromised — coolant leak detected");
            } else if (i >= 24 && i <= 30) {
                c.setStatus("DESTROYED");
                c.setDamageLevel(100);
                c.setTemperature(22.0);
                c.setHumidity(96.0);
                c.setCryoStability(0);
                c.setCoolantPressure(0.0);
                c.setLastMaintenance(today.minusDays(90));
                c.setMaintenanceNotes("Frame irreparable — biological remains extracted");
            } else {
                c.setStatus("ACTIVE");
                c.setDamageLevel(0);
                c.setTemperature(5.0 + (i * 0.1));
                c.setHumidity(40.0);
                c.setCryoStability(95);
                c.setCoolantPressure(48.0);
                c.setLastMaintenance(today.minusDays(7));
                c.setMaintenanceDue(today.plusDays(23));
            }

            coffinRepository.save(c);
        }
    }

    private void seedSystemMetrics() {
        String[] metricKeys = {"cpu_load", "temperature", "memory_usage", "disk_usage"};
        java.time.LocalDateTime base = java.time.LocalDateTime.now().minusMinutes(60);

        for (String key : metricKeys) {
            double baseVal;
            switch (key) {
                case "cpu_load": baseVal = 45.0; break;
                case "temperature": baseVal = 38.0; break;
                case "memory_usage": baseVal = 55.0; break;
                default: baseVal = 30.0;
            }

            for (int i = 0; i < 40; i++) {
                SystemMetrics m = new SystemMetrics();
                m.setMetricKey(key);
                m.setMetricValue(baseVal + (Math.random() - 0.5) * 20);
                m.setRecordedAt(base.plusMinutes(i * 2));
                systemMetricsRepository.save(m);
            }
        }
    }

    private void seedAlerts() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        Alert a1 = new Alert();
        a1.setSeverity("WARNING");
        a1.setSource("SHEBA");
        a1.setMessage("Lens aberration detected in quadrant 3 — scheduled recalibration recommended");
        a1.setAcknowledged(false);
        a1.setCreatedAt(now.minusMinutes(5));
        alertRepository.save(a1);

        Alert a2 = new Alert();
        a2.setSeverity("INFO");
        a2.setSource("COOLING");
        a2.setMessage("Coolant-α pump efficiency dropped 3% over 24h — routine maintenance advised");
        a2.setAcknowledged(false);
        a2.setCreatedAt(now.minusMinutes(20));
        alertRepository.save(a2);

        Alert a3 = new Alert();
        a3.setSeverity("CRITICAL");
        a3.setSource("COFFIN");
        a3.setMessage("COFFIN-17 cryo stability at 18% — immediate inspection required");
        a3.setAcknowledged(true);
        a3.setCreatedAt(now.minusHours(1));
        alertRepository.save(a3);

        Alert a4 = new Alert();
        a4.setSeverity("WARNING");
        a4.setSource("TRISMEGISTUS");
        a4.setMessage("Trismegistus-γ compute load exceeding 85% threshold — consider load balancing");
        a4.setAcknowledged(false);
        a4.setCreatedAt(now.minusMinutes(45));
        alertRepository.save(a4);
    }

    private void seedEventLogs() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String[][] events = {
            {"SYSTEM", "SHEBA lens calibration cycle started"},
            {"COOLING", "Coolant-α flow rate normalized to 72%"},
            {"NETWORK", "Data link to Chaldea mainframe stable — latency 12ms"},
            {"COMPUTE", "Trismegistus-α batch job #8741 completed"},
            {"SYSTEM", "System health check PASSED — all subsystems nominal"},
            {"COOLING", "CRYO-LOOP temperature stable at -180.2°C"},
            {"NETWORK", "Relay 04 handshake confirmed — packet loss 0.02%"},
            {"COMPUTE", "Sheba FE parallel processing engine active"},
            {"SYSTEM", "Daily data compaction complete — 2.3 GB recovered"},
            {"COOLING", "Coolant-β secondary pump engaged — pressure 42 PSI"},
            {"NETWORK", "External interface handshake with Wandering Sea completed"},
            {"COMPUTE", "AI prediction model inference completed in 3.2s"},
            {"SYSTEM", "User auth audit log rotated — 1,247 entries archived"},
            {"COOLING", "Heat exchanger efficiency at 94.2%"},
            {"NETWORK", "Sheba uplink signal strength -63 dBm"},
            {"COMPUTE", "Trismegistus-β memory page reconciliation done"},
            {"SYSTEM", "Backup relay to Wandering Sea established"},
            {"COOLING", "Coolant-γ temperature spike suppressed — now 34°C"},
            {"NETWORK", "Internal mesh network reconfiguration complete"},
            {"COMPUTE", "GPU cluster clock rate stabilized at 1.8 GHz"},
            {"SYSTEM", "Coffin monitoring daemon restarted after memory cleanup"},
            {"COOLING", "CRYO-LOOP coolant pressure adjusted to 48 PSI"},
            {"NETWORK", "Firewall rule set updated — 12 new signatures"},
            {"COMPUTE", "Trismegistus-γ load balancing initiated"},
            {"SYSTEM", "SHEBA observation log synced to central archive"},
            {"COOLING", "Coolant-α pump vibration within normal range"},
            {"NETWORK", "External probe data relay 100% received"},
            {"COMPUTE", "Predictive maintenance model training completed"},
            {"SYSTEM", "Frame integrity check for all 48 coffins PASSED"},
            {"COOLING", "Coolant system self-diagnostic — all loops nominal"},
        };

        for (int i = 0; i < events.length; i++) {
            EventLog e = new EventLog();
            e.setEventType(events[i][0]);
            e.setMessage(events[i][1]);
            e.setCreatedAt(now.minusMinutes((events.length - i) * 3));
            eventLogRepository.save(e);
        }
    }

    private void seedResources() {
        createResource("圣晶石",     "SAINT_QUARTZ", 300L,   "💎");
        createResource("魔力棱镜",   "MANA_PRISM",   4500L,  "🔷");
        createResource("量子（QP）", "QP",            5000000L, "🪙");
        createResource("稀有棱镜",   "RARE_PRISM",   50L,    "💠");
        createResource("圣晶石碎片", "SQ_FRAGMENT",  90L,    "💠");
    }

    private void createResource(String name, String type, Long quantity, String icon) {
        Resource r = new Resource();
        r.setName(name);
        r.setType(type);
        r.setQuantity(quantity);
        r.setIcon(icon);
        resourceRepository.save(r);
    }

    private void seedPersonnel() {
        Object[][] staff = {
            // {name, title, department, role, status, bio, loreText, sortOrder}

            // --- 御主 (Master) ---
            {"藤丸立香", "人理存续保障机构·最后之御主", "MASTER", "御主", "ACTIVE",
                "迦勒底第48号御主适格者。在管制室爆炸后成为唯一具备灵子转移适性的御主，肩负起矫正七大特异点、剪定七大异闻带的人理拯救使命。",
                "于2015年作为第48号御主候补被招募进入迦勒底。在序章的管制室爆炸中，因玛修·基列莱特以宝具舍身相护而幸存。此后作为迦勒底唯一可执行灵子转移的御主，跨越7个特异点、深入虚数之海、介入7个异闻带的世界切除作战。其乘坐Shadow Border潜入虚数空间的记录，被阿特拉斯院评价为「人类史上最长的冒险旅程」。经历所罗门决战、异星神降灵事件后，已成为多个世界线中被称为「人理之光的最后希望」的传奇存在。",
                1},

            // --- 主要从者 (Main Servants) ---
            {"玛修·基列莱特", "Shielder·亚从者", "COMBAT", "防御作战", "ACTIVE",
                "迦勒底制造的人工英灵·亚从者。以英灵加拉哈德的灵基为基础，与玛修融合后诞生的全新存在。持有对人理防御宝具「假想宝具·拟似展开/人理之础」。",
                "迦勒底在「所罗门计划」中以英灵加拉哈德的灵基为蓝本，结合胚胎工程技术培育出的亚从者实验体。在序章的爆炸危机中，为了保护御主而成功觉醒宝具。此后作为御主唯一的常驻从者，参与全部特异点与异闻带的作战。其宝具「假想宝具·拟似展开/人理之础」被评级为在防御层面足以匹敌甚至超越Berserker赫拉克勒斯「十二试炼」的概念级守护壁垒。在第六异闻带，其灵基曾因触及圆桌领域而引发特异反应，一度显现出Gallatin的完整形态。",
                2},

            {"达·芬奇", "万能的天才·文艺复兴时期的巨匠", "RESEARCH", "技术顾问", "ACTIVE",
                "以李奥纳多·达·芬奇的灵基现界的从者。迦勒底的技术中枢，负责灵子转移系统维护及各种魔术兵装开发。",
                "以Caster职阶常驻迦勒底的技术天才。在序章爆炸中为了抢救管制室核心数据而延迟撤退，受到爆炸冲击波直接伤害。此后以自动人偶形态继续活动。其负责开发的灵子转移系统是迦勒底最核心的技术体系，而特里斯墨吉斯托斯系统的拟似灵子计算机也是在她的主导下与阿特拉斯院技术合作的产物。在玛修·基列莱特的灵基调整、Shadow Border的后勤改造等方面均有重大贡献。",
                3},

            {"罗马尼·阿基曼", "迦勒底医疗部门负责人", "MEDICAL", "医疗主管", "ACTIVE",
                "迦勒底医疗部门最高负责人。虽然表面上是个废柴医生，但其真实身份是……",
                "迦勒底医疗部门负责人，表面上举止懒散、嗜好甜食，实则拥有极其精深的魔术知识和临床医疗技术。在玛修·基列莱特的灵基稳定、御主在灵子转移后的身体调整等方面起着无可替代的作用。其真实身份为所罗门王遗留于现世的肉身躯壳——在最终决战时所罗门王以第一宝具「诀别之时已至·以此舍弃世界」将自身存在从世界上抹消后，其留下的躯壳以罗马尼·阿基曼的身份在迦勒底继续行医。",
                4},

            // --- A-Team (隐匿者 / Crypters) ---
            {"基尔什塔利亚·沃戴姆", "A队队长·天体科首席", "A-TEAM", "隐匿者No.1", "FROZEN",
                "天体科首席魔术师，A组队长。在序章爆炸中受重伤，长期处于冷冻休眠状态。其背景与异星神之间存在未解的因果联系。",
                "A队的绝对核心，时钟塔天体科历史上最年轻的君主（Lord）候选。其持有的魔术回路的质与量在迦勒底所有御主中排名第一。在序章执行拟似灵子转移试验时被爆炸引发的灵子乱流严重侵蚀，灵基在冻结状态下仍持续发出异常波动。佩佩评价其为「在现代也堪称异才的怪物」。其与异星神的关系、以及他在序章前执行的秘密任务，至今仍是迦勒底最高级别的机密档案。",
                11},

            {"卡多克·泽姆露普斯", "A队成员·流浪的魔术师", "A-TEAM", "隐匿者No.2", "FROZEN",
                "出身于没落魔术家系的A队成员。序章爆炸时处于舱室边缘位置，右半身受碎片冲击造成中度冻伤。",
                "来自俄罗斯的末代魔术师。其家族的魔术回路在代代传承中逐渐衰退，降格为仅能勉强维持「魔术师」下限的层次。然而，卡多克本人的魔术适性与战术策应能力在全A队中仍属中上水平。在冻结前留下了一组意义不明的观测数据，疑似记录到了序章爆炸前后的某种异常波形。性格上略带自卑与自嘲，但在生死战场上具备冷静的判断力。",
                12},

            {"奥菲莉娅·法姆索罗尼", "A队成员·北欧魔眼使", "A-TEAM", "隐匿者No.3", "FROZEN",
                "北欧系魔术名门出身。序章后在爆炸混乱中主动提议冻结自身，以让出资源给其他幸存者。",
                "继承了北欧魔术传统的精英。其左眼装备有被列为高度危险品的「视腐」魔眼——一种能够直视并加速物体腐朽崩坏的因果干涉型魔术器官。在冻结状态下，该魔眼仍保持微弱的活性化迹象，需持续监控。性格上有着强烈的责任感，序章爆炸后的第一时间考虑的并非自身安全，而是如何最大化保留迦勒底的有生力量。",
                13},

            {"斯堪的纳维亚·佩佩隆奇诺", "A队成员·南美咒术师", "A-TEAM", "隐匿者No.4", "FROZEN",
                "来自南美大陆的独特咒术使。以其极其特异的魔术构造在A队中独树一帜。",
                "出身于南美印加文明系的咒术师家族。其精神构造与常规魔术师有着本质性的差异，这使其在序章爆炸后成功抵御了灵子污染的绝大部分影响，是A队中灵基完整性最高的冻结个体。性格上有着超然物外、神神叨叨的一面，但偶尔会说出令全场沉默的尖锐真相。与戴比特之间存在某种超乎同事关系的深厚信任纽带。",
                14},

            {"贝里尔·伽特", "A队成员·降灵科暗杀者", "A-TEAM", "隐匿者No.5", "FROZEN",
                "原时钟塔降灵科主席。在爆炸前已察觉到异常，但因此暴露于第一波冲击中，损伤度较高。",
                "时钟塔降灵科历史上最年轻的部门主席。擅长召唤并操控高位恶灵，其战斗风格极度残忍高效。在序章爆炸前数秒，他凭借降灵科的警觉性优先启动了舱室独立防护系统，但也因这一举动而在时间上距离爆炸中心最近，导致灵基损伤度在A队中仅次于队长。在冻结舱内的表情据监控记录显示为扭曲的笑容，其内心真实想法完全不可测。",
                15},

            {"戴比特·泽姆·沃伊德", "A队成员·考古学神秘专家", "A-TEAM", "隐匿者No.6", "FROZEN",
                "隐秘的考古学专家。爆炸后协助完成了受损框体的紧急冷封后，最后一个进入冻结舱。",
                "在世界各地古代遗迹发掘方面有着惊人实绩的考古学专家。他在迦勒底的正式记录极为简略，疑似其背后存在着时钟塔也无法完全掌握的特务机构背景。在序章爆炸后的混乱中，他并未优先考虑自身的伤势，而是协助完成了多台受损框体的紧急冷封工作，之后才最后一个进入自己的舱体。其所遗留的外界通讯设备已被迦勒底方面封存保管。",
                16},

            {"芥雏子", "A队成员·植物科潜入者", "A-TEAM", "隐匿者No.7", "FROZEN",
                "医疗部特招的灵子转移天才，自称时钟塔植物科出身。不爱交际，历史测试成绩极其优秀，尤其精通中国古代史。但在常规体检中，她的多项生理指标呈现出极其诡异的静止状态。",
                "由医疗技术部门特招的灵子转移适格者，自称时钟塔植物科出身。不爱交际，除了在测试中展现出异常丰富的历史知识（尤其是中国古代史部分）之外，她的各项表现都维持着不高不低的平均水平。然而在常规体检中，其多项生理指标——如血液活性与代谢周期——呈现出极度异常的静止状态，医疗部门至今未能解析其肉体的深层结构。目前处于冷冻休眠状态，冻结舱的监控数据显示其细胞活性在低温下仍维持着不可思议的稳定水平。",
                17},

            // --- 管理层 (Command) ---
            {"戈尔德鲁夫·穆吉克", "迦勒底新任所长", "COMMAND", "所长", "ACTIVE",
                "在奥尔加玛丽所长失踪后接任迦勒底管理职责的新任所长。穆吉克财团的当主。",
                "穆吉克财团的当主，在奥尔加玛丽·亚斯密雷特·阿尼姆斯菲亚失踪后，被魔术协会指命为迦勒底新任所长。最初因为不够了解灵子转移的实际情况而显得有些脱节，但随着时间的推移，逐渐展现出作为管理者的责任心。虽然时常抱怨迦勒底的运营成本过高，但在关键时刻从未削减过与御主任务相关的任何预算。对Shadow Border的整备与补给管理有着出人意料的精细手腕。",
                5},

            {"奥尔加玛丽·亚斯密雷特·阿尼姆斯菲亚", "前迦勒底所长", "COMMAND", "前所长", "MIA",
                "阿尼姆斯菲亚家的当主，迦勒底前所长。在序章的管制室爆炸中失踪，目前行踪不明。",
                "时钟塔天体科君主的女儿，在父亲马里斯比利·阿尼姆斯菲亚去世后继任迦勒底所长。尽管年仅20岁便承担起人理保障机构的最高管理职责，其卓越的魔术知识与领导能力令人信服。在序章的管制室爆炸中，她为了确认特里斯墨吉斯托斯的异常数据而留在管制室，随后在爆炸中失踪。关于其下落存在多种推测，包括「在虚数空间中漂流」以及「被某特定势力回收」等，但目前均未得到证实。",
                6},

            // --- 技术部门 (Engineering) ---
            {"特里斯墨吉斯托斯", "阿特拉斯院·拟似灵子计算机", "ENGINEERING", "中央系统", "ACTIVE",
                "由阿特拉斯院为迦勒底特别定制的拟似灵子计算系统，是整个观测机构的核心运算中枢。",
                "阿特拉斯院技术结晶的巅峰之作。该系统的正式名称为「特里斯墨吉斯托斯II式·灵子演算装置」。其核心技术基于阿特拉斯院的「灵子化石」理论——即将人类灵子转化为可在计算机内进行高速演算的信息态。该系统的处理能力足以在短时间内完成对某个特异点内数十亿人份的灵子档案检索与事象推演。其日常运维由迦勒底技术团队与达·芬奇共同负责。据非正式渠道信息，该系统本体中可能保存着前任所长奥尔加玛丽的灵子备份。",
                30},

            {"谢巴", "近未来观测透镜·SHEBA系统", "ENGINEERING", "观测系统", "ACTIVE",
                "迦勒底引以为傲的近未来观测透镜系统，用于实时监测人理基值波动。其名取自示巴女王。",
                "迦勒底所持有的最高精度的近未来观测装置。其全称为「近未来观测透镜·示巴（SHEBA）」，以旧约圣经中谒见所罗门王的示巴女王命名。该透镜并非光学望远镜，而是一种能够捕捉时空结构中「人理」维度的灵子波动，并以视觉化形式呈现在操作员面前的超高次元观测设备。通过持续监测A.D.1至A.D.2015范围内的人理基值稳定度，该系统能够提前数日至数周发现可能降格为特异点的灵子异常波动。在First Order前，正是SHEBA捕捉到了Fuyuki的不明热源。",
                31},

            // --- 医疗部门 (Medical) ---
            {"达芬奇", "医疗技术助理·自动人偶", "MEDICAL", "医疗技术", "ACTIVE",
                "达·芬奇制作的自律型医疗支援人偶。在达·芬奇本人以从者身份活动时，负责辅助医疗运营。",
                "由达·芬奇亲手制作的自动人偶型医疗辅助终端。搭载了基础的魔术诊疗分析模块与多种灵药自动调配功能。在从者达·芬奇因外界任务离开迦勒底时，此终端负责接管日常的体检与数据监控工作。造型上刻意模仿了达·芬奇本人的外貌，据称是为了在视觉上给予前来就诊者以安心感。",
                41},

            // --- 运营部门 (Operations) ---
            {"穆尼尔", "管制室操作员", "OPERATIONS", "系统操作", "ACTIVE",
                "迦勒底管制室的资深操作员。负责灵子转移的坐标计算与监控。",
                "在迦勒底管制室工作的资深操作员。主要负责灵子转移过程中御主的坐标定位、身体数据监控以及转移通道的稳定性维持。在序章爆炸后，是留守管制室确保系统最低限度运作的核心成员之一。对藤丸立香执行的所有灵子转移任务均有着至高的职业敬意。虽然经常抱怨加班时间太长，但从未在任何一次任务中缺席。",
                50},

            {"拉普", "观测数据技师", "OPERATIONS", "数据分析", "ACTIVE",
                "专门从事SHEBA系统的数据解析的技术人员。对特异点的形成机制有着独到的见解。",
                "SHEBA系统专用数据解析技师。其日常工作是从SHEBA传回的庞大灵子流数据中筛选出有意义的异常信号。在Fuyuki特异点的发现过程中，他最早注意到了数据流中的不自然周期性波动。在迦勒底的正式评定中，其数据分析精确度在所有技术人员中位列前三。",
                51},

            {"塞拉菲姆", "虚数潜航技师", "OPERATIONS", "Shadow Border技术", "ACTIVE",
                "参与了Shadow Border的改造工程，负责虚数潜航时的导航系统运维。",
                "在Shadow Border的建造与后续改造中担任主要技术人员的年轻技师。专攻虚数空间导航系统。她所开发的「虚数坐标相位微调算法」大幅提高了Shadow Border在虚数空间中的航行稳定性。在异闻带剪定作战期间，她曾创下连续72小时不眠不休监控导航系统的记录，被戈尔德鲁夫所长亲自下令强制休息。",
                52},

            // --- 研究部门 (Research) ---
            {"诸葛孔明〔埃尔梅罗二世〕", "战术顾问·拟似从者", "RESEARCH", "战术规划", "ACTIVE",
                "以拟似从者形态常驻迦勒底的军师型英灵。负责灵子转移作战的战术推演与策略制定。",
                "作为拟似从者现界，由中国三国时代的军师诸葛孔明与时钟埠埃尔梅罗二世的意识融合而成。其现界目的并非直接参与战斗，而是以卓越的战略推演能力协助御主在各个特异点与异闻带中做出最优决策。他所设计的「石兵八阵」战术推演模型能够在不实际进入战场的情况下，对其中的数万种可能发展进行并行模拟。根据管制室记录，经他推演过的作战方案的执行成功率平均提高了42%。",
                20},

            {"福尔摩斯", "侦探顾问·从者", "RESEARCH", "情报分析", "ACTIVE",
                "夏洛克·福尔摩斯以从者姿态现界。以其超越时空的推理能力协助迦勒底探明各事象真相。",
                "以Rider职阶现界的夏洛克·福尔摩斯。其保有技能「侦探的双眼」能够看穿一切虚假与伪装的本质，在特异点调查中发挥着不可替代的作用。在第五特异点与第七特异点中曾提供了关键性的情报分析。虽然其不断追问「真相」的行事风格有时会让迦勒底方面感到困扰，但不可否认的是，他每次指出的「被忽略的事实」最终都确实指向了解决危机的关键路径。目前以顾问身份常驻迦勒底。",
                21},

            {"紫式部", "档案管理员·从者", "RESEARCH", "文献管理", "ACTIVE",
                "以Caster职阶现界的日本平安时代女作家。负责迦勒底图书馆的管理与古籍文书整备。",
                "以Caster职阶现界的古典文学家。在迦勒底主要负责图书馆的管理以及古文书的解读与整理工作。其生前所著《源氏物语》被认为是世界上最早的长篇写实小说。在迦勒底，她发现了一些与虚构世界产生交界的特殊书物，这些发现疑似与某类特殊的特异点形成机制有关。作为从者，她拥有着与平安时代相符的神秘学造诣。",
                22},
        };

        for (Object[] s : staff) {
            Personnel p = new Personnel();
            p.setName((String) s[0]);
            p.setTitle((String) s[1]);
            p.setDepartment((String) s[2]);
            p.setRole((String) s[3]);
            p.setStatus((String) s[4]);
            p.setBio((String) s[5]);
            p.setLoreText((String) s[6]);
            p.setSortOrder((Integer) s[7]);
            personnelRepository.save(p);
        }
    }

}
