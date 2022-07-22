package com.lairon.plugin.playertitles.loader;

import com.lairon.plugin.playertitles.PlayerTitles;
import com.lairon.plugin.playertitles.title.AnimationTitle;
import com.lairon.plugin.playertitles.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class TitleLoader {

    private File configFile;
    private FileConfiguration config;
    private PlayerTitles main;

    public TitleLoader(File configFile, PlayerTitles main) {
        this.configFile = configFile;
        this.main = main;
        if (!configFile.exists()) main.saveResource(configFile.getName(), true);
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public HashSet<Title> loadAllTitles() {
        reloadConfiguration();
        HashSet<Title> titles = new HashSet<>();
        for (String id : config.getConfigurationSection("titles").getKeys(false)) {
            List<String> animation = (List<String>) config.getList("titles." + id + ".Animation");
            if (animation != null) {
                AnimationTitle animationTitle = new AnimationTitle(
                        id,
                        ChatColor.translateAlternateColorCodes('&', config.getString("titles." + id + ".Title")
                        ), config.getString("titles." + id + ".Skull"),
                        animation, config.getInt("titles." + id + ".Speed"),
                        main);
                titles.add(animationTitle);
                continue;
            }
            Title title = new Title(
                    id,
                    ChatColor.translateAlternateColorCodes('&', config.getString("titles." + id + ".Title")),
                    config.getString("titles." + id + ".Skull"));
            titles.add(title);
        }
        return titles;
    }


    private void reloadConfiguration() {
        try {
            config.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
