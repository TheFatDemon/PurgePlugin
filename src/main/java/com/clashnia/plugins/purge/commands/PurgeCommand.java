package com.clashnia.plugins.purge.commands;

import com.clashnia.plugins.purge.PurgePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PurgeCommand implements CommandExecutor {
    private final PurgePlugin plugin;

    public PurgeCommand(PurgePlugin purgePlugin) {
        this.plugin = purgePlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return true;
    }
}
