package com.clashnia.plugins.purge.listeners;

import com.clashnia.plugins.purge.PurgePlugin;
import com.clashnia.plugins.purge.utils.PlayerModelUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

@SuppressWarnings("unused")
public class PlayerListener implements Listener {
    private final PurgePlugin plugin;

    public PlayerListener(PurgePlugin purgePlugin) {
        this.plugin = purgePlugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event){
        // Get Player
        Player player = event.getPlayer();
        // Initialize Player Util
        PlayerModelUtil playerModelUtil = new PlayerModelUtil(plugin);
        // First we check if the player is stored in the Database
        if(playerModelUtil.getRecord(player) == null){
            // If the player isn't in DB, then insert him/her in Database
            playerModelUtil.createRecord(player);
            // We set a Welcome Message
            event.setJoinMessage(ChatColor.GOLD + "[PURGE] " + player.getName() + " has joined us in The Purge For the First Time.");
        }else{
            if (playerModelUtil.getRecord(player).isDead == 1){
                // Set player to Spectator
                player.setGameMode(GameMode.SPECTATOR);
                // Allow Flying
                player.setAllowFlight(true);
                // Set to Flying
                player.setFlying(true); // This is done to prevent some issues with Spectator Mode
                // Set the Welcome Message
                event.setJoinMessage(ChatColor.GOLD + "[PURGE] " + player.getDisplayName() + " Is Watching From the DEAD");
            }else{
                // Update Relog Count
                playerModelUtil.addRelog(player, 1);
                // Set the Welcome Message
                event.setJoinMessage(ChatColor.GOLD + "[PURGE] " + player.getName() + " has Returned For MORE!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event){
        // Probably Redundant but we check if there even is a Player
        if (event.getEntity() == null){
            // If no player, then just cancel the rest.
            return;
        }
        Player player = event.getEntity();
        // Not too sure if this part will work.
        Player killer = player.getKiller();
        // We then get the Player Util. We only need one because we pass the specific player we
        // want in the arguments for each function
        PlayerModelUtil playerModelUtil = new PlayerModelUtil(plugin);
        // We then add 1 kill to the killer's record.
        playerModelUtil.addKills(killer, 1);
        // After Player Dies, We set them to Spectator Mode
        player.setGameMode(GameMode.SPECTATOR);
        // And Set them as "dead"
        playerModelUtil.setDead(player, true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        PlayerModelUtil playerModelUtil = new PlayerModelUtil(plugin);

        // If player is considered Dead
        if (playerModelUtil.getRecord(player).isDead == 1){
            // Set Them as spectator
            player.setGameMode(GameMode.SPECTATOR);
            player.setAllowFlight(true);
            player.setFlying(true);

            // Clear player's inventory
            player.getInventory().clear();

            // Simple Message
            plugin.getServer().broadcastMessage(ChatColor.GOLD + "[PURGE]" + player.getName() + " has respawned as a SPECTATOR");
            // Just to make sure, send them to spawn
            event.setRespawnLocation(player.getWorld().getSpawnLocation());
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent event){
        // Get the Player Object
        Player player = event.getPlayer();
        // Instantiate a PlayerModelUtil
        PlayerModelUtil playerModelUtil = new PlayerModelUtil(plugin);
        // Call update last login method
        playerModelUtil.updateLastLogin(player);
        // Some fun
        event.setQuitMessage(ChatColor.GOLD + player.getName() + " has left the Purge.");
    }
}
