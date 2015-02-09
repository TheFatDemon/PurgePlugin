package com.clashnia.plugins.purge.runnables;

import com.clashnia.plugins.purge.PurgePlugin;
import com.clashnia.plugins.purge.utils.PlayerModelUtil;
import org.bukkit.entity.Player;

public class ScoreBoardUpdater implements Runnable {

    private final PurgePlugin plugin;

    public ScoreBoardUpdater(PurgePlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getServer().getOnlinePlayers().size() != 0){
            for (Player player : plugin.getServer().getOnlinePlayers()){
                PlayerModelUtil playerModelUtil = new PlayerModelUtil(plugin);
                playerModelUtil.updateScoreBoard(player);
            }
        }
    }
}
