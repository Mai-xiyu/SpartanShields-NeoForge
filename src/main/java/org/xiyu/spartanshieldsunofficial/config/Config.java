package org.xiyu.spartanshieldsunofficial.config;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import org.xiyu.spartanshieldsunofficial.ModSpartanShields;
import org.xiyu.spartanshieldsunofficial.crafting.condition.TypeDisabledCondition;
import org.xiyu.spartanshieldsunofficial.init.ModItems;
import org.xiyu.spartanshieldsunofficial.item.ShieldBaseItem;
import org.xiyu.spartanshieldsunofficial.util.Defaults;
import org.xiyu.spartanshieldsunofficial.util.Log;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = ModSpartanShields.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final String categoryGeneral = "general";
    public static final String categoryShieldBash = "shield_bash";
    public static final String categoryVanilla = "vanilla";
    public static final String categoryModdedCommon = "modded_common";
    public static final String categoryThermalMods = "modded_thermal";
    public static final String categoryModdedBotania = "modded_botania";
    public static final String categoryModdedAbyssalcraft = "modded_abyssalcraft";
    public static final String categoryModdedBWM = "modded_bwm";
    public static final String categoryModdedMekanism = "modded_mekanism";
    public static final String categoryPowered = "modded_powered";

    public static final Config INSTANCE;
    public static final ForgeConfigSpec CONFIG_SPEC;

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        CONFIG_SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static class ShieldValues {
        private final String disabledName;
        private final IntValue durability;
        private final BooleanValue isDisabled;

        public ShieldValues(ForgeConfigSpec.Builder builder, int defaultDurability, String name, String disableType) {
            this.disabledName = disableType;
            builder.push(name.toLowerCase());
            this.durability = builder.
                    comment("Durability for " + name + " Shields").
                    translation("config." + ModSpartanShields.ID + ".durability").
                    defineInRange("durability", defaultDurability, 0, 100000);
            this.isDisabled = builder.
                    comment("Set to true to disable " + name + " Shields").
                    translation("config." + ModSpartanShields.ID + ".disable").
                    define("disable", false);
            builder.pop();
        }

        public int getDurability() {
            return this.durability.get();
        }

        public boolean getIsDisabled() {
            return this.isDisabled.get();
        }

        public void updateFromConfig() {
            TypeDisabledCondition.updateType(this.disabledName, this.getIsDisabled());
        }
    }

    public IntValue durabilityWoodShield;
    public IntValue durabilityStoneShield;
    public IntValue durabilityCopperShield;
    public IntValue durabilityIronShield;
    public IntValue durabilityGoldShield;
    public IntValue durabilityDiamondShield;
    public IntValue durabilityNetheriteShield;
    public ShieldValues obsidian;

    public ShieldValues tin;
    public ShieldValues bronze;
    public ShieldValues steel;
    public ShieldValues silver;
    public ShieldValues electrum;
    public ShieldValues lead;
    public ShieldValues nickel;
    public ShieldValues invar;
    public ShieldValues constantan;
    public ShieldValues platinum;
    public ShieldValues aluminum;

    public IntValue durabilityManasteelShield;
    public IntValue durabilityTerrasteelShield;
    public IntValue durabilityElementiumShield;

    public IntValue durabilityOsmiumShield;
    public IntValue durabilityLapisLazuliShield;
    public IntValue durabilityRefinedGlowstoneShield;
    public IntValue durabilityRefinedObsidianShield;

    public IntValue durabilitySignalumShield;
    public IntValue durabilityLumiumShield;
    public IntValue durabilityEnderiumShield;

    public BooleanValue vanillaOnly;
    public BooleanValue forceDisableUncraftableTooltips;
    public DoubleValue towerShieldDurabilityMultiplier;
    //public BooleanValue disableSpikesEnchantment;
    //public BooleanValue disableTowerShields;
    //public BooleanValue disableStandardShields;
    public IntValue cooldownShieldBash;
    public IntValue cooldownTowerShieldBash;
    public BooleanValue disableShieldBash;

    public IntValue damageToFEMultiplier;

    private Config(ForgeConfigSpec.Builder builder) {
        // General Category
        builder.push(categoryGeneral);
        this.vanillaOnly = builder.
                comment("Set to true to have only Vanilla-based shields enabled. Note that this removes mod-based Shield recipes from the game!").
                translation("config." + ModSpartanShields.ID + ".vanilla_only").
                define("vanilla_only", false);
        this.forceDisableUncraftableTooltips = builder.
                comment("Will force the uncraftable shield tooltip (highlighted in red) that show on some shields to not appear. Useful for modpack makers who wish to change recipes").
                translation("config." + ModSpartanShields.ID + ".force_disable_uncraftable_tooltips").
                define("force_disable_uncraftable_tooltips", false);
        this.towerShieldDurabilityMultiplier = builder.
                comment("Durability Multiplier for Tower Shield variants compared to their basic Shield counterparts").
                translation("config." + ModSpartanShields.ID + ".tower_shield_durability_multiplier").
                defineInRange("tower_shield_durability_multiplier", Defaults.DefaultTowerShieldDurabilityMultiplier, 1.0d, 10.0d);
        builder.pop();
        // Shield Bash Category
        builder.push(categoryShieldBash);
        this.cooldownShieldBash = builder.
                comment("Cooldown period for Shield Bashes").
                translation("config." + ModSpartanShields.ID + ".cooldown_shield_bash").
                defineInRange("cooldown", 30, 1, 1000);
        this.cooldownTowerShieldBash = builder.
                comment("Cooldown period for Shield Bashes for Tower Shields").
                translation("config." + ModSpartanShields.ID + ".cooldown_tower_shield_bash").
                defineInRange("cooldown_tower", 50, 1, 1000);
        this.disableShieldBash = builder.
                comment("Set to true to disable the Shield Bash feature. Shield bash enchantments will no longer have any effect").
                translation("config." + ModSpartanShields.ID + ".disable_shield_bash").
                define("disabled", false);
        builder.pop();
        // Vanilla Category
        builder.push(categoryVanilla);
        this.durabilityWoodShield = this.defineDurability(builder, Defaults.DefaultDurabilityWoodShield, "Wood");
        this.durabilityStoneShield = this.defineDurability(builder, Defaults.DefaultDurabilityStoneShield, "Stone");
        this.durabilityCopperShield = this.defineDurability(builder, Defaults.DefaultDurabilityCopperShield, "Copper");
        this.durabilityIronShield = this.defineDurability(builder, Defaults.DefaultDurabilityIronShield, "Iron");
        this.durabilityGoldShield = this.defineDurability(builder, Defaults.DefaultDurabilityGoldShield, "Gold");
        this.durabilityDiamondShield = this.defineDurability(builder, Defaults.DefaultDurabilityDiamondShield, "Diamond");
        this.durabilityNetheriteShield = this.defineDurability(builder, Defaults.DefaultDurabilityNetheriteShield, "Netherite");
        this.obsidian = new ShieldValues(builder, Defaults.DefaultDurabilityObsidianShield, "Obsidian", TypeDisabledCondition.OBSIDIAN);
        builder.pop();
        builder.push(categoryModdedCommon);
        this.tin = new ShieldValues(builder, Defaults.DefaultDurabilityTinShield, "Tin", TypeDisabledCondition.TIN);
        this.bronze = new ShieldValues(builder, Defaults.DefaultDurabilityBronzeShield, "Bronze", TypeDisabledCondition.BRONZE);
        this.steel = new ShieldValues(builder, Defaults.DefaultDurabilitySteelShield, "Steel", TypeDisabledCondition.STEEL);
        this.silver = new ShieldValues(builder, Defaults.DefaultDurabilitySilverShield, "Silver", TypeDisabledCondition.SILVER);
        this.electrum = new ShieldValues(builder, Defaults.DefaultDurabilityElectrumShield, "Electrum", TypeDisabledCondition.ELECTRUM);
        this.lead = new ShieldValues(builder, Defaults.DefaultDurabilityLeadShield, "Lead", TypeDisabledCondition.LEAD);
        this.nickel = new ShieldValues(builder, Defaults.DefaultDurabilityNickelShield, "Nickel", TypeDisabledCondition.NICKEL);
        this.invar = new ShieldValues(builder, Defaults.DefaultDurabilityInvarShield, "Invar", TypeDisabledCondition.INVAR);
        this.constantan = new ShieldValues(builder, Defaults.DefaultDurabilityConstantanShield, "Constantan", TypeDisabledCondition.CONSTANTAN);
        this.platinum = new ShieldValues(builder, Defaults.DefaultDurabilityPlatinumShield, "Platinum", TypeDisabledCondition.PLATINUM);
        this.aluminum = new ShieldValues(builder, Defaults.DefaultDurabilityAluminumShield, "Aluminum", TypeDisabledCondition.ALUMINUM);
        builder.pop();
        builder.push(categoryModdedBotania);
        this.durabilityManasteelShield = this.defineDurability(builder, Defaults.DefaultDurabilityManasteelShield, "Manasteel");
        this.durabilityTerrasteelShield = this.defineDurability(builder, Defaults.DefaultDurabilityTerrasteelShield, "Terrasteel");
        this.durabilityElementiumShield = this.defineDurability(builder, Defaults.DefaultDurabilityElementiumShield, "Elementium");
        builder.pop();
        builder.push(categoryModdedMekanism);
        this.durabilityOsmiumShield = this.defineDurability(builder, Defaults.DefaultDurabilityOsmiumShield, "Osmium");
        this.durabilityLapisLazuliShield = this.defineDurability(builder, Defaults.DefaultDurabilityLapisLazuliShield, "Lapis Lazuli");
        this.durabilityRefinedGlowstoneShield = this.defineDurability(builder, Defaults.DefaultDurabilityRefinedGlowstoneShield, "Refined Glowstone");
        this.durabilityRefinedObsidianShield = this.defineDurability(builder, Defaults.DefaultDurabilityRefinedObsidianShield, "Refined Obsidian");
        builder.pop();
        builder.push(categoryThermalMods);
        this.durabilitySignalumShield = this.defineDurability(builder, Defaults.DefaultDurabilitySignalumShield, "Signalum");
        this.durabilityLumiumShield = this.defineDurability(builder, Defaults.DefaultDurabilityLumiumShield, "Lumium");
        this.durabilityEnderiumShield = this.defineDurability(builder, Defaults.DefaultDurabilityEnderiumShield, "Enderium");
        builder.pop();
        builder.push(categoryPowered);
        this.damageToFEMultiplier = builder.
                comment("Damage to FE Multiplier").
                translation("config." + ModSpartanShields.ID + ".damageToFEMultpier").
                defineInRange("damageToFEMultpier", Defaults.DefaultDamageToFEMultiplier, 0, 1000000);
        builder.pop();
    }

    private IntValue defineDurability(ForgeConfigSpec.Builder builder, int defaultDurability, String name) {
        IntValue durability;
        String nameLower = name.toLowerCase().replace(' ', '_');
        builder.push(nameLower);
        durability = builder.
                comment("Durability for " + name + " Shields").
                translation("config." + ModSpartanShields.ID + ".durability").
                defineInRange("durability", defaultDurability, 0, 100000);
        builder.pop();
        return durability;
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent ev) {
        Log.info("Loading config " + ev.getConfig().getFileName());
        if (ev.getConfig().getSpec() == CONFIG_SPEC) {
            Log.debug("Reloading durability values for Shields!");
            updateMaxDurability(ModItems.WOODEN_BASIC_SHIELD.get(), ModItems.WOODEN_TOWER_SHIELD.get(), INSTANCE.durabilityWoodShield.get());
            updateMaxDurability(ModItems.STONE_BASIC_SHIELD.get(), ModItems.STONE_TOWER_SHIELD.get(), INSTANCE.durabilityStoneShield.get());
            updateMaxDurability(ModItems.COPPER_BASIC_SHIELD.get(), ModItems.COPPER_TOWER_SHIELD.get(), INSTANCE.durabilityCopperShield.get());
            updateMaxDurability(ModItems.IRON_BASIC_SHIELD.get(), ModItems.IRON_TOWER_SHIELD.get(), INSTANCE.durabilityIronShield.get());
            updateMaxDurability(ModItems.GOLDEN_BASIC_SHIELD.get(), ModItems.GOLDEN_TOWER_SHIELD.get(), INSTANCE.durabilityGoldShield.get());
            updateMaxDurability(ModItems.DIAMOND_BASIC_SHIELD.get(), ModItems.DIAMOND_TOWER_SHIELD.get(), INSTANCE.durabilityDiamondShield.get());
            updateMaxDurability(ModItems.NETHERITE_BASIC_SHIELD.get(), ModItems.NETHERITE_TOWER_SHIELD.get(), INSTANCE.durabilityNetheriteShield.get());
            updateMaxDurability(ModItems.OBSIDIAN_BASIC_SHIELD.get(), ModItems.OBSIDIAN_TOWER_SHIELD.get(), INSTANCE.obsidian.getDurability());

            updateMaxDurability(ModItems.TIN_BASIC_SHIELD.get(), ModItems.TIN_TOWER_SHIELD.get(), INSTANCE.tin.getDurability());
            updateMaxDurability(ModItems.BRONZE_BASIC_SHIELD.get(), ModItems.BRONZE_TOWER_SHIELD.get(), INSTANCE.bronze.getDurability());
            updateMaxDurability(ModItems.STEEL_BASIC_SHIELD.get(), ModItems.STEEL_TOWER_SHIELD.get(), INSTANCE.steel.getDurability());
            updateMaxDurability(ModItems.SILVER_BASIC_SHIELD.get(), ModItems.SILVER_TOWER_SHIELD.get(), INSTANCE.silver.getDurability());
            updateMaxDurability(ModItems.ELECTRUM_BASIC_SHIELD.get(), ModItems.ELECTRUM_TOWER_SHIELD.get(), INSTANCE.electrum.getDurability());
            updateMaxDurability(ModItems.LEAD_BASIC_SHIELD.get(), ModItems.LEAD_TOWER_SHIELD.get(), INSTANCE.lead.getDurability());
            updateMaxDurability(ModItems.NICKEL_BASIC_SHIELD.get(), ModItems.NICKEL_TOWER_SHIELD.get(), INSTANCE.nickel.getDurability());
            updateMaxDurability(ModItems.INVAR_BASIC_SHIELD.get(), ModItems.INVAR_TOWER_SHIELD.get(), INSTANCE.invar.getDurability());
            updateMaxDurability(ModItems.CONSTANTAN_BASIC_SHIELD.get(), ModItems.CONSTANTAN_TOWER_SHIELD.get(), INSTANCE.constantan.getDurability());
            updateMaxDurability(ModItems.PLATINUM_BASIC_SHIELD.get(), ModItems.PLATINUM_TOWER_SHIELD.get(), INSTANCE.platinum.getDurability());
            updateMaxDurability(ModItems.ALUMINUM_BASIC_SHIELD.get(), ModItems.ALUMINUM_TOWER_SHIELD.get(), INSTANCE.aluminum.getDurability());

            updateMaxDurability(ModItems.MANASTEEL_BASIC_SHIELD.get(), ModItems.MANASTEEL_TOWER_SHIELD.get(), INSTANCE.durabilityManasteelShield.get());
            updateMaxDurability(ModItems.TERRASTEEL_BASIC_SHIELD.get(), ModItems.TERRASTEEL_TOWER_SHIELD.get(), INSTANCE.durabilityTerrasteelShield.get());
            updateMaxDurability(ModItems.ELEMENTIUM_BASIC_SHIELD.get(), ModItems.ELEMENTIUM_TOWER_SHIELD.get(), INSTANCE.durabilityElementiumShield.get());

            updateMaxDurability(ModItems.OSMIUM_BASIC_SHIELD.get(), null, INSTANCE.durabilityOsmiumShield.get());
            updateMaxDurability(ModItems.LAPIS_BASIC_SHIELD.get(), null, INSTANCE.durabilityLapisLazuliShield.get());
            updateMaxDurability(ModItems.REFINED_GLOWSTONE_BASIC_SHIELD.get(), null, INSTANCE.durabilityRefinedGlowstoneShield.get());
            updateMaxDurability(ModItems.REFINED_OBSIDIAN_BASIC_SHIELD.get(), null, INSTANCE.durabilityRefinedObsidianShield.get());

            updateMaxDurability(ModItems.SIGNALUM_BASIC_SHIELD.get(), ModItems.SIGNALUM_TOWER_SHIELD.get(), INSTANCE.durabilitySignalumShield.get());
            updateMaxDurability(ModItems.LUMIUM_BASIC_SHIELD.get(), ModItems.LUMIUM_TOWER_SHIELD.get(), INSTANCE.durabilityLumiumShield.get());
            updateMaxDurability(ModItems.ENDERIUM_BASIC_SHIELD.get(), ModItems.ENDERIUM_TOWER_SHIELD.get(), INSTANCE.durabilityEnderiumShield.get());

            // Update disable list for shields that can be disabled
            TypeDisabledCondition.clear();
            TypeDisabledCondition.updateType(TypeDisabledCondition.MODDED, INSTANCE.vanillaOnly.get());
            ImmutableList.of(INSTANCE.obsidian, INSTANCE.tin, INSTANCE.bronze, INSTANCE.steel, INSTANCE.silver, INSTANCE.electrum, INSTANCE.lead,
                    INSTANCE.nickel, INSTANCE.invar, INSTANCE.constantan, INSTANCE.platinum, INSTANCE.aluminum).forEach(ShieldValues::updateFromConfig);
        }
    }

    public static void updateMaxDurability(ShieldBaseItem basicShieldItem, ShieldBaseItem towerShieldItem, int value) {
        if (basicShieldItem != null)
            basicShieldItem.setMaxDamage(value);
        if (towerShieldItem != null)
            towerShieldItem.setMaxDamage(value);
    }
}
