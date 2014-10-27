package com.clashnia.plugins.purge.listeners;

import com.clashnia.plugins.purge.PurgePlugin;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener {
    private final PurgePlugin plugin;

    public PlayerListener(PurgePlugin purgePlugin) {
        this.plugin = purgePlugin;
    }
}
