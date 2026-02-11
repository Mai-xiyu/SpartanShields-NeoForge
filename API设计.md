---
title: Spartan Shields API 参考
description: Spartan Shields Unofficial 模组的详细 API 文档与示例代码。
---

# Spartan Shields API 参考

本文档提供了 `Spartan Shields Unofficial` 模组的完整 API 开发指南。开发者可以使用此 API 创建自定义盾牌、注册新的资源类型、以及自定义塔盾渲染。

> 💡 **Mod ID**: `spartan_shields_unofficial`（带下划线）
> 
> 💡 **Java 包名**: `org.xiyu.spartanshieldsunofficial`（无下划线）
> 
> 💡 **API 包**: `org.xiyu.spartanshieldsunofficial.api`



## 📚 API 总览

### API 包结构

```
api/
├── SpartanShieldsAPI.java              // 唯一入口
├── shield/
│   ├── IShieldMaterial.java            // 盾牌材质
│   ├── ShieldType.java                 // BASIC / TOWER 枚举
│   ├── ShieldBuilder.java              // 流式建造者
│   └── IShieldBlockHandler.java        // 格挡回调
├── resource/
│   ├── IResourceType.java             // 资源类型定义
│   ├── IResourceStorage.java          // 资源存储操作
│   ├── ResourceRegistry.java          // 资源类型注册表
│   ├── SimpleResourceType.java        // 通用实现基类
│   └── AbstractEnergyStorage.java     // FE 存储封装
├── tag/
│   └── ShieldTags.java                // TagKey 常量
└── client/
    └── ITowerShieldRenderer.java      // 塔盾渲染接口
```

---

## 📦 依赖配置 (Gradle)

在 `build.gradle` 中添加 Spartan Shields Unofficial 作为依赖项：

```groovy
repositories {
    maven {
        url "https://cursemaven.com"
        content {
            includeGroup "curse.maven"
        }
    }
}

dependencies {
    // 替换 xxxxx 和 yyyyy 为 CurseForge 上的 Project ID 和 File ID
    implementation "curse.maven:spartan-shields-unofficial-xxxxx:yyyyy"
}
```

---

## 🚀 快速入门

### 入口类：`SpartanShieldsAPI`

所有 API 操作都从 `SpartanShieldsAPI` 出发：

```java
import org.xiyu.spartanshieldsunofficial.api.SpartanShieldsAPI;
```

| 方法 | 说明 |
| :--- | :--- |
| `createMaterial(int, int, TagKey<Item>)` | 创建盾牌材质 |
| `createMaterial(Tier, TagKey<Item>)` | 从原版 Tier 创建盾牌材质 |
| `registerResourceType(IResourceType)` | 注册新的资源类型 |
| `getResourceType(ResourceLocation)` | 查询已注册的资源类型 |
| `getAllResourceTypes()` | 获取所有已注册的资源类型 |
| `registerTowerShieldRenderer(Item, ITowerShieldRenderer)` | 注册塔盾自定义渲染器（仅客户端） |

---

## 🛡️ 盾牌创建

### 1. 定义材质

```java
import org.xiyu.spartanshieldsunofficial.api.SpartanShieldsAPI;
import org.xiyu.spartanshieldsunofficial.api.shield.IShieldMaterial;

// 方式一：直接指定数值
IShieldMaterial mithril = SpartanShieldsAPI.createMaterial(
    800,                    // 基础耐久值
    18,                     // 附魔能力值
    MyTags.MITHRIL_INGOT    // 修复材料 Tag
);

// 方式二：从原版 Tier 创建
IShieldMaterial diamond = SpartanShieldsAPI.createMaterial(
    Tiers.DIAMOND,          // 使用钻石级属性
    Tags.Items.GEMS_DIAMOND // 修复材料 Tag
);
```

#### `IShieldMaterial` 接口

| 方法 | 返回类型 | 说明 |
| :--- | :--- | :--- |
| `getDurability()` | `int` | 基础耐久值（塔盾自动 ×1.25 倍率） |
| `getEnchantability()` | `int` | 附魔能力值 |
| `getRepairTag()` | `TagKey<Item>` | 铁砧修复材料 Tag |

