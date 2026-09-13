package com.tianshu.highbigfat;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

/**
 * Common config placeholders for HighBigFat gameplay toggles.
 * Values are wired for Phase 1+ systems (spawning, merit caps, rank gates).
 */
@Mod.EventBusSubscriber(modid = HighBigFatMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue ENABLE_ENEMY_SPAWNING = BUILDER
            .comment("Master switch for HighBigFat threat-tier enemy natural spawning.")
            .define("enableEnemySpawning", true);

    private static final ForgeConfigSpec.IntValue DAILY_MERIT_CAP = BUILDER
            .comment("Max merit a player can gain from small kills per Minecraft day. Warrant / drill bonuses may bypass this later.")
            .defineInRange("dailyMeritCap", 40, 0, 10_000);

    private static final ForgeConfigSpec.BooleanValue ENABLE_RANK_SPAWN_GATES = BUILDER
            .comment("When true, higher threat tiers do not naturally spawn until the player reaches the matching rank.")
            .define("enableRankSpawnGates", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean enableEnemySpawning = true;
    public static int dailyMeritCap = 40;
    public static boolean enableRankSpawnGates = true;

    private Config() {
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        enableEnemySpawning = ENABLE_ENEMY_SPAWNING.get();
        dailyMeritCap = DAILY_MERIT_CAP.get();
        enableRankSpawnGates = ENABLE_RANK_SPAWN_GATES.get();
    }
}
