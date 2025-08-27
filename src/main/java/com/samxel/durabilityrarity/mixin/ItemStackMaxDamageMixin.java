package com.samxel.durabilityrarity.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ItemStack.class, priority = 2000)
public class ItemStackMaxDamageMixin {

    @ModifyReturnValue(
            method = "getMaxDamage",
            at = @At("RETURN")
    )
    private int injectRarityMaxDurability(int original) {
        ItemStack stack = (ItemStack) (Object) this;
        if (stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("rarity_max_durability")) {
                return tag.getInt("rarity_max_durability");
            }
        }
        return original;
    }
}