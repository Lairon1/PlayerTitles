package com.lairon.plugin.playertitles.view;

import com.lairon.plugin.playertitles.manager.TitleManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class InventoryViewListener implements Listener {

    @EventHandler
    public void onTitleSet(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof InventoryViewTitle)) return;
        e.setCancelled(true);
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null) return;
        if (itemStack.getItemMeta() == null) return;
        if (itemStack.getItemMeta().getPersistentDataContainer() == null) return;
        String command = itemStack.getItemMeta().getPersistentDataContainer().get(TitleManager.getCommandKey(), PersistentDataType.STRING);
        if(command == null) return;
        if(command.isEmpty()) return;
        player.chat(command);
    }

}
