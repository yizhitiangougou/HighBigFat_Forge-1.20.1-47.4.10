package com.tianshu.highbigfat;

import com.mojang.logging.LogUtils;
import com.tianshu.highbigfat.registry.ModBlocks;
import com.tianshu.highbigfat.registry.ModCreativeTabs;
import com.tianshu.highbigfat.registry.ModEntityTypes;
import com.tianshu.highbigfat.registry.ModItems;
import com.tianshu.highbigfat.registry.ModSounds;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Mod entry point: registers deferred registries and common config only.
 * Gameplay systems live under registry / item / merit / entity / event packages.
 */
@Mod(HighBigFatMod.MOD_ID)
public class HighBigFatMod {
    public static final String MOD_ID = "highbigfat_mod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public HighBigFatMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModSounds.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HighBigFat common setup (enemySpawn={}, dailyMeritCap={}, rankGates={})",
                Config.enableEnemySpawning, Config.dailyMeritCap, Config.enableRankSpawnGates);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.debug("HighBigFat server starting");
    }
}
