package com.samxel.durabilityrarity;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = DurabilityRarity.MODID)
public class AttributeHandler {

    private static final UUID LEGENDARY_ATTACK_SPEED_UUID =
            UUID.fromString("12345678-1234-5678-1234-567812345678");

    @SubscribeEvent
    public static void onItemAttributeModifiers(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.hasTag() && stack.getTag().contains("rarity_class")) {
            String rarityName = stack.getTag().getString("rarity_class");
            RarityUtil.RarityClass rarity = RarityUtil.RarityClass.valueOf(rarityName);

            if (rarity == RarityUtil.RarityClass.LEGENDARY &&
                    event.getSlotType() == EquipmentSlot.MAINHAND) {

                double multiplier = Config.getLegendarySpeed();

                double vanillaAttackSpeed = event.getOriginalModifiers()
                        .get(Attributes.ATTACK_SPEED)
                        .stream()
                        .mapToDouble(AttributeModifier::getAmount)
                        .sum();

                double effective = 4.0 + vanillaAttackSpeed;

                double bonus = effective * (multiplier - 1.0);
                Collection<AttributeModifier> vanillaMods =
                        new ArrayList<>(event.getOriginalModifiers().get(Attributes.ATTACK_SPEED));

                for (AttributeModifier mod : vanillaMods) {
                    event.removeModifier(Attributes.ATTACK_SPEED, mod);
                }

                for (AttributeModifier mod : vanillaMods) {
                    event.addModifier(Attributes.ATTACK_SPEED, mod);
                }

                event.addModifier(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                LEGENDARY_ATTACK_SPEED_UUID,
                                "Legendary attack speed bonus",
                                bonus,
                                AttributeModifier.Operation.ADDITION
                        )
                );
            }
        }
    }
}