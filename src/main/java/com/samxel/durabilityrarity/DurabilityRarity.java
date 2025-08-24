package com.samxel.durabilityrarity;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(DurabilityRarity.MODID)
public class DurabilityRarity {

    public static final String MODID = "durabilityrarity";

    public DurabilityRarity() {
        MinecraftForge.EVENT_BUS.register(RarityUtil.class);
        MinecraftForge.EVENT_BUS.register(MiningSpeedHandler.class);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}