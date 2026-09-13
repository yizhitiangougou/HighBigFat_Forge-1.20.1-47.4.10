# HighBigFat Mod

面向 Minecraft **1.20.1** 的 Forge 模组：**军衔阶梯上的清剿成长**（致敬 HighBigFat）。  
当前进度：**Phase 0 工程骨架已完成**；下一阶段为 Phase 1 最小好玩闭环（T1 + 功勋 + 短棍升衔）。

| 项目 | 说明 |
|------|------|
| Mod ID | `highbigfat_mod` |
| 显示名称 | HighBigFat Mod |
| 版本 | `1.0.0-1.20.1` |
| Minecraft | 1.20.1（兼容范围 `[1.20.1, 1.21)`） |
| Forge | 47.4.10（依赖范围 `[47,)`） |
| 映射 | Parchment `2023.09.03-1.20.1` |
| Java | 17 |
| 主包名 | `com.tianshu.highbigfat` |
| 作者 | Arctiria |
| 许可证 | All Rights Reserved |

仓库地址：<https://github.com/yizhitiangougou/HighBigFat_Forge-1.20.1-47.4.10>

### 项目文档（开发必读）

| 文档 | 用途 |
|------|------|
| [`HighBigFat_Handoff.md`](./HighBigFat_Handoff.md) | **接手指南**（现状、坑、第一个工作日） |
| [`HighBigFat_Dev_Plan.md`](./HighBigFat_Dev_Plan.md) | 分阶段任务与进度看板 |
| [`HighBigFat_Mod_Design_Doc.md`](./HighBigFat_Mod_Design_Doc.md) | 玩法企划（真相来源） |

---

## 工程结构

```text
.
├── build.gradle
├── gradle.properties         # 版本、Mod 元数据
├── HighBigFat_Handoff.md     # 交接文档
├── HighBigFat_Dev_Plan.md    # 开发计划
├── HighBigFat_Mod_Design_Doc.md
├── src/main/java/com/tianshu/highbigfat/
│   ├── HighBigFatMod.java    # 入口（仅注册挂载）
│   ├── Config.java           # 刷怪/功勋/军衔锁配置
│   ├── registry/             # ModItems / ModBlocks / ModCreativeTabs …
│   ├── client/ item/ merit/ entity/ event/
└── src/main/resources/assets/highbigfat_mod/
```

首次构建或运行后还会出现（已由 `.gitignore` 忽略，无需提交）：

- `build/`、`.gradle/`：构建缓存与产物
- `run/`、`run-data/`：开发客户端 / 服务端 / 数据生成工作目录
- `.idea/`：IDEA 工程文件

---

## 当前已有示例内容

主类 `HighBigFatMod` 中注册了 MDK 自带的示例内容，便于验证环境是否正常：

| 注册名 | 类型 | 说明 |
|--------|------|------|
| `highbigfat_mod:example_block` | 方块 + 方块物品 | 示例方块，会出现在「建筑方块」创造标签中 |
| `highbigfat_mod:example_item` | 食物物品 | 营养值 1、饱和度修正 2，可随时食用 |
| `highbigfat_mod:example_tab` | 创造模式标签页 | 图标为示例物品，内含示例物品 |

`Config` 演示了 Forge 通用配置（`COMMON`），包括是否记录泥土方块、魔法数字及其提示语、启动时打印的物品列表等。配置文件会在运行游戏后生成于 `run/config/`。

---

## 环境要求

- **JDK 17**（Minecraft 1.18+ 面向玩家的运行时即为 Java 17）
- 推荐 IDE：**IntelliJ IDEA**（也可使用 Eclipse）
- 网络可访问 Gradle / Forge / Parchment 相关 Maven 仓库

---

## 开发环境搭建

本工程遵循 Minecraft Forge 的 MDK 工作流：ForgeGradle 会对原版 MCP 相关产物打补丁，供模组访问所需 API。补丁基于 SRG 名称构建；日常开发请使用当前配置的映射名（本工程为 Parchment）阅读代码。

### 1. 克隆仓库

