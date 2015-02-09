package com.clashnia.plugins.purge.utils;

import com.clashnia.plugins.purge.PurgePlugin;
import com.clashnia.plugins.purge.models.PlayerModel;
import com.iciql.Db;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

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
        // we passed 'player' as a parameter to the constructor
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

    public void updateLastLogin(Player player) {
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

    public void addRelog(Player player, int amount) {
        // Open Connection to DB
        Db db = plugin.getDB();
        // Empty Player Model
        PlayerModel playerModel = new PlayerModel();
        // Update Kill count
        db.from(playerModel)
                .increment(playerModel.relogCount)
                .by(amount)
                .where(playerModel.playerUUID)
                .is(player.getUniqueId().toString())
                .update();
        // Close Connection
        db.close();
    }

    public void setDead(Player player, boolean value) {
        // Same Stuff
        Db db = plugin.getDB();
        PlayerModel playerModel = new PlayerModel();
        // This is Just a Temporary Value because I store Booleans as 1's and 0's in the Database
        int tmp = 0;
        if (value)
            tmp = 1;
        // Now we Update the Database
        db.from(playerModel)
                .set(playerModel.isDead)
                .to(tmp)
                .where(playerModel.playerUUID)
                .is(player.getUniqueId().toString())
                .update();
        // Close Connection
        db.close();
    }

    public void updateScoreBoard(Player player){
        ScoreboardManager scoreboardManager = plugin.getServer().getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "PURGE");
        Score score = objective.getScore(ChatColor.RED + "Kills:");

        Db db = plugin.getDB();
        PlayerModel playerModel = new PlayerModel();
        int kills = db.
                    from(playerModel).
                    where(playerModel.playerUUID).
                    is(player.getUniqueId().toString()).
                    selectFirst().
                    killCount;
        score.setScore(kills);

        Score score1 = objective.getScore(ChatColor.RED + "Relogs:");
        int relogs = db.
                from(playerModel).
                where(playerModel.playerUUID).
                is(player.getUniqueId().toString()).
                selectFirst().
                relogCount;
        score1.setScore(relogs);

        player.setScoreboard(scoreboard);

        // Never Forget to close the connection
        db.close();
    }
}
