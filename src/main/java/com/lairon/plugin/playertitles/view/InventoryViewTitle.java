package com.lairon.plugin.playertitles.view;

import com.lairon.plugin.playertitles.PlayerTitles;
import com.lairon.plugin.playertitles.manager.TitleManager;
import com.lairon.plugin.playertitles.registered.RegisteredTitles;
import com.lairon.plugin.playertitles.title.Title;
import com.lairon.utils.ItemStackBuilder;
import com.lairon.utils.LoadingGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryViewTitle implements InventoryHolder {

    private Player player;
    private int page;
    private RegisteredTitles registeredTitles;

    public InventoryViewTitle(Player player, int page, RegisteredTitles registeredTitles) {
        this.player = player;
        this.page = page;
        this.registeredTitles = registeredTitles;
    }

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this, 54, "Титулы, стр№" + (page + 1));
    }

    public void openWithLoading(){
        Inventory inventory = getInventory();
        LoadingGUI loadingGUI = new LoadingGUI();
        player.openInventory(loadingGUI.getInventory());

        Bukkit.getScheduler().runTaskAsynchronously(PlayerTitles.getInstance(), ()->{
            List<Title> titles = new ArrayList<>();
            for (Title title : registeredTitles.getAllTitles()) {
                if(player.hasPermission(title.getPermission())) titles.add(title);
            }
            titles = titles.stream().skip(44 * page).limit(44).sorted(Comparator.comparing(Title::getId)).collect(Collectors.toList());
            for (int i = 0; i < 44; i++) {
                if(titles.size() <= i) break;
                inventory.setItem(i, titles.get(i).getItem());
                loadingGUI.setProgress(i / 44d);
            }

            ItemStack reset = new ItemStackBuilder(Material.BARRIER)
                    .name("§cСбросить титул")
                    .data(TitleManager.getCommandKey(), PersistentDataType.STRING, "/titul reset")
                    .build();
            inventory.setItem(49, reset);
            
            Bukkit.getScheduler().runTask(PlayerTitles.getInstance(), ()->{
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                player.openInventory(inventory);
            });
        });

    }

}