```bash
git clone https://github.com/yizhitiangougou/HighBigFat_Forge-1.20.1-47.4.10.git
cd HighBigFat_Forge-1.20.1-47.4.10
```

### 2. IntelliJ IDEA（推荐）

1. 使用 **Open** 打开本项目根目录（或选择 `build.gradle` 导入为 Gradle 项目）。
2. 等待 Gradle 同步与依赖下载完成。
3. 在项目根目录执行（Windows 可用 `gradlew.bat`）：

   ```bash
   ./gradlew genIntellijRuns
   ```

4. 在 IDEA 中刷新 Gradle 项目。完成后可使用运行配置：`runClient`、`runServer`、`runData`、`runGameTestServer`。

### 3. Eclipse

1. 在项目根目录执行：

   ```bash
   ./gradlew genEclipseRuns
   ```

2. Eclipse：**Import → Existing Gradle Project**，选择本工程目录；  
   或执行 `./gradlew eclipse` 生成 Eclipse 工程后再导入。

### 4. 常见维护命令

| 命令 | 作用 |
|------|------|
| `./gradlew --refresh-dependencies` | 刷新本地依赖缓存 |
| `./gradlew clean` | 清理构建产物（不影响你的源码） |
| `./gradlew build` | 编译并打包模组 |
| `./gradlew runClient` | 启动开发客户端 |
| `./gradlew runServer` | 启动开发服务端 |
| `./gradlew runData` | 运行数据生成（输出到 `src/generated/resources/`） |

若 IDE 中库缺失或同步异常，可先执行 `--refresh-dependencies`，再按需 `clean` 后重新导入。

---

## 构建与安装产物

```bash
./gradlew build
```

成功后，可发布的模组 JAR 一般位于：

```text
build/libs/highbigfat_mod-1.0.0-1.20.1.jar
```

将其放入 Minecraft 对应版本客户端的 `mods` 文件夹，并安装匹配的 **Forge 47.x** 即可游玩（开发阶段优先用 `runClient` 调试）。

版本号、Mod 名称等请在根目录 `gradle.properties` 中修改；`mods.toml` 与 `pack.mcmeta` 会在构建时自动注入这些属性。

---

## 映射说明（Parchment）

本工程使用 **Parchment** 映射（在官方 Mojang 映射之上叠加社区参数名与文档），便于阅读 Minecraft / Forge API。

使用 `official` 或 `parchment` 时需了解 Mojang 映射相关许可，详见：  
[MinecraftForge/MCPConfig — Mojang.md](https://github.com/MinecraftForge/MCPConfig/blob/master/Mojang.md)

Parchment 由 ParchmentMC 维护，与 Forge 相互独立。更多说明：  
[Parchment 入门](https://parchmentmc.org/docs/getting-started)

修改 `gradle.properties` 中的 `mapping_channel` / `mapping_version` 后，需重新执行工作区相关 setup / 同步任务以更新映射。

---

## 后续开发建议

1. 在 `gradle.properties` 中确认 `mod_id`、`mod_group_id`、`mod_version`、作者与描述等与计划一致。  
2. 以 `HighBigFatMod` / `Config` 为模板，逐步替换示例方块与物品，加入故事相关内容。  
3. 资源（模型、贴图、语言文件等）放在 `src/main/resources/assets/highbigfat_mod/` 下。  
4. 需要自动生成数据时使用 `runData`，产物目录为 `src/generated/resources/`（已在 `build.gradle` 中加入资源源集）。

---

## 参考链接

- [Forge 1.20.1 入门文档](https://docs.minecraftforge.net/en/1.20.1/gettingstarted/)
- [LexManos 安装演示视频](https://youtu.be/8VEdtQLuLO0)
- [Forge 论坛](https://forums.minecraftforge.net/)
- [Forge Discord](https://discord.minecraftforge.net/)

---

## 许可证

当前工程许可证为 **All Rights Reserved**（见 `LICENSE.txt` 与 `gradle.properties` 中的 `mod_license`）。如需改为开源协议，请同步更新上述文件与 `mods.toml` 注入字段。
