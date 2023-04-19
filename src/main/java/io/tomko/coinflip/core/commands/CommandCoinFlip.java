package io.tomko.coinflip.core.commands;

import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.Modules.Economy.CMIEconomyAcount;
import io.pelican.bird.framework.command.ICommand;
import io.pelican.bird.utils.UBirdMath;
import io.pelican.paperbird.framework.command.PCommand;
import io.tomko.coinflip.CoinFlip;
import io.tomko.coinflip.core.ServerFlip;
import io.tomko.coinflip.core.configs.ConfigMessages;
import io.tomko.coinflip.core.configs.ConfigPermissions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Essential Command, used for actual coin flipping
 */
@ICommand(name = "coinflip",namespace = "coinflip",mainCommand = "cfc",alias = {"cf",})
public class CommandCoinFlip extends PCommand {
    public CommandCoinFlip() {
        super("coinflip");
    }

    @Override
    public String getPermission() {
        return ConfigPermissions.permission("command","coinflip");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(!ConfigPermissions.permitted(sender,"command","coinflip") || !(sender instanceof Player player)){
            sender.sendMessage(ConfigMessages.message("insufficientPermissions"));
            return false;
        }
        if(args.length == 0){
            sender.sendMessage(ConfigMessages.message("argumentExpected"));
            return false;
        }

        if(args.length == 1){
            //FLIP AGAINST PROGRAM
            if (!UBirdMath.isNumeric(args[0])) {
                sender.sendMessage(ConfigMessages.message("NaN"));
                return false;
            }

            int number = Integer.parseInt(args[0]);
            if(!CMIUser.getUser(player).getEconomyAccount().has(number)){
                sender.sendMessage(ConfigMessages.message("insufficientFunds"));
                return false;
            }

            ServerFlip flip = new ServerFlip(player,number);

            boolean success = flip.flip();

            Component componentTitle = ConfigMessages.message("coin","thrown-title");
            Component componentSubtitle = ConfigMessages.message("coin","thrown-subtitle");

            Title.Times times = Title.Times.times(Duration.of(300, ChronoUnit.MILLIS),
                    Duration.of(3,ChronoUnit.SECONDS),Duration.of(300,ChronoUnit.MILLIS));

            Title title = Title.title(componentTitle,componentSubtitle, times);
            player.showTitle(title);

            Title wonTitle = Title.title(
                    ConfigMessages.message("coin","won-title"),
                    ConfigMessages.message("coin","won-subtitle"),
                    times
            );
            Title lostTitle = Title.title(
                    ConfigMessages.message("coin","lost-title"),
                    ConfigMessages.message("coin","lost-subtitle"),
                    times
            );

            Bukkit.getScheduler().runTaskLater(CoinFlip.instance(), new Runnable() {
                @Override
                public void run() {
                    if(success){
                        player.showTitle(wonTitle);
                        CMIUser user = CMIUser.getUser(player);
                        CMIEconomyAcount account = user.getEconomyAccount();
                        account.setBalance(account.getBalance() + flip.amount() * 2);
                        account.updateBalTopPosition();
                        return;
                    }
                    player.showTitle(lostTitle);
                    CMIUser user = CMIUser.getUser(player);
                    CMIEconomyAcount account = user.getEconomyAccount();
                    account.setBalance(account.getBalance() - flip.amount());
                    account.updateBalTopPosition();
                }
            },60L);

            return false;
        }

        return true;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return CoinFlip.instance();
    }
}