### 2. 使用 ShieldBuilder

`ShieldBuilder` 是创建盾牌的核心工具，支持流式 API：

```java
import org.xiyu.spartanshieldsunofficial.api.shield.ShieldBuilder;
import org.xiyu.spartanshieldsunofficial.api.shield.ShieldType;

// 注册一个基础盾牌
DeferredHolder<Item, ?> MITHRIL_SHIELD = ITEMS.register("mithril_shield",
    ShieldBuilder.create()
        .material(mithril)
        .build()
);

// 注册一个塔盾
DeferredHolder<Item, ?> MITHRIL_TOWER = ITEMS.register("mithril_tower_shield",
    ShieldBuilder.create()
        .material(mithril)
        .type(ShieldType.TOWER)
        .build()
);
```

#### ShieldBuilder 方法参考

| 方法 | 说明 | 必需/可选 |
| :--- | :--- | :--- |
| `create()` | 创建新的 Builder 实例 | — |
| `material(IShieldMaterial)` | 设置材质（耐久型盾牌） | 与 `poweredBy` 二选一 |
| `type(ShieldType)` | 设置盾牌类型，默认 `BASIC` | 可选 |
| `blockEffect(Holder<MobEffect>, int, int)` | 格挡时施加状态效果（可叠加） | 可选 |
| `blockHandler(IShieldBlockHandler)` | 格挡时执行自定义逻辑（可叠加） | 可选 |
| `poweredBy(IResourceType, int, int)` | 使盾牌由资源供能 | 与 `material` 二选一 |
| `bashable(boolean)` | 是否启用盾牌猛击，默认 `true` | 可选 |
| `build()` | 构建为 `Supplier<? extends ShieldBaseItem>` | 必需 |

#### `ShieldType` 枚举

| 值 | 说明 |
| :--- | :--- |
| `BASIC` | 基础盾牌 |
| `TOWER` | 塔盾 — 更大防护面积，耐久/容量 ×1.25，BEWLR 3D 渲染 |

### 3. 格挡效果（可叠加）

`blockEffect` 和 `blockHandler` 支持多次调用，效果依序叠加：

```java
DeferredHolder<Item, ?> CURSED_SHIELD = ITEMS.register("cursed_shield",
    ShieldBuilder.create()
        .material(mithril)
        .blockEffect(MobEffects.WITHER, 60, 1)             // 凋零 II, 3秒
        .blockEffect(MobEffects.MOVEMENT_SLOWDOWN, 100, 2)  // 缓慢 III, 5秒
        .blockHandler((shield, player, attacker, dmg) -> {
            // 播放自定义音效
            player.level().playSound(null, player.blockPosition(),
                SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 0.5f, 1.0f);
        })
        .build()
);
```

#### `IShieldBlockHandler` 接口

```java
@FunctionalInterface
public interface IShieldBlockHandler {
    /**
     * 盾牌成功格挡攻击时调用（伤害 ≥ 3.0 触发）。
     *
     * @param shield   盾牌 ItemStack
     * @param player   持盾玩家
     * @param attacker 攻击者（始终为 LivingEntity）
     * @param damage   原始伤害值
     */
    void onBlock(ItemStack shield, Player player, LivingEntity attacker, float damage);
}
```

支持 Lambda 表达式，例如：
```java
.blockHandler((shield, player, attacker, dmg) -> attacker.igniteForSeconds(3))
```

### 4. Builder 安全校验

`build()` 方法内置 `validate()` 校验，不合法的配置会在模组启动阶段立即抛出 `IllegalStateException`：

| 错误场景 | 异常信息 |
| :--- | :--- |
| 既无 `material` 也无 `poweredBy` | "Shield must have either a material or a resource type" |
| 同时设置 `material` 和 `poweredBy` | "Shield cannot have both material AND resource type" |
| `poweredBy` 的 `capacity ≤ 0` | "poweredBy capacity must be > 0" |
| `poweredBy` 的 `maxReceive ≤ 0` | "poweredBy maxReceive must be > 0" |

