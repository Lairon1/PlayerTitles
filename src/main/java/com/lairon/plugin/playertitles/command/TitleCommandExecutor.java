package com.lairon.plugin.playertitles.command;

import com.lairon.plugin.playertitles.lang.TitleLang;
import com.lairon.plugin.playertitles.manager.TitleManager;
import com.lairon.plugin.playertitles.title.Title;
import com.lairon.plugin.playertitles.view.InventoryViewTitle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TitleCommandExecutor implements CommandExecutor {

    private TitleManager titleManager;

    public TitleCommandExecutor(TitleManager titleManager) {
        this.titleManager = titleManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("playerTitle.command.reload")){
                titleManager.reload();
                TitleLang.reload();
                sender.sendMessage(TitleLang.RELOAD.getMessage());
                return false;
            }
            if(args[0].equalsIgnoreCase("reset")){
                if(!(sender instanceof Player)){
                    sender.sendMessage(TitleLang.ONLY_PLAYER.getMessage());
                    return false;
                }
                Player player = (Player) sender;
                titleManager.getPlayerDataLoader().savePlayerCurrentTitle(player, null);
                player.sendMessage(TitleLang.RESET.getMessage());
                return false;
            }
        }

        if(args.length >= 2){
            if(args[0].equalsIgnoreCase("set")){
                if(!(sender instanceof Player)){
                    sender.sendMessage(TitleLang.ONLY_PLAYER.getMessage());
                    return false;
                }
                Player player = (Player) sender;
                String titleID = args[1];
                Title title = titleManager.getRegisteredTitles().getTitleByID(titleID);

                if(title == null){
                    player.sendMessage(TitleLang.NOT_FOUND.getMessage());
                    return false;
                }

                if(!player.hasPermission(title.getPermission())){
                    player.sendMessage(TitleLang.DONT_HAVE.getMessage());
                    return false;
                }
                titleManager.getPlayerDataLoader().savePlayerCurrentTitle(player, title);
                player.sendMessage(TitleLang.SUCCESSFULLY_SET.getMessage().replace("%TITLE%", title.getTitle()));
                return false;
            }

        }

        if(!(sender instanceof Player)){
            sender.sendMessage(TitleLang.ONLY_PLAYER.getMessage());
            return false;
        }
        Player player = (Player) sender;

        InventoryViewTitle inventoryViewTitle = new InventoryViewTitle(player, 0, titleManager.getRegisteredTitles());
        inventoryViewTitle.openWithLoading();
        return true;
    }
}
