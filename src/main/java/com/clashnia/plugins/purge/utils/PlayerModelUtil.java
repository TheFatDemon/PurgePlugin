package com.clashnia.plugins.purge.utils;

import com.clashnia.plugins.purge.PurgePlugin;
import com.clashnia.plugins.purge.models.PlayerModel;
import com.iciql.Db;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.Calendar;

public class PlayerModelUtil {
    private final PurgePlugin plugin;

    public PlayerModelUtil(PurgePlugin plugin){
        // Allows us to reference the main Class in here.
        this.plugin = plugin;
    }


    public PlayerModel getRecord(Player player) {
        // Refers to Main Class to get Connection
        Db db = plugin.getDB();
        // Surrounded this part by a try block because the DB always needs to run
        // db.close() each time I am done using it.
        // The 'finally' block allows me to do so before the return statement Runs.
        try {
            // We instantiate an empty PlayerModel in order to be able to select from the Database.
            PlayerModel playerModel = new PlayerModel();
            // and we just return the first Model found.
            return db.from(playerModel).where(playerModel.playerUUID).is(player.getUniqueId().toString()).selectFirst();
        }catch (Exception ignored){
            // If anything were to go wrong, then we return null.
            return null;
        }finally {
            // Closing the Connection to the Database
            db.close();
        }
    }

    public void createRecord(Player player) {
        // Create Connection
        Db db = plugin.getDB();
        // Create a basic Player model, this one is different from the one used above because
        // we passed 'player' as a paraeter to the constructor
        // Which means it creates basic default data
        PlayerModel playerModel = new PlayerModel(player);
        // We then insert that Model
        db.insert(playerModel);
        // and close connection
        db.close();
    }

    public void addKills(Player killer, int i) {
        // Create Connection
        Db db = plugin.getDB();
        // Empty Player Model
        PlayerModel playerModel = new PlayerModel();
        // We then build our query to the database and update it
        // Should be self explanatory
        db.from(playerModel)
                .increment(playerModel.killCount)
                .by(i)
                .where(playerModel.playerUUID)
                .is(killer.getUniqueId().toString())
                .update();
        // Close connection
        db.close();
    }

    public void setLastLogin(Player player) {
        // Create Connection
        Db db = plugin.getDB();
        // Empty Player Model
        PlayerModel playerModel = new PlayerModel();
        // This is needed to get CurrentTimestamp
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        // Do the same as before but instead update TimeStamp
        db.from(playerModel)
                .set(playerModel.lastLoggedIn)
                .to(new Timestamp(now.getTime()))
                .where(playerModel.playerUUID)
                .is(player.getUniqueId().toString())
                .update();
        // Close connection
        db.close();
    }
}
