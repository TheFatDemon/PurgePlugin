package com.clashnia.plugins.purge;

import com.clashnia.plugins.purge.commands.PurgeCommand;
import com.clashnia.plugins.purge.listeners.PlayerListener;
import com.clashnia.plugins.purge.runnables.ScoreBoardUpdater;
import com.iciql.Db;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.logging.Logger;

public class PurgePlugin extends JavaPlugin {
    private static PurgePlugin INST;
    private Logger logger = getServer().getLogger();

    @Override
    public void onEnable(){
        INST = this;
        logger.info("Begin Enable of PurgePlugin");

        // Create Default configs
        getConfig().addDefault("mysql.username", "username");
        getConfig().addDefault("mysql.database", "database");
        getConfig().addDefault("mysql.password", "password");
        getConfig().addDefault("mysql.host", "localhost");
        getConfig().addDefault("mysql.port", 3306);
        getConfig().addDefault("configured", false);
        getConfig().options().copyDefaults(true);
        // This is done to allow owner of server to configure the Database
        if (!getConfig().getBoolean("configured")){
            saveConfig(); // Save the Config before shutting down.
            logger.info("Shutting Server Down to allow setup of MySQL.");
            getServer().getPluginManager().disablePlugin(this);
            getServer().shutdown();
            return;
        }

        // Register Listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        // Register Commands
        // Whenever a command is registered, make sure to add it in plugin.yml
        getCommand("purge").setExecutor(new PurgeCommand(this));

        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new ScoreBoardUpdater(this), 120L, 120L);

        logger.info("PurgePlugin Enabled.");
    }

    @Override
    public void onDisable(){
        logger.info("Purge Plugin Disabling.");
    }

    public static PurgePlugin getInstance(){
        return INST;
    }

    public Db getDB(){
        FileConfiguration config = getConfig();
        return Db.open("mysql://" + config.getString("mysql.host") + ":" + config.getString("mysql.port") + "/" + config.getString("mysql.database") , config.getString("mysql.username"), config.getString("mysql.password"));
    }
}
