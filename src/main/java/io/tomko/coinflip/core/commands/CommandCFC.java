package io.tomko.coinflip.core.commands;

import io.pelican.bird.framework.command.ICommand;
import io.pelican.paperbird.framework.command.PCommand;
import io.pelican.paperbird.framework.command.PCommandMain;
import io.tomko.coinflip.CoinFlip;
import io.tomko.coinflip.core.configs.ConfigMessages;
import io.tomko.coinflip.core.configs.ConfigPermissions;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Main Command
 */
@ICommand(name = "cfc",namespace = "coinflip",mainCommand = "cfc")
public class CommandCFC extends PCommand {
    public CommandCFC() {
        super("cfc");
    }

    @Override
    public String getPermission() {
        return ConfigPermissions.permission("command","main");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(!ConfigPermissions.permitted(sender,"command","main")){
            sender.sendMessage(ConfigMessages.message("insufficientPermissions"));
            return false;
        }

        return PCommandMain.execute(this,sender,args);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if(!ConfigPermissions.permitted(sender,"command","main"))
            return Collections.emptyList();
        return PCommandMain.tabComplete(this,sender,args);
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return CoinFlip.instance();
    }
}
