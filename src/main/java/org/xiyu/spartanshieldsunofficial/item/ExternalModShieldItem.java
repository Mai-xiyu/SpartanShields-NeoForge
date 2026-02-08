package org.xiyu.spartanshieldsunofficial.item;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.config.Config;
import org.xiyu.spartanshieldsunofficial.util.TierSS;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ExternalModShieldItem extends BasicShieldItem {
    protected String modName;

    public ExternalModShieldItem(TierSS toolMaterial, int defaultMaxDamage, boolean isTowerShieldIn, String externalModName, Item.Properties prop) {
        super(toolMaterial, defaultMaxDamage, isTowerShieldIn, prop);
        this.modName = externalModName;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        if (!Config.INSTANCE.forceDisableUncraftableTooltips.get()) {
            tooltip.add(Component.translatable("tooltip." + ModSpartanShields.ID + ".uncraftable_missing_mods", I18n.get("mod." + ModSpartanShields.ID + "." + this.modName)).withStyle(ChatFormatting.RED));
        }

        super.appendHoverText(stack, context, tooltip, flagIn);
    }
}
