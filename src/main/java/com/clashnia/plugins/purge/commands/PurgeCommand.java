package com.clashnia.plugins.purge.commands;

import com.clashnia.plugins.purge.PurgePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PurgeCommand implements CommandExecutor {
    private final PurgePlugin plugin;

    public PurgeCommand(PurgePlugin purgePlugin) {
        this.plugin = purgePlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.RED + "Nope.");
            return true;
        }

        Player player = (Player) commandSender;


        return true;
    }
}