```java
// ❌ 错误：空壳盾牌
ShieldBuilder.create().build();
// → IllegalStateException

// ❌ 错误：同时设置材质和资源
ShieldBuilder.create().material(mat).poweredBy(ResourceRegistry.ENERGY, 100000, 500).build();
// → IllegalStateException

// ✅ 正确
ShieldBuilder.create().material(mat).build();
ShieldBuilder.create().poweredBy(ResourceRegistry.ENERGY, 500000, 2000).build();
```

---

## ⚡ 资源系统

资源系统是 API 的核心扩展点，允许附属模组注册任意类型的能量/魔力/应力作为盾牌的供能来源。

### 核心接口：`IResourceType`

每种资源类型都是一个 `IResourceType` 实例，定义了资源的存储、显示和 Capability 注册行为。

| 方法 | 说明 |
| :--- | :--- |
| `getId()` | 全局唯一 ID，如 `neoforge:energy` |
| `getDisplayName()` | Tooltip 中的显示名，如 `FE` |
| `getDataComponent()` | 关联的 `DataComponentType`（1.21+ 同步关键） |
| `getStored(ItemStack)` | 读取当前存储量 |
| `setStored(ItemStack, int)` | 写入存储量 |
| `formatCapacityTooltip(int, int)` | 格式化容量显示 |
| `formatChargeRateTooltip(int)` | 格式化充能速率显示 |
| `formatPerDamageTooltip(int)` | 格式化每次伤害消耗显示 |
| `getBarColor()` | 物品耐久条颜色 (RGB) |
| `onRegisterCapabilities(...)` | 注册 NeoForge Capability（可选，默认空实现） |

:::caution[关于 DataComponent]
在 Minecraft 1.21+ 中，客户端**无法直接读取服务端的 NBT**。数据必须通过 `DataComponent` 系统才能正确同步到客户端。

如果 `getStored()` 底层使用了不会同步的存储方式，客户端的 Tooltip 会显示错误值（如 "0 / 100,000 FE"）。

因此每个 `IResourceType` **必须**声明其关联的 `DataComponentType<?>`，且注册时必须包含 `networkSynchronized()` 调用：

```java
// ✅ 正确
builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)

// ❌ 错误 — 客户端无法同步
builder.persistent(Codec.INT)  // 缺少 networkSynchronized
```
:::

### 预置资源类型

`ResourceRegistry` 中预置了 2 种资源类型：

| 常量 | ID | 显示名 | 耐久条颜色 | 说明 |
| :--- | :--- | :--- | :--- | :--- |
| `ResourceRegistry.ENERGY` | `neoforge:energy` | FE | 蓝色 `0x69B3FF` | NeoForge 标准能量单位 |
| `ResourceRegistry.MICRO_INFINITY` | `enderio:micro_infinity` | µI | 绿色 `0x4DA24B` | EnderIO 能量单位（底层复用 FE） |

### 使用预置资源类型创建能量盾牌

```java
import org.xiyu.spartanshieldsunofficial.api.resource.ResourceRegistry;

DeferredHolder<Item, ?> FLUX_SHIELD = ITEMS.register("flux_shield",
    ShieldBuilder.create()
        .type(ShieldType.TOWER)
        .poweredBy(ResourceRegistry.ENERGY, 500000, 2000)
        .build()
);

// ✅ 不需要手动注册 Capability！
// 主模组在 RegisterCapabilitiesEvent 时自动处理：
// 1. 扫描所有通过 ShieldBuilder.poweredBy() 创建的盾牌
// 2. 调用 resourceType.onRegisterCapabilities()
// 3. AbstractEnergyStorage 自动注册 Capabilities.EnergyStorage.ITEM
```

### 注册自定义资源类型

#### 使用 `SimpleResourceType`（推荐，只需 4 个参数）

`SimpleResourceType` 提供了 `IResourceType` 的开箱即用实现，自动处理 `getStored`/`setStored`/`format*Tooltip`：

