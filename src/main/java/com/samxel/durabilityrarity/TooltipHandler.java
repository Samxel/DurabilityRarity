package com.samxel.durabilityrarity;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DurabilityRarity.MODID, value = Dist.CLIENT)
public class TooltipHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.hasTag() && stack.getTag().contains("rarity_class")) {
            String rarityName = stack.getTag().getString("rarity_class");
            RarityUtil.RarityClass rarity = RarityUtil.RarityClass.valueOf(rarityName);

            Component rarityComponent = Component.literal(getRarityDisplayName(rarity))
                .withStyle(getRarityColor(rarity));

            event.getToolTip().add(1, rarityComponent);
        }
    }

    private static String getRarityDisplayName(RarityUtil.RarityClass rarity) {
        return switch (rarity) {
            case BROKEN -> "Broken";
            case DAMAGED -> "Damaged";
            case COMMON -> "Common";
            case RARE -> "Rare";
            case EPIC -> "Epic";
            case LEGENDARY -> "Legendary";
        };
    }

    private static ChatFormatting getRarityColor(RarityUtil.RarityClass rarity) {
        return switch (rarity) {
            case BROKEN -> ChatFormatting.DARK_GRAY;
            case DAMAGED -> ChatFormatting.GRAY;
            case COMMON -> ChatFormatting.WHITE;
            case RARE -> ChatFormatting.AQUA;
            case EPIC -> ChatFormatting.LIGHT_PURPLE;
            case LEGENDARY -> ChatFormatting.GOLD;
        };
    }
}