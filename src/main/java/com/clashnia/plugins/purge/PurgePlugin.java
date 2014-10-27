package com.clashnia.plugins.purge;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PurgePlugin extends JavaPlugin {
    @Override
    public void onEnable(){
        Logger logger = getServer().getLogger();
        logger.info("Begin Enable of PurgePlugin");
    }
    @Override
    public void onDisable(){

    }
}
