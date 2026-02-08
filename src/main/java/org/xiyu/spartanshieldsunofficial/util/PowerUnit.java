package org.xiyu.spartanshieldsunofficial.util;

public enum PowerUnit {
    RedstoneFlux("rf_capacity", "rf_per_damage", "rf_charge_rate", 1.0f),
    ForgeEnergy("fe_capacity", "fe_per_damage", "fe_charge_rate", 1.0f),
    MicroInfinity("ui_capacity", "ui_per_damage", "ui_charge_rate", 1.0f);


    private final String capUnloc;
    private final String enPerDamUnloc;
    private final String enChargeRate;
    private final float scale;

    PowerUnit(String capacityUnloc, String energyPerDamageUnloc, String energyChargeRate, float powerScale) {
        this.capUnloc = capacityUnloc;
        this.enPerDamUnloc = energyPerDamageUnloc;
        this.enChargeRate = energyChargeRate;
        this.scale = powerScale;
    }

    public String getCapacityTranslationKey() {
        return this.capUnloc;
    }

    public String getEnergyPerDamageTranslationKey() {
        return this.enPerDamUnloc;
    }

    public String getEnergyChargeRateTranslationKey() {
        return this.enChargeRate;
    }

    public float getEnergyScaleToFE() {
        return this.scale;
    }
}
