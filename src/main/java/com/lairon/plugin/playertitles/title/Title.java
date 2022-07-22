package com.lairon.plugin.playertitles.title;

import com.lairon.plugin.playertitles.PlayerTitles;
import com.lairon.plugin.playertitles.manager.TitleManager;
import com.lairon.utils.ItemStackBuilder;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Title {
    @Getter
    private String id;
    @Getter
    private String title;
    @Getter
    private String permission;
    @Getter
    private ItemStack item;


    public Title(@NonNull String id,@NonNull String title,@NonNull String skullName) {
        this.id = id;
        this.title = title;
        this.permission = "playerTitle." + id;

        ItemStack itemStack = new ItemStackBuilder(Material.PLAYER_HEAD)
                .name(title)
                .lore("", "§aНажмите чтобы установить")
                .data(TitleManager.getCommandKey(), PersistentDataType.STRING, "/titul set " + id)
                .build();
        this.item = itemStack;
    }

}
