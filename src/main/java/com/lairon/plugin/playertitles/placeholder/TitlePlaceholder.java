package com.lairon.plugin.playertitles.placeholder;

import com.lairon.plugin.playertitles.manager.TitleManager;
import com.lairon.plugin.playertitles.title.Title;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TitlePlaceholder extends PlaceholderExpansion {

    private TitleManager titleManager;

    public TitlePlaceholder(TitleManager titleManager) {
        this.titleManager = titleManager;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "title";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Lairon1";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        String request = "";
        if(!(offlinePlayer instanceof Player)) return request;
        Player player = (Player) offlinePlayer;
        String playerCurrentTitleID = titleManager.getPlayerDataLoader().getPlayerCurrentTitleID(player);
        if(playerCurrentTitleID == null) return request;
        Title title = titleManager.getRegisteredTitles().getTitleByID(playerCurrentTitleID);
        if(title == null){
            titleManager.getPlayerDataLoader().savePlayerCurrentTitle(player, null);
            return request;
        }
        if(!player.hasPermission(title.getPermission())){
            titleManager.getPlayerDataLoader().savePlayerCurrentTitle(player, null);
            return request;
        }
        request = title.getTitle();
        return request;
    }
}
