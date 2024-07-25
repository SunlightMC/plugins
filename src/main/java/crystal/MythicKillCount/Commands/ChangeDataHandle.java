package crystal.MythicKillCount.Commands;

import crystal.MythicKillCount.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ChangeDataHandle {
    public static boolean handle(String[] args) {
        String mobID = args[2].toUpperCase();
        //change for all players
        if (args[1].equalsIgnoreCase("all")) {
            File dir = new File("MythicKillCount/data");
            File[] dirList = dir.listFiles();
            for (File child : dirList) {
                YamlConfiguration data = YamlConfiguration.loadConfiguration(child);
                int tempData = data.getInt(mobID);
                if (tempData + Integer.parseInt(args[3]) < 0) {
                    tempData = 0;
                } else tempData += Integer.parseInt(args[3]);
                data.set(mobID, tempData);
                try {
                    data.save(child);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            return true;
        } else {
            //Change for a player
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                UUID targetUUID = target.getUniqueId();
                File dataFile = EventListener.getPlayerDataFile(targetUUID);
                YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                int tempData = data.getInt(mobID);
                if (tempData + Integer.parseInt(args[3]) < 0) {
                    tempData = 0;
                } else tempData += Integer.parseInt(args[3]);
                data.set(mobID, tempData);
                try {
                    data.save(dataFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
    }
}