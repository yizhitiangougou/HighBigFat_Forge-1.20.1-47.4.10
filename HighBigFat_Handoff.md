# HighBigFat 项目交接文档

> **读者**：下一位人类开发者或智能体  
> **目的**：30 分钟内理解背景、约束、现状，并能按计划继续 Phase 1+  
> **交接日期**：2026-09-14  
> **交接时进度**：**Phase 0 已完成并经真人 runClient 验收通过**；下一阶段为 **Phase 1**

---

## 1. 先读这三份文档（顺序固定）

| 顺序 | 文档 | 职责 | 冲突时以谁为准 |
|------|------|------|----------------|
| 1 | [`HighBigFat_Mod_Design_Doc.md`](./HighBigFat_Mod_Design_Doc.md) | 玩法企划、数值、敌军/军衔/奖励真相来源 | **玩法真相** |
| 2 | [`HighBigFat_Dev_Plan.md`](./HighBigFat_Dev_Plan.md) | 阶段任务勾选、验收、禁区、进度日志 | **实施看板** |
| 3 | 本文 `HighBigFat_Handoff.md` | 工程现状、环境坑、约定、如何接着干 | **接手指南** |
| — | [`README.md`](./README.md) | 对外安装/简介 | 对外文案 |

**纪律（必须遵守）**

1. 开干前读 Dev Plan §1「北极星」与 §2「明确不做」。  
2. **只做当前 Phase 清单内任务**；新想法进候补池，不直接写代码。  
3. 未通过当前 Phase 验收，**禁止**跳去做 T4/T5/结构等后续内容。  
4. 改玩法方向：先改 Design Doc 版本说明 → 再改 Dev Plan → 再改代码。

---

## 2. 项目背景（一句话 + 动机）

**HighBigFat** 是致敬军人朋友的 Forge 模组：玩家沿军衔晋升，每一阶解锁专属奖励，并面对更强一档的「荒原扰敌」。

核心体验：

> **每一级军衔，都对应一种新敌人，和一件你想立刻装备上的奖励。**

主循环：

```text
遭遇档位敌军 → 击杀得功勋/掉落 → 晋升 → 当场发本阶招牌装备
→ 解锁更高档敌军 → 主动狩猎（特训引导）
```

**刻意不做**：写实枪械、载具、自定义维度、大型势力战争、超复杂多阶段 Boss 动画。  
技术选型优先：**原版实体变种 + 自定义属性/装备/掉落**，保证可分阶段落地。

---

## 3. 工程身份信息

| 项 | 值 |
|----|-----|
| 仓库 | https://github.com/yizhitiangougou/HighBigFat_Forge-1.20.1-47.4.10 |
| Minecraft | **1.20.1** |
| Forge | **47.4.10** |
| 映射 | Parchment `2023.09.03-1.20.1` |
| Java | **17**（toolchain 已配置；本机也可有更高 JDK，但编译目标是 17） |
| Mod ID | `highbigfat_mod`（必须与 `@Mod`、资源命名空间一致） |
| 主包名 | `com.tianshu.highbigfat` |
| 版本 | `1.0.0-1.20.1`（见 `gradle.properties`） |
| 作者显示名 | Arctiria |
| 许可证 | All Rights Reserved |

元数据集中在根目录 **`gradle.properties`**；`mods.toml` / `pack.mcmeta` 由 Gradle 注入，一般不要手写死版本号。

---

## 4. 当前完成度（接手时快照）

### 4.1 已完成：Phase 0 — 工程骨架

| 验收 | 状态 | 说明 |
|------|------|------|
| 创造页可见 HighBigFat 标签 | ✅ 真人验证 pass | 见 §7.2 坑：空标签会被隐藏 |
| 无 example 残留 | ✅ | MDK 示例方块/物品/标签已移除 |
| 包结构符合计划 | ✅ | 见 §5 |
| 模组列表可见 HighBigFat Mod | ✅ | 交接当日确认 |

Phase 0 交付物摘要：

- 主类瘦身：只做 DeferredRegister 挂载 + Common Config  
- 创造标签 `highbigfat_mod:main`  
- 占位物品 `dog_tag`（尚无功勋显示逻辑，仅保证标签可见）  
- Config 草案：`enableEnemySpawning` / `dailyMeritCap` / `enableRankSpawnGates`  
- 中英语言键（创造页、犬牌、配置项）  
- `item/` `merit/` `entity/` `event/` 以 `package-info` 占位，待 Phase 1 填入

