package com.samxel.durabilityrarity.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Item.class)
public class ItemBarWidthMixin {
    /**
     * @author Samxel
     * @reason Custom durability bar for NBT max durability
     */
    @Overwrite
    public int getBarWidth(ItemStack stack) {
        int max = stack.getItem().getMaxDamage();
        if (stack.hasTag() && stack.getTag().contains("rarity_max_durability")) {
            max = stack.getTag().getInt("rarity_max_durability");
        }
        int damage = stack.getDamageValue();
        return Math.round(13.0F - (float) damage * 13.0F / (float) max);
    }
}