package io.tomko.coinflip;

import io.pelican.paperbird.framework.command.PCommandManager;
import io.pelican.paperbird.framework.configuration.PConfig;
import io.pelican.paperbird.framework.configuration.PConfigManager;
import io.tomko.coinflip.core.commands.CommandCFC;
import io.tomko.coinflip.core.commands.CommandCoinFlip;
import io.tomko.coinflip.core.commands.CommandReload;
import io.tomko.coinflip.core.configs.ConfigMessages;
import io.tomko.coinflip.core.configs.ConfigPermissions;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * This plugin requires custom Dependency [PaperBird] and CMI & CMILIB
 * @author iTomKo
 */
public final class CoinFlip extends JavaPlugin {

    private static CoinFlip instance;
    public static CoinFlip instance(){
        return instance;
    }

    public static Random random;

    @Override
    public void onEnable() {
        instance = this;
        random = new Random();

        registerConfigs();
        registerCommands();
    }

    private void registerConfigs(){
        PConfigManager.register(
                ConfigPermissions.permissions(),
                ConfigMessages.instance()
        );
    }

    private static PCommandManager commandManager;
    private void registerCommands(){
        commandManager = PCommandManager.fromPlugin(this);
        commandManager.addCommand(
                new CommandCFC(),
                new CommandCoinFlip(),
                new CommandReload()
        );
        commandManager.register();
    }

    public CompletableFuture<Boolean> reload() {
        return CompletableFuture.supplyAsync(() -> {
           for(File file : Arrays.stream(getDataFolder().listFiles()).toList()){
               PConfigManager.reload(file);
           }

           return true;
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
