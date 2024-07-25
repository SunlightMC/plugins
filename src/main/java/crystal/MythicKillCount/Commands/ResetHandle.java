package crystal.MythicKillCount.Commands;

import crystal.MythicKillCount.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ResetHandle {
    public static boolean handle(String[] args) {
        //get mobID
        String mobID = args[2].toUpperCase();

        //reset a mob for everyone
        if (args[1].equalsIgnoreCase("all")) {
            File dir = new File("MythicKillCount/data");
            File[] dirList = dir.listFiles();
            for (File child : dirList) {
                YamlConfiguration data = YamlConfiguration.loadConfiguration(child);
                data.set(mobID, null);
                try {
                    data.save(child);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        } else {
            //reset a mob of a player
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                UUID targetUUID = target.getUniqueId();
                File dataFile = EventListener.getPlayerDataFile(targetUUID);
                YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                data.set(mobID, null);
                try {
                    data.save(dataFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
    }
}