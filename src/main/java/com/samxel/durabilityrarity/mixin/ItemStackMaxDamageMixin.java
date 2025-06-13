package com.samxel.durabilityrarity.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMaxDamageMixin {

    @Inject(
        method = "getMaxDamage",
        at = @At("HEAD"),
        cancellable = true
    )
    private void injectRarityMaxDurability(CallbackInfoReturnable<Integer> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("rarity_max_durability")) {
                cir.setReturnValue(tag.getInt("rarity_max_durability"));
            }
        }
    }
}