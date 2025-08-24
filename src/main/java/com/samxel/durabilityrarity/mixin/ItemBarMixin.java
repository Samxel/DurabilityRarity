package com.samxel.durabilityrarity.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(value = Item.class, priority = 2000)
public class ItemBarMixin {

    @ModifyReturnValue(
            method = "getBarWidth",
            at = @At("RETURN")
    )
    private int modifyBarWidth(int original, ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("rarity_max_durability")) {
                int max = tag.getInt("rarity_max_durability");
                int damage = stack.getDamageValue();
                return Math.round(13.0F - (float) damage * 13.0F / (float) max);
            }
        }
        return original;
    }

    @ModifyReturnValue(
            method = "getBarColor",
            at = @At("RETURN")
    )
    private int modifyBarColor(int original, ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("rarity_max_durability")) {
                int max = tag.getInt("rarity_max_durability");
                int damage = stack.getDamageValue();
                float f = Math.max(0.0F, ((float) max - (float) damage) / (float) max);
                return net.minecraft.util.Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
            }
        }
        return original;
    }
}