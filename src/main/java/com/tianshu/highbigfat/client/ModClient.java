package com.tianshu.highbigfat.client;

import com.tianshu.highbigfat.HighBigFatMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

/**
 * Client-only setup. Entity renderers and FOV effects land here in later phases.
 */
@Mod.EventBusSubscriber(modid = HighBigFatMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClient {
    private static final Logger LOGGER = LogUtils.getLogger();

    private ModClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.debug("HighBigFat client setup complete");
    }
}
