# Spartan Shields (1.21.1 NeoForge 非官方移植版)

> **⚠️ 这是一个非官方的社区移植版本**

An addon mod for Spartan Weaponry that adds shields with a similar aesthetic to the weapons from Spartan Weaponry.<br>
This mod adds a variety of shields with unique abilities and effects, including basic shields, tower shields, and special modded shields.

---

## Fork 说明

这是 **Spartan Shields** 的非官方 **Minecraft 1.21.1 NeoForge** 移植版本。

由于原作者 (ObliviousSp) 已停止更新超过半年，本项目基于原版代码进行了 1.21.1 NeoForge 适配。

- **原项目**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/spartan-shields) | [Modrinth](https://modrinth.com/mod/spartan-shields)
- **原作者**: ObliviousSp
- **移植者**: Mai-xiyu & Claude AI
- **许可证**: Apache-2.0

### 主要改动
- 从 Forge 1.20.1 迁移至 NeoForge 1.21.1
- 适配 Minecraft 1.21 的 API 变更（Data Components 系统、新的渲染系统等）
- 修复 Tower Shield BEWLR 渲染问题
- 修复 FE 供能盾牌的能量显示和工具提示
- 修复模组盾牌（Mekanism、EnderIO、Botania 等）的模型和纹理
- 修复各种兼容性问题

### 已知问题
- ~~某些 FE 供能的 Basic Shield（如 Dark Steel、Mekanism 系列）在物品栏中显示为透明~~ *(已修复：ItemColor 返回值缺少 Alpha 通道)*

---

## 功能特性

### 基础盾牌 (Basic Shields)
- 木质、石质、铁质、金质、钻石、下界合金等材质的基础盾牌
- 支持旗帜图案装饰

### 塔盾 (Tower Shields)  
- 更大的防护面积，提供更好的保护
- 使用自定义 3D 模型渲染

### 模组联动盾牌
- **Botania**: Manasteel, Elementium, Terrasteel 盾牌（使用 Mana 能量）
- **Mekanism**: Basic/Advanced/Elite/Ultimate Mekanist's 盾牌（使用 FE 能量）
- **EnderIO**: Dark Steel Riot 盾牌（使用 µI 能量）
- **Thermal Series**: Lumium, Enderium 盾牌
- **其他**: 支持 Flux Networks 等其他模组的能量系统

### 特殊能力
- 能量供能盾牌可以消耗能量抵消伤害
- 部分盾牌具有特殊附魔或效果
- 完整的自定义能量显示界面

---

## 依赖关系

### 必需
- **Minecraft**: 1.21.1
- **NeoForge**: 21.1.x
- **Spartan Weaponry Unofficial** (`spartanweaponryunofficial`): 1.21.1 版本（必须同时安装非官方移植版）

### 可选（提供额外内容）
- **Botania**: 魔力盾牌
- **Mekanism**: Mekanism 盾牌
- **EnderIO**: Dark Steel 盾牌  
- **Thermal Series**: 热力盾牌
- **Curios API**: 可以将盾牌作为饰品佩戴

---

## 安装方法

1. 确保已安装 **Minecraft 1.21.1** 和 **NeoForge 21.1.x**
2. 下载 **Spartan Weaponry Unofficial 1.21.1** (`spartanweaponryunofficial`) 和 **Spartan Shields 1.21.1**
3. 将两个 `.jar` 文件放入 `mods` 文件夹
4. 启动游戏

**重要提示**: 本模组需要与 Spartan Weaponry 的**非官方移植版**配合使用，Mod ID 为 `spartanweaponryunofficial`，不兼容原版 Spartan Weaponry。

---

## 编译指南

```bash
# 克隆仓库
git clone https://github.com/yourusername/SpartanShields-NeoForge.git
cd SpartanShields-NeoForge

# 编译
./gradlew build

# 编译后的文件位于
# build/libs/spartanshields-neoforge-{version}.jar
```

---

## 原版信息

Now open source under the Apache License 2.0!<br>
Find the original mod pages below:<br>
CurseForge -> https://www.curseforge.com/minecraft/mc-mods/spartan-shields<br>
Modrinth -> https://modrinth.com/mod/spartan-shields

---

## 许可证

本项目采用 **Apache License 2.0** 开源许可证。

这是一个非官方的社区移植项目，仅用于学习和交流目的。所有原始内容的版权归原作者 ObliviousSp 所有。

---

## 鸣谢

- **ObliviousSp** - 原作者，创造了这个精彩的模组
- **Claude AI** - 协助完成 1.21.1 NeoForge 迁移工作
- Spartan Weaponry 和 Minecraft 模组社区的所有贡献者

---

## 支持

如果你在使用过程中遇到问题：
1. 检查是否安装了所有必需的依赖项
2. 确认 Minecraft、NeoForge 和模组版本是否匹配
3. 在 GitHub Issues 中报告问题（如果适用）

**注意**: 这是一个非官方移植版本，原作者不提供技术支持。
