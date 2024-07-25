package crystal.MythicKillCount;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class Commands implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        //reset all mob of a player
        if(args.length >= 2 && args[0].equalsIgnoreCase("resetAll")) {
            //reset all for everyone
            if(args[1].equalsIgnoreCase("all")) {
                File dir = new File("MythicKillCount/data");
                File[] dirList = dir.listFiles();
                for(File child : dirList) {
                    try {
                        child.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
            //reset all of a player
            Player target = Bukkit.getPlayer(args[1]);
            
            if(target != null) {
                UUID targetUUID = target.getUniqueId();
                File dataFile = EventListener.getPlayerDataFile(targetUUID);
                try {
                    if (dataFile.delete()) {
                        Bukkit.getLogger().info("File deleted");
                    } else Bukkit.getLogger().info("File not deleted");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //reset a mob
        //mythicmobcount reset [player,all] mobID
        if(args.length >= 3 && args[0].equalsIgnoreCase("reset")) {
            //get mobID
            String mobID = args[2].toUpperCase();
            //reset a mob for everyone
            if(args[1].equalsIgnoreCase("all")) {
                File dir = new File("MythicKillCount/data");
                File[] dirList = dir.listFiles();
                for(File child : dirList) {
                    YamlConfiguration data = YamlConfiguration.loadConfiguration(child);
                    data.set(mobID, null);
                    try {
                        data.save(child);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                
                return true;
            }
            
            //reset a mob of a player
            Player target = Bukkit.getPlayer(args[1]);
            if(target != null) {
                UUID targetUUID = target.getUniqueId();
                File dataFile = EventListener.getPlayerDataFile(targetUUID);
                YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                data.set(mobID, null);
                try {
                    data.save(dataFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //change the mob amount of a player/all
        //mythicmobcount change [player,all] mobID value
        if(args.length >=4&& args[0].equalsIgnoreCase("change")) {
            String mobID = args[2].toUpperCase();
            //change for all players
            if(args[1].equalsIgnoreCase("all")) {
                File dir = new File("MythicKillCount/data");
                File[] dirList = dir.listFiles();
                for(File child : dirList) {
                    YamlConfiguration data = YamlConfiguration.loadConfiguration(child);
                    int tempData = data.getInt(mobID);
                    if(tempData + Integer.parseInt(args[3]) < 0) {tempData = 0;}
                    else tempData += Integer.parseInt(args[3]);
                    data.set(mobID, tempData);
                    try {
                        data.save(child);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                
                return true;
            }
            //Change for a player
            Player target = Bukkit.getPlayer(args[1]);
            if(target != null) {
                UUID targetUUID = target.getUniqueId();
                File dataFile = EventListener.getPlayerDataFile(targetUUID);
                YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                int tempData = data.getInt(mobID);
                if(tempData + Integer.parseInt(args[3]) < 0) {tempData = 0;}
                else tempData += Integer.parseInt(args[3]);
                data.set(mobID, tempData);
                try {
                    data.save(dataFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //Set an amount for player/all
        //mythicmobcount set [player,all] mobID value
        if(args.length >= 4 && args[0].equalsIgnoreCase("set")) {
            String mobID = args[2].toUpperCase();
            //change for all players
            if(args[1].equalsIgnoreCase("all")) {
                File dir = new File("MythicKillCount/data");
                File[] dirList = dir.listFiles();
                for(File child : dirList) {
                    YamlConfiguration data = YamlConfiguration.loadConfiguration(child);
                    data.set(mobID, args[3]);
                    try {
                        data.save(child);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                
                return true;
            }
            //Change for a player
            Player target = Bukkit.getPlayer(args[1]);
            if(target != null) {
                UUID targetUUID = target.getUniqueId();
                File dataFile = EventListener.getPlayerDataFile(targetUUID);
                YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                data.set(mobID, args[3]);
                try {
                    data.save(dataFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(command.toString().equalsIgnoreCase("mythicmobcount"))
            //mythicmobcount arg1 arg2 arg3
        if(args.length == 0) {
            String[] firstArg = {"reset","resetAll","change","set"};
            return Arrays.asList(firstArg);
        }
        if(args.length == 1) {
            // /command reset [player,all] mobID value
            String[] secondArg = {"player","all"};
            return Arrays.asList(secondArg);
        }
        //command reset [player,all] mobID value
        if(args.length >= 2 && args[1].equalsIgnoreCase("all")) {
            return null;
        }
        if(args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if(target != null) {
                UUID targetUUID = target.getUniqueId();
                File dataFile = EventListener.getPlayerDataFile(targetUUID);
                YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                List<String> mobList = new ArrayList<>(data.getKeys(false));
                return mobList;
            }
        }
        return null;
    }
}
