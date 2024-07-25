package crystal.MythicKillCount.Commands;

import crystal.MythicKillCount.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class ResetAllHandle {
    public static boolean handle(String[] args) {
        //reset all for everyone
        if (args[1].equalsIgnoreCase("all")) {
            File dir = new File("MythicKillCount/data");
            File[] dirList = dir.listFiles();
            for (File child : dirList) {
                try {
                    if (!child.delete()){
                        Bukkit.getLogger().info("File "+ child.getName() +" can not delete");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        } else {
            //reset all of a player
            Player target = Bukkit.getPlayer(args[1]);

            if (target != null) {
                UUID targetUUID = target.getUniqueId();
                File dataFile = EventListener.getPlayerDataFile(targetUUID);
                try {
                    if (dataFile.delete()) {
                        Bukkit.getLogger().info("File deleted");
                    } else {
                        Bukkit.getLogger().info("File not deleted");
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
    }
}