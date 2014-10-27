package com.clashnia.plugins.purge;

import com.clashnia.plugins.purge.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PurgePlugin extends JavaPlugin {
    @Override
    public void onEnable(){
        Logger logger = getServer().getLogger();
        logger.info("Begin Enable of PurgePlugin");
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }
    @Override
    public void onDisable(){

    }
}
