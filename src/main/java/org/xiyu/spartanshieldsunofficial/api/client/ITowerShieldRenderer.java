package org.xiyu.spartanshieldsunofficial.api.client;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.xiyu.spartanshieldsunofficial.client.model.ShieldBaseModel;

/**
 * 塔盾自定义渲染接口。
 * <p>
 * 合并之前拆开的 Model + RenderInfo 为一个接口，更简洁。
 * </p>
 *
 * <h3>⚠️ 客户端生命周期警告</h3>
 * <p>
 * <b>请勿在 Item 构造函数、{@code DeferredRegister.register()} 的 Supplier、
 * 或任何可能在服务端执行的代码中直接 new 渲染器实例或引用此接口的任何实现类！</b>
 * </p>
 * <p>
 * 此接口的实现类会引用 {@code ModelPart}、{@code LayerDefinition} 等纯客户端类，
 * 在服务端（如专用服务器）加载时会导致 {@code ClassNotFoundException} 崩溃。
 * </p>
 * <p>
 * 正确做法：在 {@code FMLClientSetupEvent} 或模组构造器中判断 {@code Dist.CLIENT} 后再注册。
 * </p>
 */
@OnlyIn(Dist.CLIENT)
public interface ITowerShieldRenderer {

    /** 提供模型的 LayerDefinition */
    LayerDefinition createLayerDefinition();

    /** 从 baked ModelPart 创建模型实例 */
    ShieldBaseModel createModel(ModelPart root);

    /** 无旗帜图案时的纹理 */
    ResourceLocation getTextureNoPattern();

    /** 有旗帜图案时的纹理 */
    ResourceLocation getTexturePattern();

    /** 是否有额外渲染层（发光、能量效果等） */
    default boolean hasExtraLayers() { return false; }

    /** 额外渲染层的 RenderType */
    default RenderType getExtraLayerRenderType(ItemStack stack) { return RenderType.solid(); }

    /** 着色 RGB 红色通道（0.0~1.0） */
    default float tintRed()   { return 1.0f; }

    /** 着色 RGB 绿色通道（0.0~1.0） */
    default float tintGreen() { return 1.0f; }

    /** 着色 RGB 蓝色通道（0.0~1.0） */
    default float tintBlue()  { return 1.0f; }
}
