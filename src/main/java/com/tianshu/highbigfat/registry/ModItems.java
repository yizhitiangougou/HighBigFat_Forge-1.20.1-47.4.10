package com.tianshu.highbigfat.registry;

import com.tianshu.highbigfat.HighBigFatMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Item registrations.
 * Phase 0 registers {@link #DOG_TAG} so the creative tab is visible (empty tabs are hidden).
 */
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HighBigFatMod.MOD_ID);

    /** Placeholder until Phase 1 adds merit display behaviour. */
    public static final RegistryObject<Item> DOG_TAG = ITEMS.register("dog_tag",
            () -> new Item(new Item.Properties().stacksTo(1)));

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