```java
import org.xiyu.spartanshieldsunofficial.api.resource.SimpleResourceType;
import org.xiyu.spartanshieldsunofficial.api.SpartanShieldsAPI;

// 第一步：注册自己的 DataComponent（必须包含 networkSynchronized！）
public class MyDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS =
        DeferredRegister.createDataComponents("my_addon_mod");

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> STORED_MANA =
        COMPONENTS.registerComponentType("stored_mana", builder ->
            builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)
        );
}

// 第二步：在模组构造器中注册资源类型
// 注意：直接传入 DeferredHolder（它实现了 Supplier），不要调用 .get()！
// 模组构造器阶段注册表事件尚未触发，.get() 会抛出 NullPointerException
SpartanShieldsAPI.registerResourceType(new SimpleResourceType(
    ResourceLocation.fromNamespaceAndPath("botania", "mana"),   // 全局唯一 ID
    Component.literal("Mana"),                                   // 显示名
    MyDataComponents.STORED_MANA,                                // DeferredHolder IS-A Supplier
    0x00C6FF                                                     // 耐久条颜色
));

// 第三步：使用该资源类型创建盾牌
IResourceType mana = ResourceRegistry.get(
    ResourceLocation.fromNamespaceAndPath("botania", "mana")
).orElseThrow();

DeferredHolder<Item, ?> MANA_SHIELD = ITEMS.register("mana_shield",
    ShieldBuilder.create()
        .poweredBy(mana, 10000, 100)
        .blockEffect(MobEffects.REGENERATION, 40, 0)  // 还能同时加回血效果
        .build()
);
```

想自定义 Tooltip 格式？直接 override：

```java
IResourceType customMana = new SimpleResourceType(...) {
    @Override
    public Component formatCapacityTooltip(int stored, int capacity) {
        return Component.literal("✦ " + stored + " / " + capacity + " Mana")
            .withStyle(ChatFormatting.AQUA);
    }
};
```

#### 使用 `AbstractEnergyStorage`（FE 兼容型资源）

如果你的资源类型底层仍是 NeoForge Energy（如 RF），使用 `AbstractEnergyStorage` 可自动注册 `Capabilities.EnergyStorage.ITEM`：

```java
import org.xiyu.spartanshieldsunofficial.api.resource.AbstractEnergyStorage;

// 创建一个显示为 "RF" 的能量类型（底层仍是 FE）
SpartanShieldsAPI.registerResourceType(new AbstractEnergyStorage(
    ResourceLocation.fromNamespaceAndPath("thermal", "redstone_flux"),
    Component.literal("RF"),
    ModDataComponents.STORED_ENERGY,        // DeferredHolder IS-A Supplier，直接传入
    0xCC4C4C                                 // 红色耐久条
));
```

### 类层次关系

```
IResourceType (接口)
  └── SimpleResourceType (通用基类，4 参数即用)
        └── AbstractEnergyStorage (FE 适配，自动注册 Capability)
```

| 类 | 使用场景 |
| :--- | :--- |
| `IResourceType` | 需要完全自定义的资源类型（如复杂的魔力系统） |
| `SimpleResourceType` | 大部分场景 — 只需 4 个参数，自带默认 Tooltip 格式 |
| `AbstractEnergyStorage` | 基于 FE 的资源类型 — 自动注册 NeoForge Energy Capability |

### `IResourceStorage` 接口

附属模组可通过 `instanceof IResourceStorage` 判断盾牌是否为资源供能类型，并进行读写操作：

```java
if (shieldItem instanceof IResourceStorage storage) {
    IResourceType type = storage.getResourceType();
    int capacity = storage.getCapacity();
    int maxReceive = storage.getMaxReceive();

    // 接收资源
    int received = storage.receive(stack, 1000, false);

    // 提取资源（盾牌默认返回 0）
    int extracted = storage.extract(stack, 1000, false);
}
```

| 方法 | 返回类型 | 说明 |
| :--- | :--- | :--- |
| `getResourceType()` | `IResourceType` | 获取该盾牌使用的资源类型 |
| `getCapacity()` | `int` | 获取最大容量 |
| `getMaxReceive()` | `int` | 获取最大接收速率 |
| `receive(ItemStack, int, boolean)` | `int` | 接收资源，返回实际接收量 |
| `extract(ItemStack, int, boolean)` | `int` | 提取资源，返回实际提取量 |

### `ResourceRegistry` 注册防冲突

`ResourceRegistry` 对重复 ID 注册会立即抛出 `IllegalArgumentException`：

