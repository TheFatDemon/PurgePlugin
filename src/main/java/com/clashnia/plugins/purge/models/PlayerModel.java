package com.clashnia.plugins.purge.models;

import com.iciql.Iciql.*;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.Calendar;

@IQTable
public class PlayerModel {
    /*
        The IQColumns and the IQTable just tell Iciql what is a column in the database
        and what is a table. This is based off of SQL Databases but for our purposes
        we will be focusing on MySQL
     */

    // Player's UUID in a String Format
    @IQColumn(length = 36, primaryKey = true)
    public String playerUUID;

    // Player Name
    @IQColumn(length = 25)
    public String playerName;

    // Kill Count
    @IQColumn
    public int killCount;

    // Relog Count
    @IQColumn
    public int relogCount;

    // Is Dead?
    @IQColumn
    public int isDead;

    // Last Time the person logged in.
    @IQColumn
    public Timestamp lastLoggedIn;

    // Default constructor
    public PlayerModel(){
    }

    // Constructor for Creating a New Player
    public PlayerModel(Player player){

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();

        this.playerUUID = player.getUniqueId().toString();
        this.playerName = player.getName();
        this.killCount = 0;
        this.relogCount = 0;
        this.isDead = 0;
        this.lastLoggedIn = new Timestamp(now.getTime());
    }
}
