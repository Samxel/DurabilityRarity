package com.samxel.durabilityrarity;

import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

public class RarityUtil {

    private static final Logger LOGGER = LogUtils.getLogger();

    public enum RarityClass {
        BROKEN,
        DAMAGED,
        COMMON,
        RARE,
        EPIC,
        LEGENDARY
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide()) return;
        for (ItemStack stack : player.getInventory().items) {
            ensureRarity(stack, player.getRandom(), player);
        }
    }

    public static void ensureRarity(ItemStack stack, RandomSource random, Player player) {
        if (stack.isDamageableItem() &&
                (!stack.hasTag() || !stack.getTag().contains("rarity_class"))) {
            RarityClass rarity = getRandomRarity(random);
            applyRarity(stack, rarity, player);
        }
    }

    public static RarityClass getRandomRarity(RandomSource random) {
        int roll = random.nextInt(100) + 1;
        int cumulative = 0;
        for (RarityClass rarity : RarityClass.values()) {
            cumulative += Config.getChance(rarity);
            if (roll <= cumulative) return rarity;
        }
        return RarityClass.COMMON;
    }

    public static void applyRarity(ItemStack stack, RarityClass rarity, Player player) {
        double multiplier = Config.getMultiplier(rarity);
        int baseMaxDurability = stack.getMaxDamage();
        int newMax = (int) Math.round(baseMaxDurability * multiplier);

        CompoundTag tag = stack.getOrCreateTag();
        tag.putString("rarity_class", rarity.name());
        tag.putInt("rarity_max_durability", newMax);

        int currentDamage = stack.getDamageValue();
        int newDamage = Math.min(currentDamage, newMax - 1);
        stack.setDamageValue(newDamage);

        LOGGER.debug(
                "Assigned rarity [{}] to item [{}] for player [{}] (new max durability: {}, old max: {}, current damage: {})",
                rarity.name(),
                stack.getDisplayName().getString(),
                player != null ? player.getName().getString() : "unknown",
                newMax,
                baseMaxDurability,
                currentDamage
        );
    }
}