```java
// 第一次注册 — 成功
SpartanShieldsAPI.registerResourceType(myMana);

// 第二次注册同一 ID — 抛出异常
SpartanShieldsAPI.registerResourceType(anotherMana);
// → IllegalArgumentException: Resource type 'botania:mana' is already registered!

// 建议：先检查是否已存在
ResourceRegistry.get(ResourceLocation.fromNamespaceAndPath("botania", "mana"))
    .ifPresentOrElse(
        existing -> { /* 已被其他附属模组注册，直接使用 */ },
        () -> { /* 尚未注册，执行注册 */ }
    );
```

---

## 🏷️ 标签系统

`ShieldTags` 提供了公开的 `TagKey<Item>` 常量。附属模组将自定义盾牌加入这些 Tag 即可获得对应功能：

| 常量 | Tag 路径 | 功能 |
| :--- | :--- | :--- |
| `ShieldTags.BASIC_SHIELDS` | `spartan_shields_unofficial:basic_shields` | 基础盾牌附魔支持 |
| `ShieldTags.TOWER_SHIELDS` | `spartan_shields_unofficial:tower_shields` | 塔盾附魔支持 |
| `ShieldTags.SHIELDS_WITH_BASH` | `spartan_shields_unofficial:shields_with_bash` | 盾击功能 |

在数据包的 Tag JSON 文件中添加您的盾牌：

```json
// data/spartan_shields_unofficial/tags/item/basic_shields.json
{
  "replace": false,
  "values": [
    "mymod:mithril_shield"
  ]
}
```

:::caution[盾牌猛击]
通过 `ShieldBuilder` 创建的盾牌**默认启用**猛击功能（`bashable(true)`），无需手动添加数据包 Tag。

如果想禁用某个盾牌的猛击（例如纯防御型盾牌），调用 `.bashable(false)`：

```java
ShieldBuilder.create()
    .material(mithril)
    .bashable(false)   // 禁用猛击
    .build()
```

如果确实需要通过数据包管理猛击（例如可配置的资源包），附属模组仍可将盾牌加入 `shields_with_bash` Tag：

```json
// data/spartan_shields_unofficial/tags/item/shields_with_bash.json
{
  "replace": false,
  "values": [
    "mymod:mithril_shield"
  ]
}
```

同理，如需附魔支持，仍需将盾牌加入 `basic_shields` 或 `tower_shields` Tag。
:::

---

## 🎨 塔盾渲染

### `ITowerShieldRenderer` 接口

为自定义塔盾提供完整的渲染控制，合并了模型、纹理和着色为单一接口：

| 方法 | 返回类型 | 说明 | 默认值 |
| :--- | :--- | :--- | :--- |
| `createLayerDefinition()` | `LayerDefinition` | 模型定义 | 必须实现 |
| `createModel(ModelPart)` | `ShieldBaseModel` | 创建模型实例 | 必须实现 |
| `getTextureNoPattern()` | `ResourceLocation` | 无旗帜图案纹理 | 必须实现 |
| `getTexturePattern()` | `ResourceLocation` | 有旗帜图案纹理 | 必须实现 |
| `hasExtraLayers()` | `boolean` | 是否有额外渲染层 | `false` |
| `getExtraLayerRenderType(ItemStack)` | `RenderType` | 额外层 RenderType | `RenderType.solid()` |
| `tintRed()` | `float` | 着色红通道 (0.0~1.0) | `1.0f` |
| `tintGreen()` | `float` | 着色绿通道 (0.0~1.0) | `1.0f` |
| `tintBlue()` | `float` | 着色蓝通道 (0.0~1.0) | `1.0f` |

### 注册渲染器

:::danger[客户端生命周期]
**请勿**在 Item 构造函数、`DeferredRegister.register()` 的 Supplier、或任何可能在服务端执行的代码中直接 `new` 渲染器实例！

`ITowerShieldRenderer` 的实现类引用了 `ModelPart`、`LayerDefinition` 等**纯客户端类**，在专用服务器上加载会导致 `ClassNotFoundException` 崩溃。
:::

正确的注册方式：

