package com.lairon.plugin.playertitles.loader;

import com.lairon.plugin.playertitles.PlayerTitles;
import com.lairon.plugin.playertitles.title.Title;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class PlayerDataLoader {

    private File configFile;
    private FileConfiguration config;
    private PlayerTitles main;

    public PlayerDataLoader(File configFile, PlayerTitles main) {
        this.configFile = configFile;
        this.main = main;
        if(!configFile.exists()) main.saveResource(configFile.getName(), true);
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public String getPlayerCurrentTitleID(Player player){
        reloadConfiguration();
        return config.getString("players." + player.getUniqueId().toString());
    }

    public void savePlayerCurrentTitle(Player player, Title title){
        config.set("players." + player.getUniqueId().toString(), title == null ? null : title.getId());
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadConfiguration(){
        try {
            config.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
