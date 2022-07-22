package com.lairon.plugin.playertitles.lang;

import com.lairon.plugin.playertitles.PlayerTitles;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public enum TitleLang {

    PREFIX("Prefix"),

    ONLY_PLAYER("Messages.OnlyPlayer"),
    RELOAD("Messages.Reload"),
    RESET("Messages.Reset"),
    NOT_FOUND("Messages.NotFound"),
    DONT_HAVE("Messages.DontHave"),
    SUCCESSFULLY_SET("Messages.SuccessfullySet");

    private String path;
    @Getter
    private String message;

    private static FileConfiguration config;
    private static File file = new File(PlayerTitles.getInstance().getDataFolder() + File.separator + "Lang.yml");

    TitleLang(String path) {
        this.path = path;
    }


    public static void reload() {
        if(!file.exists()) PlayerTitles.getInstance().saveResource(file.getName(), true);
        if(config == null){
            config = YamlConfiguration.loadConfiguration(file);
        }else{
            try {
                config.load(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        PREFIX.message = ChatColor.translateAlternateColorCodes('&', config.getString(PREFIX.path));

        for (TitleLang value : values()) {
            String string = config.getString(value.path);
            if(string != null)
            value.message = ChatColor.translateAlternateColorCodes('&', string.replace("%PREFIX%", PREFIX.getMessage()));
            else value.message = null;
        }
    }
}