```java
// 在模组构造器中
public MyMod(IEventBus modBus, Dist dist) {
    if (dist == Dist.CLIENT) {
        modBus.addListener(this::onClientSetup);
    }
}

private void onClientSetup(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
        SpartanShieldsAPI.registerTowerShieldRenderer(
            MY_TOWER_SHIELD.get(),
            new ITowerShieldRenderer() {
                @Override
                public LayerDefinition createLayerDefinition() { /* ... */ }

                @Override
                public ShieldBaseModel createModel(ModelPart root) { /* ... */ }

                @Override
                public ResourceLocation getTextureNoPattern() {
                    return ResourceLocation.fromNamespaceAndPath("mymod", "textures/entity/shield/my_tower.png");
                }

                @Override
                public ResourceLocation getTexturePattern() {
                    return ResourceLocation.fromNamespaceAndPath("mymod", "textures/entity/shield/my_tower_pattern.png");
                }

                @Override
                public float tintRed() { return 0.8f; }    // 自定义着色
                @Override
                public float tintGreen() { return 0.9f; }
                @Override
                public float tintBlue() { return 1.0f; }
            }
        );
    });
}
```

---

## 📋 完整示例

### 示例 1：最简单 — 新材质盾牌

```java
public class MyShieldAddon {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(Registries.ITEM, "my_addon");

    static final IShieldMaterial MITHRIL =
        SpartanShieldsAPI.createMaterial(800, 18, MyTags.MITHRIL_INGOT);

    public static final DeferredHolder<Item, ?> MITHRIL_SHIELD =
        ITEMS.register("mithril_shield",
            ShieldBuilder.create().material(MITHRIL).build()
        );

    public static final DeferredHolder<Item, ?> MITHRIL_TOWER =
        ITEMS.register("mithril_tower_shield",
            ShieldBuilder.create().material(MITHRIL).type(ShieldType.TOWER).build()
        );
}
```

### 示例 2：能量盾牌（使用预置 FE）

```java
public static final DeferredHolder<Item, ?> FLUX_SHIELD =
    ITEMS.register("flux_shield",
        ShieldBuilder.create()
            .type(ShieldType.TOWER)
            .poweredBy(ResourceRegistry.ENERGY, 500000, 2000)
            .build()
    );
// Capability 自动注册 ✅ — 可被 Mekanism/Thermal 等充电器识别
```

### 示例 3：自定义 Mana 资源盾牌

```java
// === 模组构造器中 ===
public MyMod(IEventBus modBus) {
    MyDataComponents.COMPONENTS.register(modBus);

    // 注册 Mana 资源类型 — 直接传 DeferredHolder
    SpartanShieldsAPI.registerResourceType(new SimpleResourceType(
        ResourceLocation.fromNamespaceAndPath("botania", "mana"),
        Component.literal("Mana"),
        MyDataComponents.STORED_MANA,  // DeferredHolder IS-A Supplier
        0x00C6FF
    ));
}

// === 物品注册 ===
IResourceType mana = ResourceRegistry.get(
    ResourceLocation.fromNamespaceAndPath("botania", "mana")
).orElseThrow();

public static final DeferredHolder<Item, ?> MANA_SHIELD =
    ITEMS.register("mana_shield",
        ShieldBuilder.create()
            .poweredBy(mana, 10000, 100)
            .blockEffect(MobEffects.REGENERATION, 40, 0)
            .build()
    );
```

### 示例 4：Create 应力盾牌

```java
SpartanShieldsAPI.registerResourceType(new SimpleResourceType(
    ResourceLocation.fromNamespaceAndPath("create", "stress_units"),
    Component.literal("SU"),
    MyDataComponents.STORED_STRESS,  // DeferredHolder IS-A Supplier
    0xFFED50
));

public static final DeferredHolder<Item, ?> BRASS_SHIELD =
    ITEMS.register("brass_mechanical_shield",
        ShieldBuilder.create()
            .type(ShieldType.TOWER)
            .poweredBy(
                ResourceRegistry.get(ResourceLocation.fromNamespaceAndPath("create", "stress_units")).orElseThrow(),
                256, 32
            )
            .build()
    );
```

