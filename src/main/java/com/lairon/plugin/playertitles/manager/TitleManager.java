package com.lairon.plugin.playertitles.manager;

import com.lairon.plugin.playertitles.PlayerTitles;
import com.lairon.plugin.playertitles.registered.RegisteredTitles;
import com.lairon.plugin.playertitles.command.TitleCommandExecutor;
import com.lairon.plugin.playertitles.loader.PlayerDataLoader;
import com.lairon.plugin.playertitles.loader.TitleLoader;
import com.lairon.plugin.playertitles.placeholder.TitlePlaceholder;
import com.lairon.plugin.playertitles.title.Title;
import com.lairon.plugin.playertitles.view.InventoryViewListener;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.io.File;

public class TitleManager {
    @Getter
    private PlayerTitles main;
    @Getter
    private RegisteredTitles registeredTitles = new RegisteredTitles();
    private TitleLoader titleLoader;
    @Getter
    private PlayerDataLoader playerDataLoader;
    @Getter
    private TitlePlaceholder titlePlaceholder;
    @Getter
    private static NamespacedKey commandKey;

    public TitleManager(@NonNull PlayerTitles main) {
        this.main = main;
        commandKey = new NamespacedKey(main, "title_command");
        titleLoader = new TitleLoader(new File(main.getDataFolder() + File.separator + "Titles.yml"), main);
        playerDataLoader = new PlayerDataLoader(new File(main.getDataFolder() + File.separator + "PlayerData.yml"), main);
        reload();
        Bukkit.getPluginCommand("titul").setExecutor(new TitleCommandExecutor(this));
        titlePlaceholder = new TitlePlaceholder(this);
        titlePlaceholder.register();
        Bukkit.getPluginManager().registerEvents(new InventoryViewListener(), main);
    }

    public void reload(){
        registeredTitles.clear();

        for (Title title : titleLoader.loadAllTitles()) {
            registeredTitles.registerTitle(title);
        }
    }

    public void disable(){
        titlePlaceholder.unregister();
    }


}