### 4.2 未开始：Phase 1 — 最小好玩闭环（下一位的主线）

目标一句话：**夜间打 T1 → 功勋反馈 → 升列兵当场拿 `recruit_baton`。**

推荐实现顺序（与 Dev Plan / Design Doc 一致）：

1. 功勋 / 军衔数据持久化 + 犬牌显示（扩展现有 `dog_tag`）  
2. T1 `scrap_scavenger`（Zombie 变种）+ 掉落 + 击杀结算  
3. 帐篷方块晋升结算 + 发放短棍  
4. 军粮 / 绷带  
5. 成就与语言补全  

详细勾选与类级清单见 **Dev Plan §6**，勿在本文重复维护任务列表。

### 4.3 明确延后（整模组默认禁区）

详见 Dev Plan §2。接手时若被要求「加枪 / 加维度 / 大战场」，应拒绝并引导改 Design Doc。

---

## 5. 源码结构（现状）

```text
com.tianshu.highbigfat
├── HighBigFatMod.java          # 入口：注册 + Config；勿在此堆玩法
├── Config.java                 # 通用配置（刷怪/功勋上限/军衔锁）
├── client/ModClient.java       # 客户端 setup（渲染等后续放这里）
├── registry/
│   ├── ModBlocks.java          # 空 DeferredRegister（Phase 1 加帐篷）
│   ├── ModItems.java           # 已有 DOG_TAG
│   ├── ModEntityTypes.java     # 空（Phase 1 加 T1）
│   ├── ModSounds.java          # 空（可延后）
│   └── ModCreativeTabs.java    # main 标签，displayItems 必须含物品
├── item/                       # 占位 — 物品行为类放这里
├── merit/                      # 占位 — 功勋/军衔
├── entity/                     # 占位 — T1–T5
└── event/                      # 占位 — 击杀结算等
```

资源：

```text
src/main/resources/
├── META-INF/mods.toml
├── pack.mcmeta
└── assets/highbigfat_mod/
    ├── lang/en_us.json
    ├── lang/zh_cn.json
    └── models/item/dog_tag.json   # 暂复用原版 name_tag 贴图
```

**约定**：新内容进对应包；创造页物品在 `ModCreativeTabs` 的 `displayItems`（或后续统一 helper）里 `accept`，不要只注册物品却忘记挂标签。

---

## 6. 本地开发速查

### 6.1 环境

- JDK **17**（推荐；与 `java.toolchain` 一致）  
- IDE：IntelliJ IDEA + Gradle 导入  
- 首次：`./gradlew genIntellijRuns`，再刷新 Gradle  

### 6.2 常用命令

| 命令 | 用途 |
|------|------|
| `compileJava` | 快速语法/编译检查 |
| `runClient` | 开发客户端（验收首选） |
| `runServer` | 开发服务端 |
| `runData` | 数据生成 → `src/generated/resources/` |
| `build` | 产出 `build/libs/highbigfat_mod-*.jar` |

Windows：`gradlew.bat`。

### 6.3 Agent / CI 注意：Gradle 缓存路径

在部分自动化环境中，Gradle 用户目录可能被重定向到空沙箱缓存，导致找不到已下载的 Forge/Minecraft 依赖。若编译报 `Could not find net.minecraft:client:1.20.1` 一类错误，可显式指定：

```powershell
$env:GRADLE_USER_HOME = "$env:USERPROFILE\.gradle"
```

本机若 `gradlew` 因网络超时反复下载发行包，而 `~\.gradle\wrapper\dists\gradle-8.8-bin\...\gradle-8.8` 已存在，可直接调用该目录下的 `bin\gradle.bat`。  
`gradle-wrapper.properties` 中 `networkTimeout` 已调到 `120000`。

### 6.4 不要提交的目录

已由 `.gitignore` 忽略：`build/`、`.gradle/`、`run/`、`run-data/`、`.idea/` 等。不要把运行缓存强行入库。

---

## 7. 已知坑与注意事项（务必阅读）

### 7.1 创造标签：空标签不可见

Minecraft 1.20.1 对**没有任何 display 物品**的创造标签页会直接不显示。  
Phase 0 曾因此导致「模组已加载但找不到 HighBigFat 标签」。  
**修复原则**：标签至少 `accept` 一件物品；新增物品记得挂上标签。

