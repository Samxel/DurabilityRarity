package com.samxel.durabilityrarity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DurabilityRarity.MODID)
public class MiningSpeedHandler {

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();

        if (stack.hasTag()) {
            assert stack.getTag() != null;
            if (stack.getTag().contains("rarity_class")) {
                String rarityName = stack.getTag().getString("rarity_class");
                RarityUtil.RarityClass rarity = RarityUtil.RarityClass.valueOf(rarityName);

                if (rarity == RarityUtil.RarityClass.LEGENDARY) {
                    double multiplier = Config.getLegendaryMiningSpeed();
                    event.setNewSpeed((float) (event.getNewSpeed() * multiplier));
                }
            }
        }
    }
}