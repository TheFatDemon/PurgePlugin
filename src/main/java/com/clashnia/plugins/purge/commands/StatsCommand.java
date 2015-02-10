package com.clashnia.plugins.purge.commands;

import com.clashnia.plugins.purge.PurgePlugin;
import com.clashnia.plugins.purge.models.PlayerModel;
import com.clashnia.plugins.purge.utils.PlayerModelUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    private final PurgePlugin plugin;

    public StatsCommand(PurgePlugin purgePlugin) {
        this.plugin = purgePlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            return false;
        }
        Player player = (Player) commandSender;

        PlayerModelUtil playerModelUtil = new PlayerModelUtil(plugin);
        PlayerModel playerModel = playerModelUtil.getRecord(player);
        if (playerModel == null){
            player.sendMessage(ChatColor.GOLD + "[PURGE] System Error");
            return true;
        }
        if (player.getGameMode() == GameMode.SPECTATOR || playerModel.isDead == 1){
            player.sendMessage(ChatColor.GOLD + "[PURGE] " + ChatColor.RED + "You Died Bro.");
            player.setGameMode(GameMode.SPECTATOR);
            return true;
        }
        player.sendMessage(ChatColor.GOLD + "[PURGE]");
        player.sendMessage(ChatColor.GOLD + "[PURGE] " + ChatColor.RED + "Kills: " + playerModel.killCount);
        player.sendMessage(ChatColor.GOLD + "[PURGE] " + ChatColor.RED + "Relogs: " + playerModel.relogCount);
        player.sendMessage(ChatColor.GOLD + "[PURGE]");
        return true;
    }
}
