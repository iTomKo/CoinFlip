package io.tomko.coinflip.core.configs;

import io.pelican.bird.framework.configuration.IConfig;
import io.pelican.paperbird.framework.configuration.PConfig;
import io.tomko.coinflip.CoinFlip;
import org.bukkit.command.CommandSender;

/**
 * Config used to change required permissions for actions
 */
@IConfig(name = "permissions",folder = "core",behaviours = 1)
public class ConfigPermissions extends PConfig {
    public ConfigPermissions() {
        super(CoinFlip.instance().getDataFolder());

        write("command","main","coinflip.command.main");
        write("reload","coinflip.reload");
        write("command","coinflip","coinflip.command.coinflip");
    }

    private static ConfigPermissions instance;
    public static ConfigPermissions permissions(){
        if(instance == null) instance = new ConfigPermissions();
        return instance;
    }

    public static boolean permitted(CommandSender sender, String section,String path){
        return sender.hasPermission(permissions().getYamlConfig().getConfigurationSection(section).getString(path));
    }
    public static boolean permitted(CommandSender sender, String path){
        return sender.hasPermission(permissions().getYamlConfig().getString(path));
    }

    public static String permission(String path){
        return permissions().getYamlConfig().getString(path);
    }
    public static String permission(String section, String path){
        return permissions().getYamlConfig().getConfigurationSection(section).getString(path);
    }
}
