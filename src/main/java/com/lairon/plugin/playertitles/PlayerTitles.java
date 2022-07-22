package com.lairon.plugin.playertitles;

import com.lairon.plugin.playertitles.lang.TitleLang;
import com.lairon.plugin.playertitles.manager.TitleManager;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerTitles extends JavaPlugin {

    private TitleManager titleManager;
    private static PlayerTitles instance;
    @Override
    public void onEnable() {
        instance = this;
        titleManager = new TitleManager(this);
        TitleLang.reload();
    }

    public static PlayerTitles getInstance() {
        return instance;
    }
    
    @Override
    public void onDisable() {
        titleManager.disable();
    }
}
