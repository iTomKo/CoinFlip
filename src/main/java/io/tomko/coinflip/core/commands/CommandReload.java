package io.tomko.coinflip.core.commands;

import io.pelican.bird.framework.command.ICommand;
import io.pelican.paperbird.framework.command.PCommand;
import io.pelican.paperbird.utils.PBirdMessages;
import io.tomko.coinflip.CoinFlip;
import io.tomko.coinflip.core.configs.ConfigMessages;
import io.tomko.coinflip.core.configs.ConfigPermissions;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Command meant only for server operators to reload configuration files.
 */
@ICommand(name = "reload",namespace = "coinflip",standAlone = false,mainCommand = "cfc")
public class CommandReload extends PCommand {
    public CommandReload() {
        super("reload");
    }

    @Override
    public String getPermission() {
        return ConfigPermissions.permission("reload");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(!ConfigPermissions.permitted(sender,"reload")){
            sender.sendMessage(ConfigMessages.message("insufficientPermissions"));
            return false;
        }
        sender.sendMessage(PBirdMessages.orangeMessage("Reloading.."));
        CoinFlip.instance().reload().whenComplete((success,throwable) -> {
            if(throwable != null){
                throwable.printStackTrace();
                sender.sendMessage(PBirdMessages.redMessage("An error occurred!"));
                return;
            }

            if(success)
                sender.sendMessage(PBirdMessages.greenMessage("Reloaded!"));
            else sender.sendMessage(PBirdMessages.redMessage("Failed to reload!"));
        });

        return true;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return CoinFlip.instance();
    }
}