### 7.2 奖励发放只在服务端

军衔晋升、发 `recruit_baton` 等逻辑必须在**服务端**执行，防止客户端伪造。Capability / SavedData 同步方案在 Phase 1 实现时一并设计。

### 7.3 实体需要客户端渲染注册

自定义实体除 `EntityType` 外，还需在客户端注册渲染器（可用原版僵尸/骷髅渲染器占位）。漏注册会导致实体不可见或崩溃。

### 7.4 军衔锁刷怪与日功勋上限

Config 已预留开关与数值；Phase 1 先实现功勋日上限与 T1 生成，Phase 2 再完善多档军衔锁。不要在未读 Config 的情况下写死无法配置的数值。

### 7.5 简化项（防 scope 膨胀）

| 企划项 | 允许的简化 | 不要一上来做 |
|--------|------------|--------------|
| `ration_pouch` | 进食加速等 | 独立食物专用背包 |
| T1 抢掉落物 | 不做 | 复杂兴趣 AI |
| T5 | 近战 + 半血召 T1 + 短抗性 | 多阶段动画 Boss |
| 营地结构 | 可先令状定点/指令 | 完整结构包优先 |

### 7.6 映射与许可

使用 Parchment/official 需了解 Mojang 映射相关说明：  
https://github.com/MinecraftForge/MCPConfig/blob/master/Mojang.md

### 7.7 Git / 协作

- 默认分支：`master`（已推送过初始工程；后续提交按团队习惯）  
- 文档与代码变更尽量同 PR/同提交说明「对齐 Design Doc 哪一节」  
- **未经要求不要 force push / 改 git config**

---

## 8. 接手后第一个工作日建议清单

1. [ ] 克隆/打开工程，JDK 17，IDEA 同步 Gradle，确认 `runClient` 能进游戏。  
2. [ ] 创造模式确认：**HighBigFat** 标签 + **犬牌**；模组列表有 HighBigFat Mod。  
3. [ ] 通读 Design Doc §1–§7、§10；通读 Dev Plan §0–§2、§6（Phase 1）。  
4. [ ] 在 Dev Plan「当前状态」确认仍是 Phase 1 待开始；若你已开动，立即更新进度日志。  
5. [ ] 从 Dev Plan §6.1 第 1 步开始：**功勋/军衔数据 + 犬牌显示**，不要先做 T5。  
6. [ ] 每完成一块可交付结果：勾选 `- [x]` + 进度日志一行。

---

## 9. 关键配置键（已存在）

| 配置键 | 默认 | 含义 |
|--------|------|------|
| `enableEnemySpawning` | `true` | 敌军自然生成总开关 |
| `dailyMeritCap` | `40` | 每日小额击杀功勋上限 |
| `enableRankSpawnGates` | `true` | 军衔锁高档刷怪 |

语言键前缀示例：`itemGroup.highbigfat_mod`、`item.highbigfat_mod.dog_tag`。  
新增物品/方块/实体时同步补 `en_us.json` 与 `zh_cn.json`。

---

## 10. 参考链接

- Forge 1.20.1 Getting Started：https://docs.minecraftforge.net/en/1.20.1/gettingstarted/  
- Forge 1.20.x Docs：https://docs.minecraftforge.net/en/1.20.x/  
- McJty 1.20 教程：https://mcjty.eu/docs/1.20/ep1  
- Parchment：https://parchmentmc.org/docs/getting-started  

---

## 11. 交接核对表（给交接手双方）

| 项 | 状态 |
|----|------|
| Design Doc v1.1 已存在且为玩法真相 | ✅ |
| Dev Plan 已存在且 Phase 0 勾选完成 | ✅ |
| Phase 0 创造页 + 模组列表真人验证 | ✅（2026-09-14） |
| 下一阶段目标明确为 Phase 1 最小闭环 | ✅ |
| 禁区与简化项已写入计划 | ✅ |
| 已知坑（空创造标签、Gradle 缓存）已记录 | ✅ |

---

## 12. 联系与归属

- 模组叙事主角 / 致敬对象：HighBigFat（高大胖）  
- 工程作者显示：Arctiria  
- 包名组织：`com.tianshu.highbigfat`  

若接手智能体：请在每次会话开始时先打开 **Dev Plan 当前状态**，再动手改代码；结束前更新勾选与进度日志。