### 示例 5：格挡反弹 + 塔盾渲染

```java
public static final DeferredHolder<Item, ?> MIRROR_SHIELD =
    ITEMS.register("mirror_shield",
        ShieldBuilder.create()
            .material(SpartanShieldsAPI.createMaterial(Tiers.DIAMOND, Tags.Items.GEMS_DIAMOND))
            .type(ShieldType.TOWER)
            .blockHandler((shield, player, attacker, dmg) -> {
                attacker.knockback(1.5,
                    player.getX() - attacker.getX(),
                    player.getZ() - attacker.getZ());
            })
            .build()
    );

// 客户端渲染 — 在 FMLClientSetupEvent 中注册
private void onClientSetup(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
        SpartanShieldsAPI.registerTowerShieldRenderer(MIRROR_SHIELD.get(), new MyMirrorRenderer());
    });
}
```

---

## ⚠️ 注意事项

### 1. ARGB 颜色格式

Minecraft 1.21+ 的 `ItemColor.getColor()` 必须返回 `0xFFRRGGBB` 格式（含 alpha 通道），否则物品栏中物品会变透明。

### 2. DataComponent 同步

附属模组定义的 `DataComponentType` **必须包含** `networkSynchronized()` 调用：
```java
// ✅ 正确
builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)

// ❌ 错误 — 客户端 Tooltip 会显示 0
builder.persistent(Codec.INT)
```

主模组预置的 `ModDataComponents.STORED_ENERGY` 已正确配置同步，使用 `ResourceRegistry.ENERGY` 或 `ResourceRegistry.MICRO_INFINITY` 无需担心。

### 3. 资源类型注册时机

必须在**模组构造器**中调用 `SpartanShieldsAPI.registerResourceType()`。NeoForge 的模组加载顺序不保证 `FMLCommonSetupEvent` 的先后，太晚注册会导致其他模组的 `ResourceRegistry.get()` 返回空。

⚠️ **`DeferredHolder` 在模组构造器阶段尚未绑定**，调用 `.get()` 会抛出 `NullPointerException`。`SimpleResourceType` 和 `AbstractEnergyStorage` 都提供了接受 `Supplier<DataComponentType<Integer>>` 的构造器，而 NeoForge 的 `DeferredHolder` 本身实现了 `Supplier` 接口，因此**直接传入 `DeferredHolder` 即可**——首次实际使用时才会解析：

```java
// ✅ 正确 — 直接传 DeferredHolder
new SimpleResourceType(id, name, MyDataComponents.STORED_MANA, color)

// ❌ 错误 — 模组构造器中 .get() 会崩
new SimpleResourceType(id, name, MyDataComponents.STORED_MANA.get(), color)
```

### 4. 注册防冲突

如果两个附属模组都注册了 `"botania:mana"` 资源类型，后注册的会收到 `IllegalArgumentException`。建议联动类附属模组之间先用 `ResourceRegistry.get()` 检查是否已存在。

### 5. 客户端隔离

`api.client` 包内的接口标记了 `@OnlyIn(Dist.CLIENT)`，服务端不可引用。**绝对不要**在 Item 构造函数或 DeferredRegister Supplier 中 new 渲染器实例。

### 6. 渲染器注册时机

必须在 `FMLClientSetupEvent` 的 `enqueueWork()` 中注册，或在模组构造器中判断 `dist == Dist.CLIENT` 后注册。直接在静态字段中 new 渲染器会导致专用服务器崩溃。

### 7. 塔盾倍率

`ShieldType.TOWER` 自动应用配置中的倍率（当前 ×1.25），`poweredBy` 的容量也会自动乘以此倍率。

### 8. Capability 全自动注册

通过 `ShieldBuilder.poweredBy()` 创建的盾牌，主模组在 `RegisterCapabilitiesEvent` 时会自动扫描并调用 `IResourceType.onRegisterCapabilities()`。**附属模组无需手写任何 Capability 事件监听代码。**

### 9. Builder 安全校验

`ShieldBuilder.build()` 在返回 `Supplier` 前执行 `validate()`，不合法配置（空壳、互斥、零容量等）会立即抛出 `IllegalStateException` 并附带明确的错误消息。