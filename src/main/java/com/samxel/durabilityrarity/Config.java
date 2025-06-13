package com.samxel.durabilityrarity;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = DurabilityRarity.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    static { BUILDER.push("multipliers"); }
    public static final ForgeConfigSpec.DoubleValue BROKEN_MULTIPLIER = BUILDER
            .defineInRange("broken", 0.3, 0.01, 10.0);
    public static final ForgeConfigSpec.DoubleValue DAMAGED_MULTIPLIER = BUILDER
            .defineInRange("damaged", 0.6, 0.01, 10.0);
    public static final ForgeConfigSpec.DoubleValue COMMON_MULTIPLIER = BUILDER
            .defineInRange("common", 1.0, 0.01, 10.0);
    public static final ForgeConfigSpec.DoubleValue RARE_MULTIPLIER = BUILDER
            .defineInRange("rare", 1.3, 0.01, 10.0);
    public static final ForgeConfigSpec.DoubleValue EPIC_MULTIPLIER = BUILDER
            .defineInRange("epic", 1.6, 0.01, 10.0);
    public static final ForgeConfigSpec.DoubleValue LEGENDARY_MULTIPLIER = BUILDER
            .defineInRange("legendary", 1.9, 0.01, 10.0);
    static { BUILDER.pop(); }

    static { BUILDER.push("chances"); }
    public static final ForgeConfigSpec.IntValue BROKEN_CHANCE = BUILDER
            .defineInRange("broken", 15, 0, 100);
    public static final ForgeConfigSpec.IntValue DAMAGED_CHANCE = BUILDER
            .defineInRange("damaged", 20, 0, 100);
    public static final ForgeConfigSpec.IntValue COMMON_CHANCE = BUILDER
            .defineInRange("common", 30, 0, 100);
    public static final ForgeConfigSpec.IntValue RARE_CHANCE = BUILDER
            .defineInRange("rare", 20, 0, 100);
    public static final ForgeConfigSpec.IntValue EPIC_CHANCE = BUILDER
            .defineInRange("epic", 10, 0, 100);
    public static final ForgeConfigSpec.IntValue LEGENDARY_CHANCE = BUILDER
            .defineInRange("legendary", 5, 0, 100);
    static { BUILDER.pop(); }

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    private static double[] multipliers = new double[6];
    private static int[] chances = new int[6];

    public static double getMultiplier(RarityUtil.RarityClass rarity) {
        return multipliers[rarity.ordinal()];
    }

    public static int getChance(RarityUtil.RarityClass rarity) {
        return chances[rarity.ordinal()];
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        multipliers[0] = BROKEN_MULTIPLIER.get();
        multipliers[1] = DAMAGED_MULTIPLIER.get();
        multipliers[2] = COMMON_MULTIPLIER.get();
        multipliers[3] = RARE_MULTIPLIER.get();
        multipliers[4] = EPIC_MULTIPLIER.get();
        multipliers[5] = LEGENDARY_MULTIPLIER.get();

        chances[0] = BROKEN_CHANCE.get();
        chances[1] = DAMAGED_CHANCE.get();
        chances[2] = COMMON_CHANCE.get();
        chances[3] = RARE_CHANCE.get();
        chances[4] = EPIC_CHANCE.get();
        chances[5] = LEGENDARY_CHANCE.get();
    }
}