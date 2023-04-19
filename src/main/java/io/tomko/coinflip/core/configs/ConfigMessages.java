package io.tomko.coinflip.core.configs;

import io.pelican.bird.framework.configuration.IConfig;
import io.pelican.paperbird.framework.configuration.PConfig;
import io.pelican.paperbird.utils.PBirdMessages;
import io.tomko.coinflip.CoinFlip;
import net.kyori.adventure.text.Component;

/**
 * Config that stores UI messages
 */
@IConfig(name = "messages",folder = "core",behaviours = 1)
public class ConfigMessages extends PConfig {
    public ConfigMessages() {
        super(CoinFlip.instance().getDataFolder());

        write("insufficientPermissions","<newline><#fb0c0c>⌇<#fb1717>⌇ <#e61000>Na toto nemáš dostatečná oprávnění.<newline>");
        write("argumentExpected","<newline><#fb0c0c>⌇<#fb1717>⌇ <#e61000>Byl očekáván parametr.<newline>");
        write("NaN","<newline><#fb0c0c>⌇<#fb1717>⌇ <#e61000>Zadaný parametr není číslo.<newline>");
        write("insufficientFunds","<newline><#fb0c0c>⌇<#fb1717>⌇ <#e61000>Nemáš dostatečný počet peněz.<newline>");

        write("coin","thrown-title","<#fb0c0c>⌇<#fb1717>⌇ <white><bold>ᴍɪɴᴄᴇ ᴠʀᴢᴇɴᴀ</bold></white> <#fb0c0c>⌇<#fb1717>⌇ ");
        write("coin","thrown-subtitle","");

        write("coin","lost-title","<#fb0c0c>⌇<#fb1717>⌇ <#e61000><bold>ᴘʀᴏʜʀᴀ</bold><#fb0c0c>⌇<#fb1717>⌇ ");
        write("coin","lost-subtitle","<#fb1717>Bohužel jsi prohrál..");

        write("coin","won-title","<#fb0c0c>⌇<#fb1717>⌇ <#81e600><bold>ᴠʏʜʀᴀ</bold> <#fb0c0c>⌇<#fb1717>⌇ ");
        write("coin","won-subtitle","<#81e600>Gratulujeme!");
    }

    private static ConfigMessages instance;
    public static ConfigMessages instance(){
        if(instance == null) instance = new ConfigMessages();
        return instance;
    }

    public static Component message(String section,String path){
        return PBirdMessages.color(instance().getYamlConfig().getConfigurationSection(section).getString(path));
    }
    public static Component message(String path){
        return PBirdMessages.color(instance().getYamlConfig().getString(path));
    }
}
